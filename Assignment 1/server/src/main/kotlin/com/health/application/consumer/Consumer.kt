package com.health.application.consumer

import com.health.application.model.dto.ActivityDto
import com.health.application.repository.ActivityPatientRepository
import com.health.application.repository.PatientRepository
import com.health.application.util.AppProperties.Companion.ACTIVITY_LEAVING
import com.health.application.util.AppProperties.Companion.ACTIVITY_SHOWERING
import com.health.application.util.AppProperties.Companion.ACTIVITY_SLEEPING
import com.health.application.util.AppProperties.Companion.ACTIVITY_TOILETING
import com.health.application.util.AppProperties.Companion.ONE_HOUR
import com.health.application.util.AppProperties.Companion.TWELVE_HOURS
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class Consumer {

    companion object {
        val LOGGER = LoggerFactory.getLogger(Consumer.javaClass)
    }

    @Autowired
    private lateinit var activityPatientRepository: ActivityPatientRepository

    @Autowired
    private lateinit var template: SimpMessagingTemplate

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @RabbitListener(queues = ["\${jsa.rabbitmq.queue}"], containerFactory = "jsaFactory")
    fun receivedMessage(activityDto: ActivityDto) {
        LOGGER.info("Received Message: {}", activityDto)

        checkForIssues(activityDto)
    }

    private fun checkForIssues(activityDto: ActivityDto) {

        var isNormal: Boolean = true
        if (activityDto.activity.equals(ACTIVITY_SLEEPING)) {
            var elapsedTime = activityDto.endTime!!.time - activityDto.startTime!!.time

            if (elapsedTime >= TWELVE_HOURS) {
                var activityPatient = ActivityDto.fromDto(activityDto)
                activityPatient.isNormal = "false"
                activityPatientRepository.save(activityPatient)

                isNormal = false

                notifyCaregiver(activityDto.patientId!!, """The patient with id ${activityDto.patientId!!} has slept for more than 12 hours!""")
            }
        }

        if (activityDto.activity.equals(ACTIVITY_LEAVING)) {
            var elapsedTime = activityDto.endTime!!.time - activityDto.startTime!!.time

            if (elapsedTime >= TWELVE_HOURS) {
                var activityPatient = ActivityDto.fromDto(activityDto)
                activityPatient.isNormal = "false"
                activityPatientRepository.save(activityPatient)

                isNormal = false
                notifyCaregiver(activityDto.patientId!!, """The patient with id ${activityDto.patientId!!} has left his house for more than 12 hours!""")
            }
        }

        if (activityDto.activity.equals(ACTIVITY_SHOWERING) || activityDto.activity.equals(ACTIVITY_TOILETING)) {
            var elapsedTime = activityDto.endTime!!.time - activityDto.startTime!!.time

            if (elapsedTime >= ONE_HOUR) {
                var activityPatient = ActivityDto.fromDto(activityDto)
                activityPatient.isNormal = "false"
                activityPatientRepository.save(activityPatient)

                isNormal = false
                notifyCaregiver(activityDto.patientId!!, """The patient with id ${activityDto.patientId!!} has been doing showering stuff for more than one hour!""")
            }
        }
        if (isNormal) {
            var activityPatient = ActivityDto.fromDto(activityDto)
            activityPatient.isNormal = "true"
            activityPatientRepository.save(activityPatient)

        }
    }

    private fun notifyCaregiver(patientId: Int, message: String) {

        var patient = patientRepository.findById(patientId)

        if (patient.isPresent) {

            var caregiverId = patient.get().caregiver!!.id!!

            LOGGER.info("Notify caregiver {} about patient {} with message: {}", patientId, caregiverId, message)

            template.convertAndSend("/topic/caregiver-$caregiverId", message)
        }
    }
}