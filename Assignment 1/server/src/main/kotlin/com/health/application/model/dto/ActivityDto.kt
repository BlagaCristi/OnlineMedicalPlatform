package com.health.application.model.dto

import com.health.application.model.entity.ActivityPatientEntity
import com.health.application.model.entity.types.PatientEntity
import java.sql.Timestamp

class ActivityDto(
        var patientId: Int? = null,
        var startTime: Timestamp? = null,
        var endTime: Timestamp? = null,
        var activity: String? = null,
        var is_normal: String? = null
) {
    override fun toString(): String {
        var stringBuilder = StringBuilder()
        stringBuilder.append(patientId)
        stringBuilder.append(" $startTime")
        stringBuilder.append(" $endTime")
        stringBuilder.append(" $activity")
        return stringBuilder.toString()
    }

    companion object {
        fun fromDto(activityDto: ActivityDto): ActivityPatientEntity {
            return ActivityPatientEntity(
                    patientEntity = PatientEntity(
                            id = activityDto.patientId
                    ),
                    startTime = activityDto.startTime,
                    endTime = activityDto.endTime,
                    activity = activityDto.activity,
                    isNormal = activityDto.is_normal
            )
        }
    }
}