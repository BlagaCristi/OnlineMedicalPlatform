package com.health.application.controller

import com.health.application.model.dto.CaregiverDto
import com.health.application.service.CaregiverService
import com.health.application.util.AppProperties.Companion.CAREGIVER_PATH
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + CAREGIVER_PATH)
@CrossOrigin(origins = ["*"])
class CaregiverController {

    @Autowired
    lateinit var caregiverService: CaregiverService

    @GetMapping
    fun getCaregiverBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): CaregiverDto? {
        return try {
            caregiverService.getCaregiverBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createCaregiver(
            @RequestBody caregiver: CaregiverDto,
            response: HttpServletResponse
    ): CaregiverDto? {
        return try {
            caregiverService.createCaregiver(caregiver)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateCaregiver(
            @RequestBody caregiver: CaregiverDto,
            response: HttpServletResponse
    ) {
        try {
            caregiverService.updateCaregiver(caregiver, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteCaregiver(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            caregiverService.deleteCaregiver(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @GetMapping("/user")
    fun getCaregiverBasedOnUserId(
            @RequestParam("userId", required = true) userId: Int,
            response: HttpServletResponse
    ): CaregiverDto? {
        return try {
            caregiverService.getCaregiverBasedOnUserId(userId, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }
}