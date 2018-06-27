package noaa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

import noaa.WeatherRecordCompositeKey;

@Entity
@IdClass(WeatherRecordCompositeKey.class)
public class WeatherRecord implements Serializable {

    // defining only getters as data is only being read from the database

    // defining composite id. see WeatherRecordCompositeKey class for documentation.
    @Id
    private String station;

    @Id
    private String date;

    @Id
    private String element;
 
    private String data_value;
    private String measure_flag;
    private String quality_flag;
    private String source_flag;
    private String obs_time;
    
    public String getStation() {
        return station;
    }
    
    public String getDate() {
	return date;
    }

    public String getElement() {
	return element;
    }

    public String getDataValue() {
	return data_value;
    }

    public String getMeasureFlag() {
        return measure_flag;
    }

    public String getQualityFlag() {
        return quality_flag;
    }
    
    public String getSourceFlag() {
        return source_flag;
    }
    
    public String getObservationTime() {
        return obs_time;
    }

}
