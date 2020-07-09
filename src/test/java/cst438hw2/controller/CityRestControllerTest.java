package cst438hw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import cst438hw2.domain.*;
import cst438hw2.service.CityService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;

	// This class is used for make simulated HTTP requests to the class
    // being tested.
	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@Before
	public void setup() {
	  JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
	  CityInfo testCityInfo = new CityInfo(1, "TestCity", "TST", "Test Country",
	                                   "DistrictTest", 100, (double) 101.3, "4:00 PM");
	  
	  given(cityService.getCityInfo("TestCity")).willReturn(testCityInfo);
	  
	  // perform the test by making simulated HTTP get using URL of "/api/city/TestCity"
      MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity"))
              .andReturn().getResponse();
      
      // verify that result is as expected
      assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
      
      // convert returned data from JSON string format to CityInfo object
      CityInfo cityInfoResult = json.parseObject(response.getContentAsString());
	  
      assertThat(cityInfoResult).isEqualTo(testCityInfo);		
	}
	
	@Test
    public void getNoCityInfo() throws Exception {
      CityInfo emptyCity = null;
      given(cityService.getCityInfo("TestCity")).willReturn(emptyCity);
      
      
      // perform the test by making simulated HTTP get using URL of "/city/TestCity"
      MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity"))
              .andReturn().getResponse();
      
      // verify that result is as expected
      assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());     
    }

}
