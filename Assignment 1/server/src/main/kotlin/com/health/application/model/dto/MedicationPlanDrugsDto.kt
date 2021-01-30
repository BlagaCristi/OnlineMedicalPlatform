package com.health.application.model.dto

import com.health.application.model.entity.MedicationEntity
import com.health.application.model.entity.MedicationPlanDrugsEntity
import com.health.application.model.entity.MedicationPlanEntity

class MedicationPlanDrugsDto(
        var id: Int? = null,
        var medicationPlanId: Int? = null,
        var medicationId: Int? = null
) {
    companion object {
        fun toDto(medicationPlanDrugsEntity: MedicationPlanDrugsEntity): MedicationPlanDrugsDto {
            return MedicationPlanDrugsDto(
                    id = medicationPlanDrugsEntity.id,
                    medicationPlanId = if (medicationPlanDrugsEntity.medicationPlan != null) medicationPlanDrugsEntity.medicationPlan!!.id else null,
                    medicationId = if (medicationPlanDrugsEntity.medication != null) medicationPlanDrugsEntity.medication!!.id else null
            )
        }

        fun fromDto(medicationPlanDrugsDto: MedicationPlanDrugsDto): MedicationPlanDrugsEntity {
            return MedicationPlanDrugsEntity(
                    id = medicationPlanDrugsDto.id,
                    medication = if (medicationPlanDrugsDto.medicationId != null) MedicationEntity(
                            id = medicationPlanDrugsDto.medicationId
                    ) else null,
                    medicationPlan = if (medicationPlanDrugsDto.medicationPlanId != null) MedicationPlanEntity(
                            id = medicationPlanDrugsDto.medicationPlanId
                    ) else null
            )
        }
    }
}