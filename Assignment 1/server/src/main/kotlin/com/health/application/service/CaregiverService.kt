package com.health.application.service

import com.health.application.model.dto.CaregiverDto
import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.CaregiverEntity
import com.health.application.repository.CaregiverRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class CaregiverService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(CaregiverService.javaClass)
    }

    @Autowired
    private lateinit var caregiverRepository: CaregiverRepository

    fun getCaregiverBasedOnId(id: Int, response: HttpServletResponse): CaregiverDto? {

        LOGGER.info("Find caregiver based on id: {}", id)

        var caregiverEntity = caregiverRepository.findById(id)
        return if (caregiverEntity.isPresent) {
            CaregiverDto.toDto(caregiverEntity.get())
        } else {
            LOGGER.info("No caregiver has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No caregiver found with id: $id")
            null
        }
    }

    fun createCaregiver(caregiverDto: CaregiverDto): CaregiverDto {
        LOGGER.info("Create caregiver based on DTO: {}", caregiverDto)

        var caregiver = CaregiverDto.fromDto(caregiverDto)
        var savedCaregiver = caregiverRepository.save(caregiver)
        return CaregiverDto.toDto(savedCaregiver)
    }

    fun updateCaregiver(caregiverDto: CaregiverDto, response: HttpServletResponse) {
        LOGGER.info("Update caregiver based on DTO: {}", caregiverDto)

        var caregiver = CaregiverDto.fromDto(caregiverDto)

        if (caregiver.id == null) {
            LOGGER.info("Can not update caregiver with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Can not update caregiver if given id is null")
        } else {
            var initialCaregiverOptional = caregiverRepository.findById(caregiver.id!!)
            if (!initialCaregiverOptional.isPresent) {
                LOGGER.info("Can not update caregiver with id: {}. Caregiver has not been found.", caregiver.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), """No caregiver found with id: ${caregiver.id}to be updated""")
            } else {
                var initialCaregiver = initialCaregiverOptional.get()

                initialCaregiver.user = if (caregiver.user != null) caregiver.user else initialCaregiver.user

                caregiverRepository.save(initialCaregiver)
            }
        }
    }

    fun deleteCaregiver(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete caregiver based on id: {}", id)

        var caregiver = caregiverRepository.findById(id)
        if (caregiver.isPresent) {
            caregiverRepository.delete(CaregiverEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No caregiver has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No caregiver found with id: $id to be deleted")
        }
    }

    fun getCaregiverBasedOnUserId(userId: Int, response: HttpServletResponse): CaregiverDto {
        LOGGER.info("Get caregiver based on user id: {}", userId)

        return CaregiverDto.toDto(caregiverRepository.findByUser(UserEntity(id = userId)))
    }
}