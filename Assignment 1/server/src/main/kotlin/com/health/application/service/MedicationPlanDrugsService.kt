package com.health.application.service

import com.health.application.model.dto.MedicationPlanDrugsDto
import com.health.application.model.entity.MedicationPlanDrugsEntity
import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.repository.MedicationPlanDrugsRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse

@Service
class MedicationPlanDrugsService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(MedicationPlanDrugsService.javaClass)
    }

    @Autowired
    private lateinit var medicationPlanDrugsRepository: MedicationPlanDrugsRepository

    fun getMedicationPlanDrugsBasedOnId(id: Int, response: HttpServletResponse): MedicationPlanDrugsDto? {

        LOGGER.info("Find medicationPlanDrugs based on id: {}", id)

        var medicationPlanDrugsEntity = medicationPlanDrugsRepository.findById(id)
        return if (medicationPlanDrugsEntity.isPresent) {
            MedicationPlanDrugsDto.toDto(medicationPlanDrugsEntity.get())
        } else {
            LOGGER.info("No medicationPlanDrugs has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan drugs found with id: $id")
            null
        }
    }

    fun createMedicationPlanDrugs(medicationPlanDrugsDto: MedicationPlanDrugsDto): MedicationPlanDrugsDto {
        LOGGER.info("Create medicationPlanDrugs based on DTO: {}", medicationPlanDrugsDto)

        var medicationPlanDrugs = MedicationPlanDrugsDto.fromDto(medicationPlanDrugsDto)
        var savedMedicationPlanDrugs = medicationPlanDrugsRepository.save(medicationPlanDrugs)
        return MedicationPlanDrugsDto.toDto(savedMedicationPlanDrugs)
    }

    fun updateMedicationPlanDrugs(medicationPlanDrugsDto: MedicationPlanDrugsDto, response: HttpServletResponse) {
        LOGGER.info("Update medicationPlanDrugs based on DTO: {}", medicationPlanDrugsDto)

        var medicationPlanDrugs = MedicationPlanDrugsDto.fromDto(medicationPlanDrugsDto)

        if (medicationPlanDrugs.id == null) {
            LOGGER.info("Can not update medicationPlanDrugs with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Can not update medication plan drugs with a null id")
        } else {
            var initialMedicationPlanDrugsOptional = medicationPlanDrugsRepository.findById(medicationPlanDrugs.id!!)
            if (!initialMedicationPlanDrugsOptional.isPresent) {
                LOGGER.info("Can not update medicationPlanDrugs with id: {}. MedicationPlanDrugs has not been found.", medicationPlanDrugs.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Can not find medication plan  drugswith id: " + medicationPlanDrugs.id + " to update")
            } else {
                var initialMedicationPlanDrugs = initialMedicationPlanDrugsOptional.get()

                initialMedicationPlanDrugs.medication = if (medicationPlanDrugs.medication != null) medicationPlanDrugs.medication else initialMedicationPlanDrugs.medication
                initialMedicationPlanDrugs.medicationPlan = if (medicationPlanDrugs.medicationPlan != null) medicationPlanDrugs.medicationPlan else initialMedicationPlanDrugs.medicationPlan

                medicationPlanDrugsRepository.save(initialMedicationPlanDrugs)
            }
        }
    }

    fun deleteMedicationPlanDrugs(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete medicationPlanDrugs based on id: {}", id)

        var medicationPlanDrugs = medicationPlanDrugsRepository.findById(id)
        if (medicationPlanDrugs.isPresent) {
            medicationPlanDrugsRepository.delete(MedicationPlanDrugsEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No medicationPlanDrugs has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan drugs found with id: $id to be deleted")
        }
    }

    fun findByMedicationPlan(medicationPlanId: Int): List<MedicationPlanDrugsDto> {
        LOGGER.info("Find medicationPlanDrugs by medication plan id: {}", medicationPlanId)

        return medicationPlanDrugsRepository.findByMedicationPlan(MedicationPlanEntity(id = medicationPlanId))
                .stream()
                .map { MedicationPlanDrugsDto.toDto(it) }
                .collect(Collectors.toList())
    }
}