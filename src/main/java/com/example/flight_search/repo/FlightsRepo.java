package com.example.flight_search.repo;

import com.example.flight_search.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightsRepo extends JpaRepository<Flights,Long> {
    @Query(value = "SELECT f FROM Flights f WHERE f.departure = :departure and f.arrival = :arrival and f.departure_date = :departureDate and f.return_date is null")
    List<Flights> findByDepartureAndArrivalAndDeparture_date(
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("departureDate") LocalDateTime departureDate);

    @Query(value = "SELECT f FROM Flights f WHERE f.departure = :departure and f.arrival = :arrival and f.departure_date = :departureDate and f.return_date = :returnDate")
    List<Flights> findByDepartureAndArrivalAndDeparture_dateAndReturn_date(
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("departureDate") LocalDateTime departureDate,
            @Param("returnDate") LocalDateTime returnDate
    );

}
