package ds.health.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "medication_intake")
public class MedicationIntakeEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "medication_name")
    private String medicationName;

    @Column(name = "intake_date")
    private Timestamp intakeDate;

    @Column(name = "is_taken")
    private String isTaken;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Timestamp getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Timestamp intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getIsTaken() {
        return isTaken;
    }

    public void setIsTaken(String isTaken) {
        this.isTaken = isTaken;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }
}