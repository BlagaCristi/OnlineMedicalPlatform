package com.health.application.controller

import com.health.application.model.dto.DoctorDto
import com.health.application.service.DoctorService
import com.health.application.util.AppProperties.Companion.DOCTOR_PATH
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + DOCTOR_PATH)
@CrossOrigin(origins = ["*"])
class DoctorController {

    @Autowired
    lateinit var doctorService: DoctorService

    @GetMapping
    fun getDoctorBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): DoctorDto? {
        return try {
            doctorService.getDoctorBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createDoctor(
            @RequestBody doctor: DoctorDto,
            response: HttpServletResponse
    ): DoctorDto? {
        return try {
            doctorService.createDoctor(doctor)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateDoctor(
            @RequestBody doctor: DoctorDto,
            response: HttpServletResponse
    ) {
        try {
            doctorService.updateDoctor(doctor, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteDoctor(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            doctorService.deleteDoctor(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @GetMapping("/user")
    fun getDoctorBasedOnUserId(
            @RequestParam("userId", required = true) userId: Int,
            response: HttpServletResponse
    ): DoctorDto? {
        return try {
            doctorService.getDoctorBasedOnUserId(userId, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }
}