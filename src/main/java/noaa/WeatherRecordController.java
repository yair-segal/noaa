package noaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import noaa.WeatherRecord;
import noaa.WeatherRecordRepository;

@RestController
@Validated // for validating input later
@Api(value="NOAA", description="Operations for getting daily weather info")
public class WeatherRecordController {

    @Autowired
    private WeatherRecordRepository weatherRecordRepository;

    @GetMapping("/weather_record")
    public @ResponseBody List<WeatherRecord> weather_record(
	@RequestParam
	// prevent SQL injection - checking input length.
	// we can add further input checks, for example - contains only capitals and numbers.
	// for size longer than specified, Spring returns 500 code instead of traditional 400.
	// to return 400 we can catch the exception and customize the response.
	@Size(max=11, message="station must be up to 11 characters")
	String station)
	{
	    return weatherRecordRepository.findByStation(station);
	}
}
