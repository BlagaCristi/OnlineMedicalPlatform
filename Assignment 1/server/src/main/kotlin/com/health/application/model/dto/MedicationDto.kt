package com.health.application.model.dto

import com.health.application.model.entity.MedicationEntity

class MedicationDto(
        var id: Int? = null,
        var name: String? = null,
        var sideEffect: String? = null,
        var dosage: String? = null
) {
    companion object {
        fun toDto(medicationEntity: MedicationEntity): MedicationDto {
            return MedicationDto(
                    id = medicationEntity.id,
                    name = medicationEntity.name,
                    sideEffect = medicationEntity.sideEffect,
                    dosage = medicationEntity.dosage
            )
        }

        fun fromDto(medicationDTO: MedicationDto): MedicationEntity {
            return MedicationEntity(
                    id = medicationDTO.id,
                    name = medicationDTO.name,
                    sideEffect = medicationDTO.sideEffect,
                    dosage = medicationDTO.dosage
            )
        }
    }
}