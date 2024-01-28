package com.example.flight_search.repo;

import com.example.flight_search.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String userName);

    User getOneUserByUserName(String userName);
}
