package com.reactivestax.spring5mvc;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.repository.WidgetRepository;
import com.reactivestax.spring5mvc.service.WidgetService;
import com.reactivestax.spring5mvc.utils.dto.ClientMetaData;
import com.reactivestax.spring5mvc.utils.dto.ErrorInfo;
import com.reactivestax.spring5mvc.validators.ValidationRuleFactoryForWidgetController;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetControllerUsingRestAssuredTest {

    @Value("${local.server.port}")
    int port;

    @Value("${server.servlet.context-path}")
    String serverContextPath;

    @MockBean
    WidgetRepository widgetRepository;

    @MockBean
    WidgetService widgetService;

    @MockBean
    ValidationRuleFactoryForWidgetController validationRuleFactoryForWidgetController;

    String baseUrl;

    @BeforeAll
    public void setup() {
        RestAssured.port = port;
        baseUrl = "http://localhost:" + port + serverContextPath + "/rest/widget";

    }


    @Test
    public void testPostWidget() throws Exception {
        BDDMockito.given(widgetService.saveWidget(ArgumentMatchers.anyObject())).willReturn(new Widget("name123", "description123"));


        Gson gson = new Gson();
        String widgetJsonString = gson.toJson(new Widget("name123", "description123"));

        ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

        System.out.println("baseUrl = " + baseUrl);
        Response response =

                RestAssured
                        .given()
                        .header("client-metadata", clientMetaData)
                        .contentType(ContentType.JSON)
                        .request()
                        .body("{\n" +
                                "\"name\": \"name23324\",\n" +
                                "\"description\":\"description234234\"\n" +
                                "}")
                        .when()
                        .post(baseUrl)
                        .then()
                        .statusCode(SC_CREATED)
                        .contentType(ContentType.JSON)
//						.assertThat()
//						.body(Matchers.notNull())
                        .extract().response();
        String responseString = response.asString();
        System.out.println("responseString:" + responseString);

        Widget widget = gson.fromJson(responseString, Widget.class);
        Assertions.assertThat(StringUtils.equalsIgnoreCase(widget.getDescription(), "description234234"));
        Assertions.assertThat(StringUtils.equalsIgnoreCase(widget.getName(), "name23324"));
    }

    @Disabled("for demo purposes")
    public void testPostWidgetMissingClientMetadata() throws Exception {
        BDDMockito.given(widgetService.saveWidget(ArgumentMatchers.anyObject())).willReturn(new Widget("name123", "description123"));


        Gson gson = new Gson();
        String widgetJsonString = gson.toJson(new Widget("name123", "description123"));

        ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

        System.out.println("baseUrl = " + baseUrl);
        Response response =

                RestAssured
                        .given()
                        //.header("client-metadata", clientMetaData)
                        .contentType(ContentType.JSON)
                        .request()
                        .body("{\n" +
                                "\"name\": \"name23324\",\n" +
                                "\"description\":\"description234234\"\n" +
                                "}")
                        .when()
                        .post(baseUrl)
                        .then()
                        .statusCode(SC_BAD_REQUEST)
                        .contentType(ContentType.JSON)

                        .extract().response();

        String responseString = response.asString();
        System.out.println("responseString:" + responseString);

        ErrorInfo errorInfo = gson.fromJson(responseString, ErrorInfo.class);
        Assertions.assertThat(StringUtils.equalsIgnoreCase(errorInfo.getDetailedMessages().get(0).getMessage(), "client_metadata request header cannot be missing or have blank value"));

    }

    private ClientMetaData retrieveClientMetadataFromFile(String fileName) throws IOException {
        InputStreamReader reader = new InputStreamReader(WidgetControllerUsingRestAssuredTest.class.getResourceAsStream(fileName), "UTF-8");
        Gson gson = new Gson();
        ClientMetaData clientMetaData = gson.fromJson(reader, ClientMetaData.class);
        reader.close();
        return clientMetaData;
    }

}
