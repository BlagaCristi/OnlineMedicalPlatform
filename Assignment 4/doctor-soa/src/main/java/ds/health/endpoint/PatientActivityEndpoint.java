package ds.health.endpoint;

import ds.health.model.PatientActivityEntity;
import ds.health.model.PatientEntity;
import ds.health.repository.PatientActivityRepository;
import ds.health.soa.soa_endpoints.GetPatientActivityRequest;
import ds.health.soa.soa_endpoints.GetPatientActivityResponse;
import ds.health.soa.soa_endpoints.PatientActivity;
import ds.health.soa.soa_endpoints.PatientActivityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class PatientActivityEndpoint {
    private static final String NAMESPACE_URI = "http://health.ds/soa/soa-endpoints";

    private PatientActivityRepository patientActivityRepository;

    @Autowired
    public PatientActivityEndpoint(PatientActivityRepository patientActivityRepository) {
        this.patientActivityRepository = patientActivityRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPatientActivityRequest")
    @ResponsePayload
    public GetPatientActivityResponse getPatientActivity(@RequestPayload GetPatientActivityRequest request) {
        GetPatientActivityResponse response = new GetPatientActivityResponse();

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(request.getId());

        List<PatientActivityEntity> patientActivityEntityList = patientActivityRepository.findByPatientEntity(patientEntity);
        PatientActivityList patientActivityList = new PatientActivityList();

        patientActivityEntityList.forEach(patientActivityEntity -> {
            PatientActivity patientActivity = new PatientActivity();
            patientActivity.setActivity(patientActivityEntity.getActivity());
            patientActivity.setId(patientActivityEntity.getId());
            patientActivity.setStartTime(patientActivityEntity.getStartTime().toString());
            patientActivity.setEndTime(patientActivityEntity.getEndTime().toString());
            patientActivity.setIsNormal(patientActivityEntity.getIsNormal());

            patientActivityList.getPatientActivity().add(patientActivity);
        });

        response.setPatientActivityList(patientActivityList);

        return response;
    }
}
