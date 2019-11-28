package com.wecode.test;

import com.wecode.controller.LogController;
import com.wecode.entity.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogControllerIntegrationTest {
    String url = "http://localhost:8091";
    String authAdmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb250YXNzYXIiLCJzY29wZXMiOiJST0xFX0FETUlOIiwiaWF0IjoxNTc0OTU4MjEyLCJleHAiOjE1NzUwNDQ2MTJ9.UFOjsGTToDnLRJb-G2p7B2YFfEYJBD4k7pW-kEx4eQE";
    String authNoadmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWVtIiwic2NvcGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNTc0OTU5MTE0LCJleHAiOjE1NzUwNDU1MTR9.bIWf_UIhW1-I3YRlbYEIoVPUt_igYJPN5pkPoUmzgT0";

    @Mock
    LogController logController;

    HttpHeaders headersAdmin = new HttpHeaders();
    HttpHeaders headersNoAdmin = new HttpHeaders();
    HttpHeaders headersNoAuth = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        headersAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersAdmin.add("Authorization", "Bearer "+ authAdmin);

        headersNoAdmin.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersNoAdmin.add("Authorization", "Bearer "+ authNoadmin);

    }


    @Test
    void findAll() {
        //For Admin Test
        HttpEntity<Log[]> entityFromAdmin = new HttpEntity<Log[]>(null, headersAdmin);
        HttpEntity<List<Log>> response = restTemplate.exchange(
                url+"/logs", HttpMethod.GET, entityFromAdmin, new ParameterizedTypeReference<List<Log>>(){});
        assertNotNull(response.getBody());

        // For No Admin Test
        HttpEntity<Log[]> entityNoAdmin = new HttpEntity<>(null, headersNoAdmin);
        HttpEntity<List<Log>> responseNoAdmin = restTemplate.exchange(
                url+"/logs", HttpMethod.GET, entityNoAdmin, new ParameterizedTypeReference<List<Log>>(){});
        assertNotNull(responseNoAdmin.getBody());

        // For No Auth Test
        HttpEntity<Log[]> entity = new HttpEntity<>(null, headersNoAuth);
        HttpEntity<String> resNoAuth = restTemplate.exchange(
                url+"/logs", HttpMethod.GET, entity, String.class);
        assert resNoAuth.getBody().contains("Unauthorized") ;
    }
}