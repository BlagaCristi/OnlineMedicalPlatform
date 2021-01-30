package com.health.application.controller

import com.health.application.model.dto.UserRoleDto
import com.health.application.service.UserRoleService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.USER_ROLE_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(HEALTH_PATH + USER_ROLE_PATH)
@CrossOrigin(origins = ["*"])
class UserRoleController {

    @Autowired
    lateinit var userRoleService: UserRoleService

    @GetMapping
    fun getUserRoleBasedOnId(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ): UserRoleDto? {
        return try {
            userRoleService.getUserRoleBasedOnId(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PostMapping
    fun createUserRole(
            @RequestBody userRole: UserRoleDto,
            response: HttpServletResponse
    ): UserRoleDto? {
        return try {
            userRoleService.createUserRole(userRole)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
            null
        }
    }

    @PutMapping
    fun updateUserRole(
            @RequestBody userRole: UserRoleDto,
            response: HttpServletResponse
    ) {
        try {
            userRoleService.updateUserRole(userRole, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }

    @DeleteMapping
    fun deleteUserRole(
            @RequestParam("id", required = true) id: Int,
            response: HttpServletResponse
    ) {
        try {
            userRoleService.deleteUserRole(id, response)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")
        }
    }
}