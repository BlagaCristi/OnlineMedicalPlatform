package com.health.application.model.entity

import com.health.application.model.entity.types.DoctorEntity
import com.health.application.model.entity.types.PatientEntity
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "medication_plan")
class MedicationPlanEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_plan_sequence_generator")
        @SequenceGenerator(name = "medication_plan_sequence_generator", sequenceName = "medication_plan_sequence", allocationSize = 1)
        var id: Int? = null,

        @Column(name = "start_date")
        var startDate: Date? = null,

        @Column(name = "end_date")
        var endDate: Date? = null,

        @Column(name = "interval")
        var interval: String? = null,

        @Column(name = "hour_in_day")
        var hourInDay: Int? = null,

        @ManyToOne
        @JoinColumn(name = "patient_id")
        var patient: PatientEntity? = null,

        @ManyToOne
        @JoinColumn(name = "doctor_id")
        var doctor: DoctorEntity? = null,

        @OneToMany(mappedBy = "medicationPlan", cascade = [(CascadeType.ALL)], targetEntity = MedicationPlanDrugsEntity::class)
        var medicationPlanDrugsEntity: List<MedicationPlanDrugsEntity>? = null
)