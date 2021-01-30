package com.health.application.model.entity

import com.health.application.model.entity.types.PatientEntity
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "medication_intake")
class MedicationIntakeEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_intake_sequence_generator")
        @SequenceGenerator(name = "medication_intake_sequence_generator", sequenceName = "medication_intake_sequence", allocationSize = 1)
        var id: Int? = null,

        @Column(name = "is_taken")
        var isTaken: String? = null,

        @Column(name = "medication_name")
        var medicationName: String? = null,

        @Column(name = "intake_date")
        var intakeDate: Timestamp? = null,

        @ManyToOne
        @JoinColumn(name = "patient_id")
        var patient: PatientEntity? = null
)