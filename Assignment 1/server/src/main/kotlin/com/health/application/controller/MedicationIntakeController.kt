package com.health.application.controller

import com.health.application.model.dto.MedicationIntakeDto
import com.health.application.service.MedicationIntakeService
import com.health.application.util.AppProperties.Companion.HEALTH_PATH
import com.health.application.util.AppProperties.Companion.MEDICATION_INTAKE_PATH
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping(HEALTH_PATH + MEDICATION_INTAKE_PATH)
@CrossOrigin(origins = ["*"])
class MedicationIntakeController {

    @Autowired
    lateinit var medicationIntakeService: MedicationIntakeService

    @PostMapping
    fun createMedicationIntake(
            @RequestBody medicationIntakeDto: MedicationIntakeDto,
            response: HttpServletResponse
    ) {
        try {
            medicationIntakeService.insertMedicationIntake(medicationIntakeDto)
        } catch (ex: PSQLException) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "PostgreSQL exception occurred!")

        }
    }
}