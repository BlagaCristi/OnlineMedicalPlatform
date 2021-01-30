package com.health.application.controller

import com.health.application.model.dto.MedicationPlanDrugsDto
import com.health.application.service.MedicationPlanDrugsService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PLAN_DRUGS_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + MEDICATION_PLAN_DRUGS_PATH)
@CrossOrigin(origins = ["*"])
class MedicationPlanDrugsController {

    @Autowired
    lateinit var medicationPlanDrugsService: MedicationPlanDrugsService

    @GetMapping
    fun getMedicationPlanDrugsBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): MedicationPlanDrugsDto? {
        return try {
            medicationPlanDrugsService.getMedicationPlanDrugsBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createMedicationPlanDrugs(
            @RequestBody medicationPlanDrugs: MedicationPlanDrugsDto,
            response: HttpServletResponse
    ): MedicationPlanDrugsDto? {
        return try {
            medicationPlanDrugsService.createMedicationPlanDrugs(medicationPlanDrugs)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateMedicationPlanDrugs(
            @RequestBody medicationPlanDrugs: MedicationPlanDrugsDto,
            response: HttpServletResponse
    ) {
        try {
            medicationPlanDrugsService.updateMedicationPlanDrugs(medicationPlanDrugs, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteMedicationPlanDrugs(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            medicationPlanDrugsService.deleteMedicationPlanDrugs(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @GetMapping("/medication-plan")
    fun getMedicationPlanDrugsBasedOnPatient(
            @RequestParam("medicationPlanId", required = true) medicationPlanId: Int,
            response: HttpServletResponse
    ): List<MedicationPlanDrugsDto>? {
        return try {
            medicationPlanDrugsService.findByMedicationPlan(medicationPlanId)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }
}