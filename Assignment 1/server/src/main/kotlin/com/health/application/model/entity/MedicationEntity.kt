package com.health.application.model.entity

import javax.persistence.*


@Entity
@Table(name = "medication")
class MedicationEntity(
        @Column(name = "name")
        var name: String? = null,

        @Column(name = "side_effect")
        var sideEffect: String? = null,

        @Column(name = "dosage")
        var dosage: String? = null,

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_sequence_generator")
        @SequenceGenerator(name = "medication_sequence_generator", sequenceName = "medication_sequence", allocationSize = 1)
        var id: Int?,

        @OneToMany(mappedBy = "medication", cascade = [(CascadeType.ALL)], targetEntity = MedicationPlanDrugsEntity::class)
        var medicationPlanDrugsEntity: List<MedicationPlanDrugsEntity>? = null
)