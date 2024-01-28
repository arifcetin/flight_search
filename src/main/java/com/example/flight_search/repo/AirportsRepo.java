package com.example.flight_search.repo;

import com.example.flight_search.entity.Airports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportsRepo extends JpaRepository<Airports,Long> {
}
