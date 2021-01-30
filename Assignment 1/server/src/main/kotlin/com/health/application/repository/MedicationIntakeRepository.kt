package com.health.application.repository

import com.health.application.model.entity.MedicationIntakeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationIntakeRepository : JpaRepository<MedicationIntakeEntity, Int> {
}