package com.health.application.repository

import com.health.application.model.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String): UserEntity
}