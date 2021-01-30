package com.health.application.model.entity

import com.health.application.model.entity.types.PatientEntity
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "activity_patient")
class ActivityPatientEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_patient_sequence_generator")
        @SequenceGenerator(name = "activity_patient_sequence_generator", sequenceName = "activity_patient_sequence", allocationSize = 50)
        var id: Int? = null,

        @OneToOne(cascade = [(CascadeType.REMOVE)])
        @JoinColumn(name = "patient_id")
        var patientEntity: PatientEntity? = null,

        @Column(name = "start_time")
        var startTime: Timestamp? = null,

        @Column(name = "end_time")
        var endTime: Timestamp? = null,

        @Column(name = "activity")
        var activity: String? = null,

        @Column(name = "is_normal")
        var isNormal: String? = null
)