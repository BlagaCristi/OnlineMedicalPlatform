package ds.health.endpoint;

import ds.health.model.MedicationIntakeEntity;
import ds.health.model.PatientEntity;
import ds.health.repository.MedicationIntakeRepository;
import ds.health.soa.soa_endpoints.GetMedicationIntakeRequest;
import ds.health.soa.soa_endpoints.GetMedicationIntakeResponse;
import ds.health.soa.soa_endpoints.MedicationIntake;
import ds.health.soa.soa_endpoints.MedicationIntakeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class MedicationIntakeEndpoint {
    private static final String NAMESPACE_URI = "http://health.ds/soa/soa-endpoints";

    private MedicationIntakeRepository medicationIntakeRepository;

    @Autowired
    public MedicationIntakeEndpoint(MedicationIntakeRepository medicationIntakeRepository) {
        this.medicationIntakeRepository = medicationIntakeRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMedicationIntakeRequest")
    @ResponsePayload
    public GetMedicationIntakeResponse getMedicationIntake(@RequestPayload GetMedicationIntakeRequest request) {

        GetMedicationIntakeResponse response = new GetMedicationIntakeResponse();

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(request.getId());

        List<MedicationIntakeEntity> medicationIntakeEntityList = medicationIntakeRepository.findByPatientEntity(patientEntity);
        MedicationIntakeList medicationIntakeList = new MedicationIntakeList();

        medicationIntakeEntityList.forEach(medicationIntakeEntity -> {
            MedicationIntake medicationIntake = new MedicationIntake();
            medicationIntake.setId(medicationIntakeEntity.getId());
            medicationIntake.setIntakeDate(medicationIntakeEntity.getIntakeDate().toString());
            medicationIntake.setIsTaken(medicationIntakeEntity.getIsTaken());
            medicationIntake.setMedicationName(medicationIntakeEntity.getMedicationName());
            medicationIntake.setPatientId(medicationIntakeEntity.getPatientEntity().getId());

            medicationIntakeList.getMedicationIntake().add(medicationIntake);
        });

        response.setMedicationIntakeList(medicationIntakeList);

        return response;
    }
}
