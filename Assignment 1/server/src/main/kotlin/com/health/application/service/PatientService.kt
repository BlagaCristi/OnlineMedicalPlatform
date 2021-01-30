package com.health.application.service

import com.health.application.model.dto.PatientDto
import com.health.application.model.dto.RecommendationDto
import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.CaregiverEntity
import com.health.application.model.entity.types.PatientEntity
import com.health.application.repository.PatientRepository
import com.health.application.soa.CaregiverClient
import com.health.application.soa.DoctorClient
import ds.health.soa.soa_endpoints.GetPatientRecommendationResponse
import ds.health.soa.soa_endpoints.MedicationIntake
import ds.health.soa.soa_endpoints.PatientActivity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse

@Service
class PatientService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(PatientService.javaClass)
    }

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @Autowired
    private lateinit var doctorClient: DoctorClient

    @Autowired
    private lateinit var caregiverClient: CaregiverClient

    fun getPatientBasedOnId(id: Int, response: HttpServletResponse): PatientDto? {

        LOGGER.info("Find patient based on id: {}", id)

        var patientEntity = patientRepository.findById(id)
        return if (patientEntity.isPresent) {
            PatientDto.toDto(patientEntity.get())
        } else {
            LOGGER.info("No patient has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No patient found with id: $id")
            null
        }
    }

    fun createPatient(patientDto: PatientDto): PatientDto {
        LOGGER.info("Create patient based on DTO: {}", patientDto)

        var patient = PatientDto.fromDto(patientDto)
        var savedPatient = patientRepository.save(patient)
        return PatientDto.toDto(savedPatient)
    }

    fun updatePatient(patientDto: PatientDto, response: HttpServletResponse) {
        LOGGER.info("Update patient based on DTO: {}", patientDto)

        var patient = PatientDto.fromDto(patientDto)

        if (patient.id == null) {
            LOGGER.info("Can not update patient with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No patient can be found with NULL id")
        } else {
            var initialPatientOptional = patientRepository.findById(patient.id!!)
            if (!initialPatientOptional.isPresent) {
                LOGGER.info("Can not update patient with id: {}. Patient has not been found.", patient.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No patient found with id: ${patient.id} to be updated")
            } else {
                var initialPatient = initialPatientOptional.get()

                initialPatient.caregiver = if (patient.caregiver != null) patient.caregiver else initialPatient.caregiver
                initialPatient.medicalRecord = if (patient.medicalRecord != null) patient.medicalRecord else initialPatient.medicalRecord
                initialPatient.user = if (patient.user != null) patient.user else initialPatient.user

                patientRepository.save(initialPatient)
            }
        }
    }

    fun deletePatient(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete patient based on id: {}", id)

        var patient = patientRepository.findById(id)
        if (patient.isPresent) {
            patientRepository.delete(PatientEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No patient has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No patient found with id: $id to be deleted")
        }
    }

    fun getPatientBasedOnUserId(userId: Int, response: HttpServletResponse): PatientDto {
        LOGGER.info("Get patient based on user id: {}", userId)

        return PatientDto.toDto(patientRepository.findByUser(UserEntity(id = userId)))
    }

    fun getPatientsBasedOnCaregiverId(caregiverId: Int): List<PatientDto> {
        LOGGER.info("Get patient based on caregiver id: {}", caregiverId)

        return patientRepository.findByCaregiver(CaregiverEntity(id = caregiverId))
                .stream()
                .map(PatientDto.Companion::toDto)
                .collect(Collectors.toList())
    }

    fun getPatientActivity(patientId: Int): List<PatientActivity> {
        return doctorClient.getPatientActivity(patientId).patientActivityList.patientActivity
    }

    fun getPatientMedicationIntake(patientId: Int): List<MedicationIntake> {
        return doctorClient.getPatientMedicationIntake(patientId).medicationIntakeList.medicationIntake
    }

    fun getPatientRecommendation(patientId: Int): GetPatientRecommendationResponse {
        return caregiverClient.getPatientRecommendation(patientId)
    }

    fun setPatientRecommendation(patientId: Int, recommendationDto: RecommendationDto) {
        doctorClient.setPatientRecommendation(patientId, if (recommendationDto.recommendation == null) "" else recommendationDto.recommendation!!)
    }
}