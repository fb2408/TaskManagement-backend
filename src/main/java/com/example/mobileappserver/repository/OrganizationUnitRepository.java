package com.example.mobileappserver.repository;

import com.example.mobileappserver.model.OrganizationUnit;
import com.example.mobileappserver.model.Usertask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, Integer> {

    OrganizationUnit findByName(String name);

}
