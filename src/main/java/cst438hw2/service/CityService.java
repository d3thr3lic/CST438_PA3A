package cst438hw2.service;

import java.util.List;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private FanoutExchange fanout;
    
    public CityInfo getCityInfo(String cityName) {
      // Grab data from database that holds city information
      List<City> cities = cityRepository.findByName(cityName);
      if (cities != null) {
        City city = cities.get(0);
        Country country = countryRepository.findByCode(city.getCountryCode());
        TempAndTime tempAndTime = weatherService.getTempAndTime(cityName);
        
        return new CityInfo(city, country.getName(), tempAndTime.getTemp(),
                            tempAndTime.getTimeString());
      }
      return null; 
    }

    public void requestReservation( 
                  String cityName, 
                  String level, 
                  String email) {
      
      String msg  = "{\"cityName\": \""+ cityName + 
          "\" \"level\": \""+level+
          "\" \"email\": \""+email+"\"}";
      
      System.out.println("Sending message:"+msg);
      rabbitTemplate.convertSendAndReceive(
          fanout.getName(),
          "",   // routing key none.
          msg);
    }
}
