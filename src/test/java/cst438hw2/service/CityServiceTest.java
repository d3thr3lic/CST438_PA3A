package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testCityFound() throws Exception {
	  Country country = new Country("TST", "Test Country");
      City city = new City(1, "TestCity", "TEST", "DistrictTest", 100000);
      List<City> cities = new ArrayList<City>();
      cities.add(city);
      
      TempAndTime tempAndTime = new TempAndTime((double) 100, (long) 10, 1);
      
      given(cityRepository.findByName("TestCity")).willReturn(cities);
      
      given(countryRepository.findByCode("TEST")).willReturn(country);
      
      given(weatherService.getTempAndTime("TestCity")).willReturn(tempAndTime);
      
      CityInfo cityInfoTest = cityService.getCityInfo("TestCity");
      
      // verify that result is as expected
      assertThat(cityInfoTest.getId()).isEqualTo(city.getId());
      assertThat(cityInfoTest.getName()).isEqualTo(city.getName());
      assertThat(cityInfoTest.getCountryCode()).isEqualTo(city.getCountryCode());
      assertThat(cityInfoTest.getCountryName()).isEqualTo(country.getName());
      assertThat(cityInfoTest.getDistrict()).isEqualTo(city.getDistrict());
      assertThat(cityInfoTest.getPopulation()).isEqualTo(city.getPopulation());
      assertThat(cityInfoTest.getTemp()).isEqualTo(tempAndTime.getTemp());
      assertThat(cityInfoTest.getTime()).isEqualTo(tempAndTime.getTimeString());
	}
	
	@Test 
	public void  testCityNotFound() {
	  Country country = new Country("TST", "Test Country");
      City city = new City(1, "TestCity", "TEST", "DistrictTest", 100000);
      List<City> cities = new ArrayList<City>();
      cities.add(city);
      
      TempAndTime tempAndTime = new TempAndTime((double) 100, (long) 10, 1);
      
      given(cityRepository.findByName("TestCity")).willReturn(null);
      
      given(countryRepository.findByCode("TEST")).willReturn(null);
      
      given(weatherService.getTempAndTime("TestCity")).willReturn(null);
      
      CityInfo cityInfoTest = cityService.getCityInfo("TestCity");
      
      // verify that result is as expected
      assertThat(cityInfoTest).isEqualTo(null);
	}
	
	@Test 
	public void  testCityMultiple() {
	  Country country = new Country("TST", "Test Country");
      City city = new City(1, "TestCity", "TEST", "DistrictTest", 100000);
      List<City> cities = new ArrayList<City>();
      cities.add(city);
      
      City secondCity = new City(2 , "BadCity", "TEST2", "DisctrictTest2", 45);
      cities.add(secondCity);
      
      TempAndTime tempAndTime = new TempAndTime((double) 100, (long) 10, 1);
      
      given(cityRepository.findByName("TestCity")).willReturn(cities);
      
      given(countryRepository.findByCode("TEST")).willReturn(country);
      
      given(weatherService.getTempAndTime("TestCity")).willReturn(tempAndTime);
      
      CityInfo cityInfoTest = cityService.getCityInfo("TestCity");
      
      // verify that result is as expected
      assertThat(cityInfoTest.getId()).isEqualTo(city.getId());
      assertThat(cityInfoTest.getName()).isEqualTo(city.getName());
      assertThat(cityInfoTest.getCountryCode()).isEqualTo(city.getCountryCode());
      assertThat(cityInfoTest.getCountryName()).isEqualTo(country.getName());
      assertThat(cityInfoTest.getDistrict()).isEqualTo(city.getDistrict());
      assertThat(cityInfoTest.getPopulation()).isEqualTo(city.getPopulation());
      assertThat(cityInfoTest.getTemp()).isEqualTo(tempAndTime.getTemp());
      assertThat(cityInfoTest.getTime()).isEqualTo(tempAndTime.getTimeString());		
	}

}
