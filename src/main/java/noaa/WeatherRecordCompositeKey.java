package noaa;

import java.io.Serializable;

// for defining multiple columns composition as unique id for each row in the weather_record table.
// assuming there is one record per station id per date per element being cheked (the data is "daily")
public class WeatherRecordCompositeKey implements Serializable {
    private String station;
    private String date;
    private String element;
}
