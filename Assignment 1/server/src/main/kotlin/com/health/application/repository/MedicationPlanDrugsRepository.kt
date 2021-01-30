package com.health.application.repository

import com.health.application.model.entity.MedicationPlanDrugsEntity
import com.health.application.model.entity.MedicationPlanEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationPlanDrugsRepository : JpaRepository<MedicationPlanDrugsEntity, Int> {
    fun findByMedicationPlan(medicationPlan: MedicationPlanEntity): List<MedicationPlanDrugsEntity>
}