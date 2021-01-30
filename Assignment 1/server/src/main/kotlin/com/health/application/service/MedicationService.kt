package com.health.application.service

import com.health.application.model.dto.MedicationDto
import com.health.application.model.entity.MedicationEntity
import com.health.application.repository.MedicationRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse

@Service
class MedicationService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(MedicationService.javaClass)
    }

    @Autowired
    private lateinit var medicationRepository: MedicationRepository

    fun getMedicationBasedOnId(id: Int, response: HttpServletResponse): MedicationDto? {

        LOGGER.info("Find medication based on id: {}", id)

        var medicationEntity = medicationRepository.findById(id)
        return if (medicationEntity.isPresent) {
            MedicationDto.toDto(medicationEntity.get())
        } else {
            LOGGER.info("No medication has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication found with id: $id")
            null
        }
    }

    fun createMedication(medicationDto: MedicationDto): MedicationDto {
        LOGGER.info("Create medication based on DTO: {}", medicationDto)

        var medication = MedicationDto.fromDto(medicationDto)
        var savedMedication = medicationRepository.save(medication)
        return MedicationDto.toDto(savedMedication)
    }

    fun updateMedication(medicationDto: MedicationDto, response: HttpServletResponse) {
        LOGGER.info("Update medication based on DTO: {}", medicationDto)

        var medication = MedicationDto.fromDto(medicationDto)

        if (medication.id == null) {
            LOGGER.info("Can not update medication with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication can be found with NULL id")
        } else {
            var initialMedicationOptional = medicationRepository.findById(medication.id!!)
            if (!initialMedicationOptional.isPresent) {
                LOGGER.info("Can not update medication with id: {}. Medication has not been found.", medication.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication found with id: ${medication.id} to be updated")
            } else {
                var initialMedication = initialMedicationOptional.get()

                initialMedication.dosage = if (medication.dosage != null) medication.dosage else initialMedication.dosage
                initialMedication.name = if (medication.name != null) medication.name else initialMedication.name
                initialMedication.sideEffect = if (medication.sideEffect != null) medication.sideEffect else initialMedication.sideEffect

                medicationRepository.save(initialMedication)
            }
        }
    }

    fun deleteMedication(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete medication based on id: {}", id)

        var medication = medicationRepository.findById(id)
        if (medication.isPresent) {
            medicationRepository.delete(MedicationEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No medication has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication found with id: $id to be deleted")
        }
    }

    fun getAllMedications(): List<MedicationDto> {
        LOGGER.info("Get all medications")

        return medicationRepository
                .findAll()
                .stream()
                .map(MedicationDto.Companion::toDto)
                .collect(Collectors.toList())
    }
}