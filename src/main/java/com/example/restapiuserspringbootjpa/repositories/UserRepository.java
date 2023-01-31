package com.example.restapiuserspringbootjpa.repositories;

import com.example.restapiuserspringbootjpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
