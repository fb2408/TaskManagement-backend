package com.example.mobileappserver.repository;

import com.example.mobileappserver.model.Tasktype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<Tasktype, Integer> {
}
