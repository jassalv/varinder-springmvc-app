package com.reactivestax.spring5mvc;

import com.google.gson.Gson;
import com.reactivestax.spring5mvc.model.Widget;
import com.reactivestax.spring5mvc.utils.dto.ErrorInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomServerPort;

    @Value("${server.servlet.context-path}")
    String serverContextPath;


    @Bean
    public Gson createGson() {
        return new Gson();
    }

    @Autowired
    Gson gson;

    @Test
    public void testPostWidget() throws URISyntaxException {
        //arrange
        Widget widget = new Widget();
        widget.setName("name");
        widget.setDescription("desc123");

        Widget widget2 = new Widget();
        widget2.setName("name");
        widget2.setDescription("123desc123");

        //act

        final String baseUrl = "http://localhost:" + randomServerPort + "/" + serverContextPath + "/rest/widget";
        System.out.println("baseUrl = " + baseUrl);
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("client-metadata", "{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}");

        HttpEntity<Widget> request = new HttpEntity<>(widget, headers);

        ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assertions.assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    public void testPostWidgetWithMissingClientMetadataRequestHeader() throws URISyntaxException {
        //arrange
        Widget widget = new Widget();
        widget.setName("name");
        widget.setDescription("desc123");

        Widget widget2 = new Widget();
        widget2.setName("name");
        widget2.setDescription("123desc123");

        //act

        final String baseUrl = "http://localhost:" + randomServerPort + "/" + serverContextPath + "/rest/widget";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
//		headers.set("client-metadata", "{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}");

        HttpEntity<Widget> request = new HttpEntity<>(widget, headers);

        ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, request, String.class);

        //Verify request succeed
        Assertions.assertEquals(400, result.getStatusCodeValue());
        Assertions.assertTrue(gson != null);
        ErrorInfo errorInfo = gson.fromJson(result.getBody().toString(), ErrorInfo.class);
        Assertions.assertTrue(errorInfo != null && StringUtils.equalsIgnoreCase(errorInfo.getDetailedMessages().get(0).getMessage(), "client_metadata request header cannot be missing or have blank value"));
    }

//	RestTemplate restTemplate() throws Exception {
//		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
//				.build();
//		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//		HttpClient httpClient = HttpClients.custom()
//				.setSSLSocketFactory(socketFactory)
//				.build();
//		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		return new RestTemplate(factory);
//	}


}
