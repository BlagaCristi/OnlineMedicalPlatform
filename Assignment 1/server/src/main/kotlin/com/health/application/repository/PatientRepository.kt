package com.health.application.repository

import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.CaregiverEntity
import com.health.application.model.entity.types.PatientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<PatientEntity, Int> {
    fun findByUser(user: UserEntity): PatientEntity
    fun findByCaregiver(caregiver: CaregiverEntity): List<PatientEntity>
}