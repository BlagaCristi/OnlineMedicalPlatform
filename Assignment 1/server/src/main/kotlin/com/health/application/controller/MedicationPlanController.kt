package com.health.application.controller

import com.health.application.model.dto.MedicationPlanDto
import com.health.application.service.MedicationPlanService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PLAN_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + MEDICATION_PLAN_PATH)
@CrossOrigin(origins = ["*"])
class MedicationPlanController {

    @Autowired
    lateinit var medicationPlanService: MedicationPlanService

    @GetMapping
    fun getMedicationPlanBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): MedicationPlanDto? {
        return try {
            medicationPlanService.getMedicationPlanBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createMedicationPlan(
            @RequestBody medicationPlan: MedicationPlanDto,
            response: HttpServletResponse
    ): MedicationPlanDto? {
        return try {
            medicationPlanService.createMedicationPlan(medicationPlan)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateMedicationPlan(
            @RequestBody medicationPlan: MedicationPlanDto,
            response: HttpServletResponse
    ) {
        try {
            medicationPlanService.updateMedicationPlan(medicationPlan, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteMedicationPlan(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            medicationPlanService.deleteMedicationPlan(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @GetMapping("/patient")
    fun getMedicationPlanBasedOnPatient(
            @RequestParam("patientId", required = true) patientId: Int,
            response: HttpServletResponse
    ): List<MedicationPlanDto>? {
        return try {
            medicationPlanService.findByPatient(patientId)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }
}