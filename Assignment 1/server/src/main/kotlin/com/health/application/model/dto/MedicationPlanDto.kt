package com.health.application.model.dto

import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.model.entity.types.DoctorEntity
import com.health.application.model.entity.types.PatientEntity
import java.sql.Date

class MedicationPlanDto(
        var id: Int?,
        var startDate: Date? = null,
        var endDate: Date? = null,
        var interval: String? = null,
        var hourInDay: Int? = null,
        var patientId: Int? = null,
        var doctorId: Int? = null
) {
    companion object {
        fun toDto(medicationPlanEntity: MedicationPlanEntity): MedicationPlanDto {
            return MedicationPlanDto(
                    id = medicationPlanEntity.id,
                    startDate = medicationPlanEntity.startDate,
                    endDate = medicationPlanEntity.endDate,
                    interval = medicationPlanEntity.interval,
                    hourInDay = medicationPlanEntity.hourInDay,
                    patientId = if (medicationPlanEntity.patient != null) medicationPlanEntity.patient!!.id else null,
                    doctorId = if (medicationPlanEntity.doctor != null) medicationPlanEntity.doctor!!.id else null
            )
        }

        fun fromDto(medicationPlanDto: MedicationPlanDto): MedicationPlanEntity {
            return MedicationPlanEntity(
                    id = medicationPlanDto.id,
                    startDate = medicationPlanDto.startDate,
                    endDate = medicationPlanDto.endDate,
                    interval = medicationPlanDto.interval,
                    hourInDay = medicationPlanDto.hourInDay,
                    patient = if (medicationPlanDto.patientId != null) PatientEntity(
                            id = medicationPlanDto.patientId
                    ) else null,
                    doctor = if (medicationPlanDto.doctorId != null) DoctorEntity(
                            id = medicationPlanDto.doctorId
                    ) else null
            )
        }
    }
}