package com.reactivestax.spring5mvc;

import com.google.gson.Gson;
import com.reactivestax.spring5mvc.exceptions.NoDataFoundException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import com.reactivestax.spring5mvc.web.WidgetRestController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class WidgetControllerUsingMockMvcTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	WidgetRepository widgetRepository;

	@MockBean
	WidgetService widgetService;

	@MockBean
	ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;

	//GET good case
	@Test
	public void testGetWidget() throws Exception {
		BDDMockito
				.given(widgetService.findWidgetById(1L))
				.willReturn(Optional.of(new Widget("name123","description123")));

		mockMvc.perform(MockMvcRequestBuilders.get("/rest/widget/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("name123"))
				.andExpect(jsonPath("description").value("description123"));
	}

	// GET bad case
	@Test
	public void testGetWidgetWithNoDataFoundError() throws Exception {
		BDDMockito.given(widgetService.findWidgetByIdNotOptional(ArgumentMatchers.anyLong())).willThrow(new NoDataFoundException());

		mockMvc.perform(MockMvcRequestBuilders.get("/rest/widget/v2/1234234234"))
				.andExpect(status().isNoContent());
	}

	// POST good case
	@Test
	public void testPostWidget() throws Exception {
		BDDMockito.given(widgetService.saveWidget(ArgumentMatchers.anyObject())).willReturn(new Widget("name123","description123"));

		Gson gson = new Gson();
		String widgetJsonString = gson.toJson(new Widget("name123", "description123"));
		//
		mockMvc.perform(MockMvcRequestBuilders.post("/rest/widget").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(widgetJsonString))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("name").value("name123"))
				.andExpect(jsonPath("description").value("description123"));
	}

//	@Test    // POST bad case
	//TODO: Robin -- Add a validation failed test as well and pass wrong name and description value.
	//expect the http STatus to be 400 in this case.
}
