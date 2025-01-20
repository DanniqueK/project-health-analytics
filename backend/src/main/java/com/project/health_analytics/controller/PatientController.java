package com.project.health_analytics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.dto.HealthDataQueryDto;
import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.dto.SearchQueryDto;
import com.project.health_analytics.model.HealthData;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.HealthDataService;
import com.project.health_analytics.service.MedicalProfessionalService;
import com.project.health_analytics.service.PatientService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * REST controller for managing patients and their health data.
 */
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalProfessionalService medicalProfessionalService;

    @Autowired
    private HealthDataService healthDataService;

    /**
     * Retrieves a list of all patients by the primary physician's BIG number.
     *
     * @param request The HTTP request containing the user's BSN.
     * @return A ResponseEntity containing a list of PatientDto objects and an HTTP
     *         status code.
     */
    @GetMapping("/patient-list")
    @AllowedUserTypes({ UserType.medical_professional, UserType.dentist })
    public ResponseEntity<List<PatientDto>> getPatientsByPrimaryPhysician(HttpServletRequest request) {
        Integer bsn = (Integer) request.getAttribute("bsn");
        String big = medicalProfessionalService.getBigByBsn(bsn);
        if (big != null) {
            List<PatientDto> patients = patientService.getPatientsByPrimaryPhysician(big);
            return new ResponseEntity<>(patients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Searches for patients by name or BSN.
     *
     * @param searchQueryDto DTO containing the search query string
     * @return A ResponseEntity containing a list of PatientDto objects and an HTTP
     *         status code
     */
    @PostMapping("/patient-list")
    @AllowedUserTypes({ UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<List<PatientDto>> searchPatients(@RequestBody SearchQueryDto searchQueryDto) {
        List<PatientDto> patients = patientService.searchPatientsByNameOrBsn(searchQueryDto.getSearchQuery());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * Retrieves health data for a patient by their BSN.
     *
     * @param healthDataQueryDto DTO containing the BSN of the patient
     * @return A ResponseEntity containing a List of HealthData and an HTTP status
     *         code
     */
    @PostMapping("/patient/health-data")
    @AllowedUserTypes({ UserType.patient, UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<List<HealthData>> getHealthDataByPatientBsn(
            @RequestBody HealthDataQueryDto healthDataQueryDto) {
        List<HealthData> healthDataList = healthDataService.getHealthDataByPatientBsn(healthDataQueryDto.getBsn());
        if (!healthDataList.isEmpty()) {
            return new ResponseEntity<>(healthDataList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}