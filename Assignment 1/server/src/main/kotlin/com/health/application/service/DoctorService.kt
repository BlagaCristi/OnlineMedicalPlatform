package com.health.application.service

import com.health.application.model.dto.DoctorDto
import com.health.application.model.dto.PatientDto
import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.DoctorEntity
import com.health.application.repository.DoctorRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class DoctorService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(DoctorService.javaClass)
    }

    @Autowired
    private lateinit var doctorRepository: DoctorRepository

    fun getDoctorBasedOnId(id: Int, response: HttpServletResponse): DoctorDto? {

        LOGGER.info("Find doctor based on id: {}", id)

        var doctorEntity = doctorRepository.findById(id)
        return if (doctorEntity.isPresent) {
            DoctorDto.toDto(doctorEntity.get())
        } else {
            LOGGER.info("No doctor has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No doctor found with id: $id")
            null
        }
    }

    fun createDoctor(doctorDto: DoctorDto): DoctorDto {
        LOGGER.info("Create doctor based on DTO: {}", doctorDto)

        var doctor = DoctorDto.fromDto(doctorDto)
        var savedDoctor = doctorRepository.save(doctor)
        return DoctorDto.toDto(savedDoctor)
    }

    fun updateDoctor(doctorDto: DoctorDto, response: HttpServletResponse) {
        LOGGER.info("Update doctor based on DTO: {}", doctorDto)

        var doctor = DoctorDto.fromDto(doctorDto)

        if (doctor.id == null) {
            LOGGER.info("Can not update doctor with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Can not update doctor if the id is NULL")
        } else {
            var initialDoctorOptional = doctorRepository.findById(doctor.id!!)
            if (!initialDoctorOptional.isPresent) {
                LOGGER.info("Can not update doctor with id: {}. Doctor has not been found.", doctor.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), """No doctor found with id: ${doctor.id} to be deleted""")
            } else {
                var initialDoctor = initialDoctorOptional.get()

                initialDoctor.user = if (doctor.user != null) doctor.user else initialDoctor.user

                doctorRepository.save(initialDoctor)
            }
        }
    }

    fun deleteDoctor(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete doctor based on id: {}", id)

        var doctor = doctorRepository.findById(id)
        if (doctor.isPresent) {
            doctorRepository.delete(DoctorEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No doctor has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), """No doctor found with id: ${id}to be deleted""")
        }
    }

    fun getDoctorBasedOnUserId(userId: Int, response: HttpServletResponse): DoctorDto {
        LOGGER.info("Get doctor based on user id: {}", userId)

        return DoctorDto.toDto(doctorRepository.findByUser(UserEntity(id = userId)))
    }
}