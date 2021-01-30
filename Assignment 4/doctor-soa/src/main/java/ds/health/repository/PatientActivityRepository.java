package ds.health.repository;

import ds.health.model.PatientActivityEntity;
import ds.health.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientActivityRepository extends JpaRepository<PatientActivityEntity, Integer> {

    public List<PatientActivityEntity> findByPatientEntity(PatientEntity patientEntity);
}
