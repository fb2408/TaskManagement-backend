package com.example.mobileappserver.service;

import com.example.mobileappserver.model.OrganizationUnit;
import com.example.mobileappserver.repository.OrganizationUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationUnitService {

    @Autowired
    private final OrganizationUnitRepository organizationUnitRepository;

    public OrganizationUnitService(OrganizationUnitRepository organizationUnitRepository) {
        this.organizationUnitRepository = organizationUnitRepository;
    }

    public void addOrganizationUnit(OrganizationUnit organizationunit) {
        organizationUnitRepository.save(organizationunit);
    }

    public void updateOrganizationUnit(OrganizationUnit organizationunit) throws Exception {
        OrganizationUnit changedOrgUnit = organizationUnitRepository.findById(organizationunit.getId()).orElseThrow(() -> new Exception("Org unit not founded!"));
        changedOrgUnit.setName(organizationunit.getName());
        changedOrgUnit.setCloseTime(organizationunit.getCloseTime());
        changedOrgUnit.setOpenTime(organizationunit.getOpenTime());
        changedOrgUnit.setSuperorganizationunit(organizationunit.getSuperorganizationunit());
        organizationUnitRepository.save(changedOrgUnit);
    }

    public void deleteOrganizationUnit(int orgUnitId) {
        organizationUnitRepository.deleteById(orgUnitId);
    }

    public List<OrganizationUnit> listAll() {
        return organizationUnitRepository.findAll();
    }
}
