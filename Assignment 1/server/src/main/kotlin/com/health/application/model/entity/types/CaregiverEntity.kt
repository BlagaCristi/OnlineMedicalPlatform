package com.health.application.model.entity.types

import com.health.application.model.entity.UserEntity
import javax.persistence.*

@Entity
@Table(name = "caregiver")
class CaregiverEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caregiver_sequence_generator")
        @SequenceGenerator(name = "caregiver_sequence_generator", sequenceName = "caregiver_sequence", allocationSize = 1)
        var id: Int? = null,

        @OneToOne(cascade = [(CascadeType.REMOVE)])
        @JoinColumn(name = "user_id")
        var user: UserEntity? = null,

        @OneToMany(mappedBy = "caregiver", cascade = [(CascadeType.ALL)], targetEntity = PatientEntity::class)
        var patientList: List<PatientEntity>? = null
)