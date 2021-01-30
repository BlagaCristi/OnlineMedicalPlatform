package com.health.application.repository

import com.health.application.model.entity.MedicationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MedicationRepository : JpaRepository<MedicationEntity, Int>