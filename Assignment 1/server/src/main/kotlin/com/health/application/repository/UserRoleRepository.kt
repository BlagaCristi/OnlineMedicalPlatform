package com.health.application.repository

import com.health.application.model.entity.UserRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRoleEntity, Int>