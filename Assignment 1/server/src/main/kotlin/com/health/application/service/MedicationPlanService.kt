package com.health.application.service

import com.health.application.model.dto.MedicationPlanDto
import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.model.entity.types.PatientEntity
import com.health.application.repository.MedicationPlanRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse

@Service
class MedicationPlanService {

    companion object {
        val LOGGER = LoggerFactory.getLogger(MedicationPlanService.javaClass)
    }

    @Autowired
    private lateinit var medicationPlanRepository: MedicationPlanRepository

    fun getMedicationPlanBasedOnId(id: Int, response: HttpServletResponse): MedicationPlanDto? {

        LOGGER.info("Find medicationPlan based on id: {}", id)

        var medicationPlanEntity = medicationPlanRepository.findById(id)
        return if (medicationPlanEntity.isPresent) {
            MedicationPlanDto.toDto(medicationPlanEntity.get())
        } else {
            LOGGER.info("No medicationPlan has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan found with id: $id")
            null
        }
    }

    fun createMedicationPlan(medicationPlanDto: MedicationPlanDto): MedicationPlanDto {
        LOGGER.info("Create medicationPlan based on DTO: {}", medicationPlanDto)

        var medicationPlan = MedicationPlanDto.fromDto(medicationPlanDto)
        var savedMedicationPlan = medicationPlanRepository.save(medicationPlan)
        return MedicationPlanDto.toDto(savedMedicationPlan)
    }

    fun updateMedicationPlan(medicationPlanDto: MedicationPlanDto, response: HttpServletResponse) {
        LOGGER.info("Update medicationPlan based on DTO: {}", medicationPlanDto)

        var medicationPlan = MedicationPlanDto.fromDto(medicationPlanDto)

        if (medicationPlan.id == null) {
            LOGGER.info("Can not update medicationPlan with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan can be found with NULL id")
        } else {
            var initialMedicationPlanOptional = medicationPlanRepository.findById(medicationPlan.id!!)
            if (!initialMedicationPlanOptional.isPresent) {
                LOGGER.info("Can not update medicationPlan with id: {}. MedicationPlan has not been found.", medicationPlan.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan found with id: ${medicationPlan.id} to be updated")
            } else {
                var initialMedicationPlan = initialMedicationPlanOptional.get()

                initialMedicationPlan.doctor = if (medicationPlan.doctor != null) medicationPlan.doctor else initialMedicationPlan.doctor
                initialMedicationPlan.endDate = if (medicationPlan.endDate != null) medicationPlan.endDate else initialMedicationPlan.endDate
                initialMedicationPlan.interval = if (medicationPlan.interval != null) medicationPlan.interval else initialMedicationPlan.interval
                initialMedicationPlan.startDate = if (medicationPlan.startDate != null) medicationPlan.startDate else initialMedicationPlan.startDate
                initialMedicationPlan.hourInDay = if (medicationPlan.hourInDay != null) medicationPlan.hourInDay else initialMedicationPlan.hourInDay
                initialMedicationPlan.patient = if (medicationPlan.patient != null) medicationPlan.patient else initialMedicationPlan.patient

                medicationPlanRepository.save(initialMedicationPlan)
            }
        }
    }

    fun deleteMedicationPlan(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete medicationPlan based on id: {}", id)

        var medicationPlan = medicationPlanRepository.findById(id)
        if (medicationPlan.isPresent) {
            medicationPlanRepository.delete(MedicationPlanEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No medicationPlan has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No medication plan found with id: $id to be deleted")
        }
    }

    fun findByPatient(patientId: Int): List<MedicationPlanDto> {
        LOGGER.info("Find medicationPlan by patient id: {}", patientId)

        return medicationPlanRepository.findByPatient(PatientEntity(id = patientId))
                .stream()
                .map { MedicationPlanDto.toDto(it) }
                .collect(Collectors.toList())
    }
}