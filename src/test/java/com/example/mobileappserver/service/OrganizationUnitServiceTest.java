package com.example.mobileappserver.service;

import com.example.mobileappserver.model.OrganizationUnit;
import com.example.mobileappserver.repository.OrganizationUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationUnitServiceTest {

    @Mock
    private OrganizationUnitRepository organizationUnitRepository;
    private OrganizationUnitService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrganizationUnitService(organizationUnitRepository);
    }

    @Test
    void addOrganizationUnit() throws Exception {
        final OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar",
                LocalTime.of(9,0), LocalTime.of(0,0),
                null);

        underTest.addOrganizationUnit(testOrganizationUnit);
        verify(organizationUnitRepository).save(testOrganizationUnit);
    }

    @Test
    void updateOrganizationUnitFail() {
        final OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar",
                LocalTime.of(9,0), LocalTime.of(0,0),
                null);

        Exception exception = assertThrows(Exception.class, () -> {
            underTest.updateOrganizationUnit(testOrganizationUnit);
        });

        assertEquals("Org unit not found!", exception.getMessage());
    }

    @Test
    void updateOrganizationUnitSuccess() throws Exception {
        final OrganizationUnit testOrganizationUnit = new OrganizationUnit("Aria bar",
                LocalTime.of(9,0), LocalTime.of(0,0),
                null);

        when(organizationUnitRepository.findById(testOrganizationUnit.getId())).thenReturn(Optional.of(testOrganizationUnit));

        underTest.updateOrganizationUnit(testOrganizationUnit);

        verify(organizationUnitRepository).save(testOrganizationUnit);
    }


    @Test
    void deleteOrganizationUnit() {
        int id = 1;
        underTest.deleteOrganizationUnit(id);
        verify(organizationUnitRepository).deleteById(id);
    }

    @Test
    void listAll() {
        underTest.listAll();
        verify(organizationUnitRepository).findAll();
    }
}