package com.project.health_analytics.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.health_analytics.model.MedicalProfessional;
import com.project.health_analytics.repository.MedicalProfessionalRepository;

/**
 * Service class for handling medical professional operations.
 * 
 * @method getBigByBsn - Retrieves the BIG number by BSN.
 */
@Service
public class MedicalProfessionalServiceImpl implements MedicalProfessionalService {

    @Autowired
    private MedicalProfessionalRepository medicalProfessionalRepository;

    /**
     * Retrieves the BIG number by BSN.
     *
     * @param bsn the BSN of the medical professional
     * @return the BIG number if found, null otherwise
     */
    @Override
    public String getBigByBsn(Integer bsn) {
        Optional<MedicalProfessional> medicalProfessionalOptional = medicalProfessionalRepository.findById(bsn);
        if (medicalProfessionalOptional.isPresent()) {
            MedicalProfessional medicalProfessional = medicalProfessionalOptional.get();
            return medicalProfessional.getBig();
        }
        return null;
    }
}
