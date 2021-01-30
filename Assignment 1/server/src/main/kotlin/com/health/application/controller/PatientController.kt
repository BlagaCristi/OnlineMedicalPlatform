package com.health.application.controller

import com.health.application.model.dto.PatientDto
import com.health.application.model.dto.RecommendationDto
import com.health.application.service.PatientService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.PATIENT_PATH
import ds.health.soa.soa_endpoints.GetPatientRecommendationResponse
import ds.health.soa.soa_endpoints.MedicationIntake
import ds.health.soa.soa_endpoints.PatientActivity
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + PATIENT_PATH)
@CrossOrigin(origins = ["*"])
class PatientController {

    @Autowired
    lateinit var patientService: PatientService

    @GetMapping
    fun getPatientBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): PatientDto? {
        return try {
            patientService.getPatientBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createPatient(
            @RequestBody patient: PatientDto,
            response: HttpServletResponse
    ): PatientDto? {
        return try {
            patientService.createPatient(patient)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updatePatient(
            @RequestBody patient: PatientDto,
            response: HttpServletResponse
    ) {
        try {
            patientService.updatePatient(patient, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deletePatient(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            patientService.deletePatient(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @GetMapping("/user")
    fun getPatientBasedOnUserId(
            @RequestParam("userId", required = true) userId: Int,
            response: HttpServletResponse
    ): PatientDto? {
        return try {
            patientService.getPatientBasedOnUserId(userId, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @GetMapping("/caregiver")
    fun getPatientsBasedOnCaregiverId(
            @RequestParam("caregiverId", required = true) caregiverId: Int,
            response: HttpServletResponse
    ): List<PatientDto>? {
        return try {
            patientService.getPatientsBasedOnCaregiverId(caregiverId)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @GetMapping("/activity")
    fun getPatientActivityBasedOnId(
            @RequestParam("patientId", required = true) patientId: Int
    ): List<PatientActivity> {
        return patientService.getPatientActivity(patientId)
    }

    @GetMapping("/medication-intake")
    fun getPatientMedicationIntakeBasedOnId(
            @RequestParam("patientId", required = true) patientId: Int
    ): List<MedicationIntake> {
        return patientService.getPatientMedicationIntake(patientId)
    }

    @GetMapping("/recommendation")
    fun getPatientRecommendationBasedOnId(
            @RequestParam("patientId", required = true) patientId: Int
    ): GetPatientRecommendationResponse {
        return patientService.getPatientRecommendation(patientId)
    }

    @PutMapping("/recommendation")
    fun setPatientRecommendationBasedOnId(
            @RequestParam("patientId", required = true) patientId: Int,
            @RequestBody recommendationDto: RecommendationDto
    ) {
        patientService.setPatientRecommendation(patientId, recommendationDto)
    }
}