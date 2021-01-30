package com.health.application.controller

import com.health.application.model.dto.UserDto
import com.health.application.service.UserService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.USER_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin(origins = ["*"])
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping(HEALTH_PATH + USER_PATH + "/all")
    fun getAllUsers(): List<UserDto> {
        return userService.getAllUsers()
    }

    @GetMapping(HEALTH_PATH + USER_PATH + "/username")
    fun getUserBasedOnUsername(
            @RequestParam("username", required = true) username: String,
            response: HttpServletResponse
    ): UserDto? {
        return try {
            userService.findUserByUsername(username)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @GetMapping(HEALTH_PATH + USER_PATH)
    fun getUserBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): UserDto? {
        return try {
            userService.getUserBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping(HEALTH_PATH + USER_PATH)
    fun createUser(
            @RequestBody user: UserDto,
            response: HttpServletResponse
    ): UserDto? {
        return try {
            userService.createUser(user)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping(HEALTH_PATH + USER_PATH)
    fun updateUser(
            @RequestBody user: UserDto,
            response: HttpServletResponse
    ) {
        try {
            userService.updateUser(user, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping(HEALTH_PATH + USER_PATH)
    fun deleteUser(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            userService.deleteUser(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }
}