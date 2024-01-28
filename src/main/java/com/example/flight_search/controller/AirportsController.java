package com.example.flight_search.controller;

import com.example.flight_search.dto.AirportsDto;
import com.example.flight_search.entity.Airports;
import com.example.flight_search.service.AirportsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
@Api(value = "Airport Documentation")
public class AirportsController {

    private final AirportsService airportsService;

    @Autowired
    public AirportsController(AirportsService airportsService) {
        this.airportsService = airportsService;
    }
    @PostMapping
    @ApiOperation(value = "createAirport")
    public AirportsDto createAirport(@RequestBody Airports newAirports){
        AirportsDto airportsDto = airportsService.createAirport(newAirports);
        return airportsDto;
    }

    @GetMapping
    @ApiOperation(value = "getAirport")
    public List<AirportsDto> getAirports(){
        List<AirportsDto> airportsDtos = airportsService.getAll();
        return airportsDtos;
    }

    @DeleteMapping("{airport_id}")
    @ApiOperation(value = "deleteAirport")
    public void deleteAirport(@PathVariable Long airport_id){
        airportsService.deleteAirport(airport_id);
    }

    @PutMapping("{airport_id}")
    @ApiOperation(value = "updateAirport")
    public AirportsDto updateAirport(@RequestBody Airports updateAirport, @PathVariable Long airport_id){
        AirportsDto airportsDto = airportsService.updateAirport(updateAirport,airport_id);
        return airportsDto;
    }
}
