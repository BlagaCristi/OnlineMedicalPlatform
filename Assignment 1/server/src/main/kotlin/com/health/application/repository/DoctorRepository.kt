package com.health.application.repository

import com.health.application.model.entity.UserEntity
import com.health.application.model.entity.types.DoctorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DoctorRepository : JpaRepository<DoctorEntity, Int> {
    fun findByUser(user: UserEntity): DoctorEntity
}