package ds.health.caregiversoa.model

import javax.persistence.*

@Entity
@Table(name = "patient")
class PatientEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence_generator")
        @SequenceGenerator(name = "patient_sequence_generator", sequenceName = "patient_sequence", allocationSize = 1)
        var id: Int? = null,

        @Column(name = "recommendation")
        var recommendation: String? = null
)