package com.health.application.model.entity.types

import com.health.application.model.entity.MedicationPlanEntity
import com.health.application.model.entity.UserEntity
import javax.persistence.*

@Entity
@Table(name = "doctor")
class DoctorEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_sequence_generator")
        @SequenceGenerator(name = "doctor_sequence_generator", sequenceName = "doctor_sequence", allocationSize = 1)
        var id: Int? = null,

        @OneToOne(cascade = [(CascadeType.REMOVE)])
        @JoinColumn(name = "user_id")
        var user: UserEntity? = null,

        @OneToMany(mappedBy = "doctor", cascade = [(CascadeType.ALL)], targetEntity = MedicationPlanEntity::class)
        var medicationPlanList: List<MedicationPlanEntity>? = null
)