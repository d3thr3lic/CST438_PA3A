package cst438hw2.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempAndTime {
    public double temp;
    public long time;
    public int timezone;
    
    public TempAndTime(double temp, long time, int timezone){
        this.temp = (temp - 273.15) * 9.0/5.0 + 32.0;
        this.time = time;
        this.timezone = timezone;
    }

    public double getTemp() {
      // https://www.baeldung.com/java-round-decimal-number
      BigDecimal bd = new BigDecimal(Double.toString(temp));
      bd = bd.setScale(2, RoundingMode.HALF_UP);
      return bd.doubleValue();
    }

    public void setTemp(double temp) {
      this.temp = temp;
    }

    public long getTime() {
      return time;
    }
    
    public String getTimeString() {
      DateFormat dateFormat = new SimpleDateFormat("h:mm aa");
      String dateString = dateFormat.format(time).toString();
      return dateString;
    }

    public void setTime(long time) {
      this.time = time;
    }

    public int getTimezone() {
      return timezone;
    }

    public void setTimezone(int timezone) {
      this.timezone = timezone;
    }
 }
