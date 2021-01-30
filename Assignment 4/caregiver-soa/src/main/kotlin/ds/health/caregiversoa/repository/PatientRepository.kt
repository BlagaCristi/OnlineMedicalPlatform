package ds.health.caregiversoa.repository

import ds.health.caregiversoa.model.PatientEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository : JpaRepository<PatientEntity, Int> {
}