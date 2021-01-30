package com.health.application.repository

import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.CaregiverEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CaregiverRepository : JpaRepository<CaregiverEntity, Int> {
    fun findByUser(user: UserEntity): CaregiverEntity
}