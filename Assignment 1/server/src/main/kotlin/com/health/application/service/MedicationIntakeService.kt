package com.health.application.service

import com.health.application.model.dto.MedicationIntakeDto
import com.health.application.repository.MedicationIntakeRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MedicationIntakeService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(CaregiverService.javaClass)
    }

    @Autowired
    private lateinit var medicationIntakeRepository: MedicationIntakeRepository

    fun insertMedicationIntake(medicationIntakeDto: MedicationIntakeDto) {

        LOGGER.info("Save medication intake for patient {}", medicationIntakeDto.patientId)

        medicationIntakeRepository.save(MedicationIntakeDto.fromDto(medicationIntakeDto))
    }

}