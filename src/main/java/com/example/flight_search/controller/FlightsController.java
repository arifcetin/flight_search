package com.example.flight_search.controller;

import com.example.flight_search.dto.FlightsDto;
import com.example.flight_search.entity.Flights;
import com.example.flight_search.service.FlightsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@Api(value = "Flight Documentation")
public class FlightsController {

    private final FlightsService flightsService;
    @Autowired
    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }
    @PostMapping
    @ApiOperation(value = "createFlight")
    public FlightsDto createFlight(@RequestBody Flights newFlight){
        FlightsDto flightsDto = flightsService.createFlight(newFlight);
        return flightsDto;
    }
    @GetMapping
    @ApiOperation(value = "getAllFlights")
    public List<FlightsDto> getAllFlights(){
        List<FlightsDto> flightsDtos = flightsService.getAllFlights();
        return  flightsDtos;
    }
    @GetMapping("{flight_id}")
    @ApiOperation(value = "getOneFlight")
    public List<FlightsDto> getOneFlight(@PathVariable Long flight_id){
        List<FlightsDto> flightsDto = flightsService.getOneFlight(flight_id);
        return flightsDto;
    }

    @GetMapping("/search")
    @ApiOperation(value = "searchFlights")
    public List<FlightsDto> searchFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") LocalDateTime departure_date,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy-HH:mm") LocalDateTime returnDate) {

        // flightsService kullanarak istenen kriterlere uygun uçuşları al
        List<FlightsDto> matchingFlights = flightsService.searchFlights(departure, arrival, departure_date, returnDate);

        return matchingFlights;
    }

    @PutMapping("{flight_id}")
    @ApiOperation(value = "updateFlight")
    public FlightsDto updateFlight(@RequestBody Flights updateFlights, @PathVariable Long flight_id){
        FlightsDto flightsDto = flightsService.updateFlight(updateFlights, flight_id);
        return flightsDto;
    }
    @DeleteMapping("{flight_id}")
    @ApiOperation(value = "deleteFlight")
    public void deleteFlight(@PathVariable Long flight_id){
        flightsService.deleteFlight(flight_id);
    }

}
