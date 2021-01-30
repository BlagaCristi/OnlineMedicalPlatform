package com.health.application.model.entity

import javax.persistence.*

@Entity
@Table(name = "medication_plan_drugs")
class MedicationPlanDrugsEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_plan_drugs_sequence_generator")
        @SequenceGenerator(name = "medication_plan_drugs_sequence_generator", sequenceName = "medication_plan_drugs_sequence", allocationSize = 1)
        var id: Int?,

        @ManyToOne
        @JoinColumn(name = "medication_plan_id")
        var medicationPlan: MedicationPlanEntity? = null,

        @ManyToOne
        @JoinColumn(name = "medication_id")
        var medication: MedicationEntity? = null
)