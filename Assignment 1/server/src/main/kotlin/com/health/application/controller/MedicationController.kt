package com.health.application.controller

import com.health.application.model.dto.MedicationDto
import com.health.application.service.MedicationService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + MEDICATION_PATH)
@CrossOrigin(origins = ["*"])
class MedicationController {

    @Autowired
    lateinit var medicationService: MedicationService

    @GetMapping
    fun getMedicationBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): MedicationDto? {
        return try {
            medicationService.getMedicationBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createMedication(
            @RequestBody medication: MedicationDto,
            response: HttpServletResponse
    ): MedicationDto? {
        return try {
            medicationService.createMedication(medication)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateMedication(
            @RequestBody medication: MedicationDto,
            response: HttpServletResponse
    ) {
        try {
            medicationService.updateMedication(medication, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteMedication(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            medicationService.deleteMedication(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }


    @GetMapping("/all")
    fun getAllUsers(
            response: HttpServletResponse
    ): List<MedicationDto>? {
        return try {
            medicationService.getAllMedications()
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }
}