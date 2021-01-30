package com.health.application.service

import com.health.application.model.dto.UserDto
import com.health.application.model.entity.UserEntity
import com.health.application.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse

@Service
class UserService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(UserService.javaClass)
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    fun getUserBasedOnId(id: Int, response: HttpServletResponse): UserDto? {

        LOGGER.info("Find userDto based on id: {}", id)

        var userEntity = userRepository.findById(id)
        return if (userEntity.isPresent) {
            UserDto.toDto(userEntity.get())
        } else {
            LOGGER.info("No userDto has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No user found with id: $id")
            null
        }
    }

    fun createUser(userDto: UserDto): UserDto {
        LOGGER.info("Create userDto based on DTO: {}", userDto)

        var user = UserDto.fromDto(userDto)
        user.password = passwordEncoder.encode(user.password)
        var savedUser = userRepository.save(user)
        return UserDto.toDto(savedUser)
    }

    fun updateUser(userDto: UserDto, response: HttpServletResponse) {
        LOGGER.info("Update userDto based on DTO: {}", userDto)

        var user = UserDto.fromDto(userDto)

        if (user.id == null) {
            LOGGER.info("Can not update userDto with a null id")
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Empty user id")
        } else {
            var initialUserOptional = userRepository.findById(user.id!!)
            if (!initialUserOptional.isPresent) {
                LOGGER.info("Can not update userDto with id: {}. User has not been found.", user.id)
                response.sendError(HttpStatus.BAD_REQUEST.value(), "No user found to be updated with id: " + user.id)
            } else {
                var initialUser = initialUserOptional.get()

                initialUser.address = if (user.address != null) user.address else initialUser.address
                initialUser.birthDate = if (user.birthDate != null) user.birthDate else initialUser.birthDate
                initialUser.gender = if (user.gender != null) user.gender else initialUser.gender
                initialUser.password = if (user.password != null) passwordEncoder.encode(user.password) else initialUser.password
                initialUser.personName = if (user.personName != null) user.personName else initialUser.personName
                initialUser.username = if (user.username != null) user.username else initialUser.username
                initialUser.userRole = if (user.userRole != null) user.userRole else initialUser.userRole
                initialUser.email = if (user.email != null) user.email else initialUser.email

                userRepository.save(initialUser)
            }
        }
    }

    fun deleteUser(id: Int, response: HttpServletResponse) {
        LOGGER.info("Delete userDto based on id: {}", id)

        var user = userRepository.findById(id)
        if (user.isPresent) {
            userRepository.delete(UserEntity(
                    id = id
            ))
        } else {
            LOGGER.info("No userDto has been found with the id: {}", id)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "No user found with id to be deleted: $id")
        }
    }

    fun getAllUsers(): List<UserDto> {
        LOGGER.info("Get all users")

        return userRepository
                .findAll()
                .stream()
                .map(UserDto.Companion::toDto)
                .collect(Collectors.toList())
    }

    fun findUserByUsername(username: String): UserDto {
        LOGGER.info("Get user with username: {}", username)

        return UserDto.toDto(userRepository.findByUsername(username))
    }
}