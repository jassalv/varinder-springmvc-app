package com.reactivestax.spring5mvc;

import com.reactivestax.spring5mvc.exceptions.NoDataFoundException;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WidgetServiceTest {

    @MockBean
    WidgetRepository widgetRepository;

    @MockBean
    ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;

    @Autowired
    WidgetService widgetService;


    @Test
    public void testGetWidget() throws Exception {
        BDDMockito.given(widgetRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(new Widget("name123", "description123")));
        Optional<Widget> widgetById = widgetService.findWidgetById(123L);
        Assertions.assertTrue(widgetById.isPresent());
    }


    @Test
    public void testGetWidgetWithNoDataFoundException() throws Exception {
        BDDMockito.given(widgetRepository.findById(ArgumentMatchers.anyLong())).willThrow(new NoDataFoundException());

        Assertions.assertThrows(NoDataFoundException.class, () -> {
            widgetService.findWidgetByIdNotOptional(123L);
        });
    }
}
