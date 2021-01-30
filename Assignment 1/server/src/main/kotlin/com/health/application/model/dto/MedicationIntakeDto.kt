package com.health.application.model.dto

import com.health.application.model.entity.MedicationIntakeEntity
import com.health.application.model.entity.types.PatientEntity
import java.sql.Timestamp

class MedicationIntakeDto(
        var id: Int? = null,
        var medicationName: String? = null,
        var isTaken: String? = null,
        var patientId: Int? = null,
        var intakeDate: Timestamp? = null
) {
    companion object {
        fun toDto(medicationIntakeEntity: MedicationIntakeEntity): MedicationIntakeDto {
            return MedicationIntakeDto(
                    id = medicationIntakeEntity.id,
                    medicationName = medicationIntakeEntity.medicationName,
                    isTaken = medicationIntakeEntity.isTaken,
                    intakeDate = medicationIntakeEntity.intakeDate,
                    patientId = if (medicationIntakeEntity.patient != null) medicationIntakeEntity.patient!!.id else null
            )
        }

        fun fromDto(medicationIntakeDto: MedicationIntakeDto): MedicationIntakeEntity {
            return MedicationIntakeEntity(
                    id = medicationIntakeDto.id,
                    medicationName = medicationIntakeDto.medicationName,
                    isTaken = medicationIntakeDto.isTaken,
                    intakeDate = medicationIntakeDto.intakeDate,
                    patient = if (medicationIntakeDto.patientId != null) PatientEntity(
                            id = medicationIntakeDto.patientId
                    ) else null
            )
        }
    }
}