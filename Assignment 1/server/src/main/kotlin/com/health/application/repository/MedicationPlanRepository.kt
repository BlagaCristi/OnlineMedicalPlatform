package com.health.application.repository

import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.model.entity.types.PatientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationPlanRepository : JpaRepository<MedicationPlanEntity, Int> {
    fun findByPatient(patientEntity: PatientEntity): List<MedicationPlanEntity>
}