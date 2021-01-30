package ds.health.endpoint;

import ds.health.model.PatientEntity;
import ds.health.repository.PatientRepository;
import ds.health.soa.soa_endpoints.SetPatientRecommendationRequest;
import ds.health.soa.soa_endpoints.SetPatientRecommendationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class PatientRecommendationEndpoint {
    private static final String NAMESPACE_URI = "http://health.ds/soa/soa-endpoints";

    private PatientRepository patientRepository;

    @Autowired
    public PatientRecommendationEndpoint(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "setPatientRecommendationRequest")
    @ResponsePayload
    public SetPatientRecommendationResponse setPatientRecommendation(@RequestPayload SetPatientRecommendationRequest request) {

        SetPatientRecommendationResponse response = new SetPatientRecommendationResponse();

        Optional<PatientEntity> patientEntity = patientRepository.findById(request.getId());
        if (patientEntity.isPresent()) {
            patientEntity.get().setRecommendation(request.getRecommendation());
            patientRepository.save(patientEntity.get());
            response.setResponse("Patient recommendation set");
        } else {
            response.setResponse("Patient does not exist!");
        }

        return response;
    }
}
