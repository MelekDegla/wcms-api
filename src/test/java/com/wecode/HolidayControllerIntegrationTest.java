/*
package com.wecode;

import com.wecode.controller.HolidayController;
import com.wecode.entity.Holiday;
import com.wecode.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HolidayControllerIntegrationTest {

    String url = "http://localhost:8091";
    String authAdmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEZWdsYSIsInNjb3BlcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1NzU4NDAxMTAsImV4cCI6MTU3NTkyNjUxMH0.WkOZzCdIahFXZJPU-tqwcdLzS65a-Szzh83JcgOY5Ow";
    String authNoadmin= "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aWVtIiwic2NvcGVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTU3NTg0MDA3NywiZXhwIjoxNTc1OTI2NDc3fQ.gtI6g8V95oHhWwCgg7GBYUHq3l_jsOQQS3ZeUqNgqXQ";


    Holiday holiday;

    @InjectMocks
    HolidayController holidayController;

    @Mock
    HolidayService holidayService;

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
        HttpEntity<Holiday[]> entityFromAdmin = new HttpEntity<Holiday[]>(null, headersAdmin);
        HttpEntity<List<Holiday>> response = restTemplate.exchange(
                url+"/holidays", HttpMethod.GET, entityFromAdmin, new ParameterizedTypeReference<List<Holiday>>(){});
        assertNotNull(response.getBody());

        // For No Admin Test
        HttpEntity<Holiday[]> entityNoAdmin = new HttpEntity<>(null, headersNoAdmin);
        HttpEntity<String> resNoAdmin = restTemplate.exchange(
                url+"/holidays", HttpMethod.GET, entityNoAdmin, String.class);
        assertNotNull(resNoAdmin.getBody());

        // For No Auth Test
        HttpEntity<Holiday[]> entity = new HttpEntity<>(null, headersNoAuth);
        HttpEntity<String> resNoAuth = restTemplate.exchange(
                url+"/holidays", HttpMethod.GET, entity, String.class);
        assert resNoAuth.getBody().contains("Unauthorized") ;
    }

    @Test
    void getOne() {
        // For Admin Test
        HttpEntity<Holiday> responseAdmin = restTemplate.exchange(
                url+"/holidays/2", HttpMethod.GET, new HttpEntity<Holiday>(null, headersAdmin), Holiday.class);

        assertNotNull(responseAdmin.getBody());
        System.out.println(responseAdmin.getBody());

        assertEquals("23-10-2020", responseAdmin.getBody().getEndDate());

        // For No Admin Test
        HttpEntity<Holiday> responseNoAdmin = restTemplate.exchange(
                url+"/holidays/2", HttpMethod.GET, new HttpEntity<Holiday>(null, headersNoAdmin), Holiday.class);

        assertNotNull(responseNoAdmin.getBody());
        assertEquals("23-10-2020", responseAdmin.getBody().getEndDate());

        // For No Auth Test
        HttpEntity<String> responseNoAuth = restTemplate.exchange(
                url+"/holidays/2", HttpMethod.GET, new HttpEntity<String>(null, headersNoAuth), String.class);

        assertNotNull(responseNoAuth.getBody());
        assert responseNoAuth.getBody().contains("Unauthorized") ;
    }

    @Test
    void saveHoliday() {
        holiday = new Holiday();
        holiday.setStatus(2);
        holiday.setEndDate("12-01-2020");
        holiday.setStartDate("04-12-2019");
        // For Admin Test
        HttpEntity<Holiday> entityAdmin = new HttpEntity<Holiday>(holiday, headersAdmin);
        restTemplate.postForEntity(url+"/holidays", entityAdmin, String.class);


        HttpEntity<Holiday> entityFromAdmin = new HttpEntity<Holiday>(null, headersAdmin);
        ResponseEntity<Holiday> resAdmin = restTemplate.exchange(
                url+"/holidays/enddate/"+holiday.getEndDate(), HttpMethod.GET, entityFromAdmin, Holiday.class);

        assertEquals(holiday.getStatus(), resAdmin.getBody().getStatus());
        assertEquals(holiday.getStartDate(), resAdmin.getBody().getStartDate());
        assertEquals(holiday.getEndDate(), resAdmin.getBody().getEndDate());

        // For No Admin Test
        Holiday holidayNoAdmin =new Holiday();
        holidayNoAdmin.setStatus(1);
        holidayNoAdmin.setEndDate("24-02-2020");
        holidayNoAdmin.setStartDate("05-12-2019");

        HttpEntity<Holiday> entityNoAdmin = new HttpEntity<Holiday>(holidayNoAdmin, headersNoAdmin);
        restTemplate.exchange(url+"/holidays", HttpMethod.POST, entityNoAdmin, String.class);


        ResponseEntity<Holiday> resNoAdmin = restTemplate.exchange(
                url+"/holidays/enddate/"+holidayNoAdmin.getEndDate(), HttpMethod.GET, entityNoAdmin, Holiday.class);

        assertEquals(holidayNoAdmin.getStatus(), resNoAdmin.getBody().getStatus());
        assertEquals(holidayNoAdmin.getStartDate(), resNoAdmin.getBody().getStartDate());
        assertEquals(holidayNoAdmin.getEndDate(), resNoAdmin.getBody().getEndDate());

        // For No Auth Test

        HttpEntity<Holiday> entityNoAuth = new HttpEntity<Holiday>(holidayNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> responseNoAAuth = restTemplate.exchange(url + "/holidays", HttpMethod.POST, entityNoAuth, String.class);
            assert responseNoAAuth.getBody().contains("Unauthorized") ;
        }
        catch (Exception e)
        {
            assert e.getMessage().contains("authentication") ;
        }
    }

    @Test
    void delete() {
        // For Admin Test
        holiday = new Holiday();
        holiday.setStatus(2);
        holiday.setEndDate("27-01-2020");
        holiday.setStartDate("04-12-2019");

        HttpEntity<Holiday> entityAdmin = new HttpEntity<Holiday>(holiday, headersAdmin);
        restTemplate.postForEntity(url+"/holidays", entityAdmin, String.class);

        HttpEntity<Holiday> entityFromAdmin = new HttpEntity<Holiday>(null, headersAdmin);
        ResponseEntity<Holiday> res = restTemplate.exchange(
                url+"/holidays/enddate/27-01-2020", HttpMethod.GET, entityFromAdmin, Holiday.class);

        restTemplate.exchange(url+"/holidays/"+res.getBody().getId(), HttpMethod.DELETE, entityFromAdmin, Holiday.class);

        HttpEntity<Holiday> res2 = restTemplate.exchange(
                url+"/holidays/enddate/27-01-2020", HttpMethod.GET, entityFromAdmin, Holiday.class);

        assertNull(res2.getBody());


        // For No Admin Test
        Holiday holidayNoAdmin =new Holiday();
        holidayNoAdmin.setStatus(1);
        holidayNoAdmin.setEndDate("23-08-2020");
        holidayNoAdmin.setStartDate("05-8-2019");

        HttpEntity<Holiday> entityNoAdmin = new HttpEntity<Holiday>(holidayNoAdmin, headersNoAdmin);
        restTemplate.postForEntity(url+"/holidays", entityNoAdmin, String.class);

        HttpEntity<Holiday> entityNoAdmin1 = new HttpEntity<Holiday>(null, headersNoAdmin);
        ResponseEntity<Holiday> resNoAdmin = restTemplate.exchange(
                url+"/holidays/enddate/23-08-2020", HttpMethod.GET, entityNoAdmin1, Holiday.class);

        ResponseEntity<Holiday> resultNoAdmin= restTemplate.exchange(url+"/holidays/"+resNoAdmin.getBody().getId(), HttpMethod.DELETE, entityNoAdmin1, Holiday.class);
        assert resultNoAdmin.getBody() == null;

        // No Auth Test
        HttpEntity<Holiday> entityNoAuth = new HttpEntity<Holiday>(null, headersNoAuth);
        ResponseEntity<String> resultNoAuth= restTemplate.exchange(url+"/holidays/1", HttpMethod.DELETE, entityNoAuth, String.class);
        assert resultNoAuth.getBody().contains("Unauthorized");
    }

    @Test
    void update() {

        holiday = new Holiday();
        holiday.setStatus(2);
        holiday.setEndDate("31-01-2020");
        holiday.setStartDate("31-12-2019");


        HttpEntity<Holiday> entityAdmin = new HttpEntity<Holiday>(holiday, headersAdmin);
        restTemplate.postForEntity(url+"/holidays", entityAdmin, String.class);

        ResponseEntity<Holiday> resAdmin = restTemplate.exchange(
                url+"/holidays/enddate/"+holiday.getEndDate(), HttpMethod.GET, new HttpEntity<Holiday>(null, headersAdmin), Holiday.class);

        holiday.setId(resAdmin.getBody().getId());
        holiday.setStatus(7);

        restTemplate.put(url+"/holidays", entityAdmin, String.class);


        ResponseEntity<Holiday> rsAdmin = restTemplate.exchange(
                url+"/holidays/"+holiday.getId(), HttpMethod.GET, new HttpEntity<Holiday>(null, headersAdmin), Holiday.class);



        assertEquals(holiday.getStatus(), rsAdmin.getBody().getStatus());
        assertEquals(holiday.getStartDate(), rsAdmin.getBody().getStartDate());
        assertEquals(holiday.getEndDate(), rsAdmin.getBody().getEndDate());

        // For No Admin Test
        Holiday holidayNoAdmin =new Holiday();
        holidayNoAdmin.setStatus(1);
        holidayNoAdmin.setEndDate("23-10-2020");
        holidayNoAdmin.setStartDate("05-10-2019");


        HttpEntity<Holiday> entityNoAdmin = new HttpEntity<Holiday>(holidayNoAdmin, headersNoAdmin);
        restTemplate.postForEntity(url+"/holidays", entityNoAdmin, String.class);

        ResponseEntity<Holiday> resNoAdmin = restTemplate.exchange(
                url+"/holidays/enddate/"+holiday.getEndDate(), HttpMethod.GET, new HttpEntity<Holiday>(null, headersNoAdmin), Holiday.class);

        holidayNoAdmin.setId(resNoAdmin.getBody().getId());
        holidayNoAdmin.setStatus(8);

        restTemplate.put(url+"/holidays", entityNoAdmin, String.class);


        ResponseEntity<Holiday> rsNoAdmin = restTemplate.exchange(
                url+"/holidays/"+holidayNoAdmin.getId(), HttpMethod.GET, new HttpEntity<Holiday>(null, headersNoAdmin), Holiday.class);



        assertEquals(holidayNoAdmin.getStatus(), rsNoAdmin.getBody().getStatus());
        assertEquals(holidayNoAdmin.getStartDate(), rsNoAdmin.getBody().getStartDate());
        assertEquals(holidayNoAdmin.getEndDate(), rsNoAdmin.getBody().getEndDate());


        // For No Auth Test
        HttpEntity<Holiday> entityNoAuth = new HttpEntity<Holiday>(holidayNoAdmin, headersNoAuth);
        try {
            HttpEntity<String> resultNoAuth = restTemplate.exchange(url + "/holidays", HttpMethod.PUT, entityNoAuth, String.class);
            assert resultNoAuth.getBody().contains("Unauthorized") ;
        }
        catch (Exception e) {
            assert e.getMessage().contains("authentication");
        }
    }
}
*/
