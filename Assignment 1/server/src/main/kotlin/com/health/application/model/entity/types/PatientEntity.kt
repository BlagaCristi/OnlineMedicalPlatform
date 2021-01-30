package com.health.application.model.entity.types

import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.model.entity.UserEntity
import javax.persistence.*

@Entity
@Table(name = "patient")
class PatientEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence_generator")
        @SequenceGenerator(name = "patient_sequence_generator", sequenceName = "patient_sequence", allocationSize = 1)
        var id: Int? = null,

        @OneToOne(cascade = [(CascadeType.REMOVE)])
        @JoinColumn(name = "user_id")
        var user: UserEntity? = null,

        @Column(name = "medical_record")
        var medicalRecord: String? = null,

        @ManyToOne
        @JoinColumn(name = "caregiver_id")
        var caregiver: CaregiverEntity? = null,

        @OneToMany(mappedBy = "patient", cascade = [(CascadeType.ALL)], targetEntity = MedicationPlanEntity::class)
        var medicationPlanList: List<MedicationPlanEntity>? = null
)