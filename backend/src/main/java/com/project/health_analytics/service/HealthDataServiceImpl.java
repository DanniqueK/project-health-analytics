package com.project.health_analytics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.health_analytics.model.HealthData;
import com.project.health_analytics.repository.HealthDataRepository;

@Service
public class HealthDataServiceImpl implements HealthDataService {

    @Autowired
    private HealthDataRepository healthDataRepository;

    @Override
    public List<HealthData> getHealthDataByPatientBsn(Integer bsn) {
        return healthDataRepository.findByPatientBsn(bsn);
    }
}