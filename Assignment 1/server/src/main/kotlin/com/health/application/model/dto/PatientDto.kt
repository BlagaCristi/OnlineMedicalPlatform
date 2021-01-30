package com.health.application.model.dto

import com.health.application.model.entity.types.CaregiverEntity
import com.health.application.model.entity.types.PatientEntity

class PatientDto(
        var id: Int? = null,
        var medicalRecord: String? = null,
        var userDto: UserDto? = null,
        var caregiverId: Int? = null
) {
    companion object {
        fun toDto(patientEntity: PatientEntity): PatientDto {
            return PatientDto(
                    id = patientEntity.id,
                    medicalRecord = patientEntity.medicalRecord,
                    caregiverId = if (patientEntity.caregiver != null) patientEntity.caregiver!!.id else null,
                    userDto = if (patientEntity.user != null) UserDto.toDto(patientEntity.user!!) else null
            )
        }

        fun fromDto(patientDto: PatientDto): PatientEntity {
            return PatientEntity(
                    id = patientDto.id,
                    medicalRecord = patientDto.medicalRecord,
                    user = if (patientDto.userDto != null) UserDto.fromDto(patientDto.userDto!!) else null,
                    caregiver = if (patientDto.caregiverId != null) CaregiverEntity(
                            id = patientDto.caregiverId
                    ) else null
            )
        }
    }
}