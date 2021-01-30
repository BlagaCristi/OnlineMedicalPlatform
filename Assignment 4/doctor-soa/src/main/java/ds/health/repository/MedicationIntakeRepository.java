package ds.health.repository;

import ds.health.model.MedicationIntakeEntity;
import ds.health.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationIntakeRepository extends JpaRepository<MedicationIntakeEntity, Integer> {
    public List<MedicationIntakeEntity> findByPatientEntity(PatientEntity patientEntity);

}
