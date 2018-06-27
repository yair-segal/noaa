package noaa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import noaa.WeatherRecord;

public interface WeatherRecordRepository extends CrudRepository<WeatherRecord, String> {
    List<WeatherRecord> findByStation(String station);
}
