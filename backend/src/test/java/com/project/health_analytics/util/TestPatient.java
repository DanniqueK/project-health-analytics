package com.project.health_analytics.util;

import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.model.Patient;
import com.project.health_analytics.model.BloodType;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating test Patient objects and DTOs
 */
public class TestPatient {

    public static List<PatientDto> createPatientDtoList() {
        List<PatientDto> patients = new ArrayList<>();
        patients.add(new PatientDto(123456789, "John Doe", new Timestamp(31556952000L)));
        patients.add(new PatientDto(987654321, "Jane Smith", new Timestamp(38536352000L)));
        return patients;
    }

    public static PatientDto createPatientDto() {
        return new PatientDto(123456789, "John Doe", new Timestamp(88536352000L));
    }

    public static Patient createPatient() {
        return new Patient(
                123456789, // bsn
                "Pollen, Penicillin", // allergies
                "Appendectomy 2015", // medicalHistory
                BloodType.A_Negative, // bloodType
                180, // height in cm
                new BigDecimal("75.5"), // weight in kg
                "Metformin 500mg", // currentMedication
                false, // smokingStatus
                false, // alcoholConsumption
                "None", // drugConsumption
                "3x weekly gym", // exerciseRoutine
                "12345678", // primaryPhysician (BIG number)
                Date.valueOf("2023-01-01"), // dateOfRegistration
                "Diabetes in family" // familyHistory
        );
    }

    public static List<Patient> createPatientList() {
        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient(
                123456789,
                "Pollen, Penicillin",
                "Appendectomy 2015",
                BloodType.O_Negative,
                180,
                new BigDecimal("75.5"),
                "Metformin 500mg",
                false,
                false,
                "None",
                "3x weekly gym",
                "12345678",
                Date.valueOf("2023-01-01"),
                "Diabetes in family"));

        patients.add(new Patient(
                987654321,
                "Lactose intolerant",
                "Broken arm 2018",
                BloodType.O_Negative,
                165,
                new BigDecimal("62.0"),
                "None",
                false,
                true,
                "None",
                "Daily walking",
                "12345678",
                Date.valueOf("2023-02-01"),
                "Heart disease in family"));

        return patients;
    }
}