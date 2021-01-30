package com.health.application.repository

import com.health.application.model.entity.ActivityPatientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ActivityPatientRepository : JpaRepository<ActivityPatientEntity, Int>
