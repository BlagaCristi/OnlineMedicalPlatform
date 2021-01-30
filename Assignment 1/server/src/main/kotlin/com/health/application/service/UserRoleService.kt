package com.health.application.service

import com.health.application.model.dto.UserRoleDto
import com.health.application.model.entity.UserRoleEntity
import com.health.application.repository.UserRoleRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class UserRoleService {

    companion object {
        val LOGGER = LoggerFactory.getLogger(UserRoleService.javaClass)
    }

    @Autowired
    private lateinit var userRoleRepository: UserRoleRepository

    fun getUserRoleBasedOnId(id: Int, response: HttpServletResponse): UserRoleDto? {

        LOGGER.info("Find userRole based on id: {}", id)

        var userRoleEntity = userRoleRepository.findById(id)
        return if (userRoleEntity.isPresent) {
            UserRoleDto.toDto(userRoleEntity.get())
        } else {
            LOGGER.info("No userRole has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No userRole found with id: $id")
            null
        }
    }

    fun createUserRole(userRoleDto: UserRoleDto): UserRoleDto {
        LOGGER.info("Create userDto Role based on DTO: {}", userRoleDto)

        var userRole = UserRoleDto.fromDto(userRoleDto)
        var savedUserRole = userRoleRepository.save(userRole)
        return UserRoleDto.toDto(savedUserRole)
    }

    fun updateUserRole(userRoleDto: UserRoleDto, response: HttpServletResponse) {
        LOGGER.info("Update userRole based on DTO: {}", userRoleDto)

        var userRole = UserRoleDto.fromDto(userRoleDto)

        if (userRole.id == null) {
            LOGGER.info("Can not update userRole with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No userRole can be found with NULL id")
        } else {
            var initialUserRoleOptional = userRoleRepository.findById(userRole.id!!)
            if (!initialUserRoleOptional.isPresent) {
                LOGGER.info("Can not update userRole with id: {}. UserRole has not been found.", userRole.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No userRole found with id: ${userRole.id} to be updated")
            } else {
                var initialUserRole = initialUserRoleOptional.get()

                initialUserRole.roleName = if (userRole.roleName != null) userRole.roleName else initialUserRole.roleName

                userRoleRepository.save(initialUserRole)
            }
        }
    }

    fun deleteUserRole(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete userRole based on id: {}", id)

        var userRole = userRoleRepository.findById(id)
        if (userRole.isPresent) {
            userRoleRepository.delete(UserRoleEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No userRole has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No userRole found with id: $id to be deleted")
        }
    }
}