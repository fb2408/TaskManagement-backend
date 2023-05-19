package com.example.mobileappserver.repository;

import com.example.mobileappserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();

    Optional<User> findByEmail(String email);

    List<User> findByIsAdmin(boolean b);
}