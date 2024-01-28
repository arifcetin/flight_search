package com.example.flight_search.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@ApiModel(value = "Flight Model")
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "flight_id")
    private Long flight_id;
    @ApiModelProperty(value = "departure")
    private String departure;
    @ApiModelProperty(value = "arrival")
    private String arrival;
    @ApiModelProperty(value = "departure_date")
    @JsonFormat(pattern = "dd-MM-yyyy-HH:mm")
    private LocalDateTime departure_date;
    @ApiModelProperty(value = "return_date")
    @JsonFormat(pattern = "dd-MM-yyyy-HH:mm")
    private LocalDateTime return_date;
    @ApiModelProperty(value = "price")
    private double price;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(LocalDateTime departure_date) {
        this.departure_date = departure_date;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
