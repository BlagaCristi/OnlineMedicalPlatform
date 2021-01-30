package ds.health.caregiversoa.endpoint

import ds.health.caregiversoa.repository.PatientRepository
import ds.health.soa.soa_endpoints.GetPatientRecommendationRequest
import ds.health.soa.soa_endpoints.GetPatientRecommendationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ws.server.endpoint.annotation.Endpoint
import org.springframework.ws.server.endpoint.annotation.PayloadRoot
import org.springframework.ws.server.endpoint.annotation.RequestPayload
import org.springframework.ws.server.endpoint.annotation.ResponsePayload

@Endpoint
class PatientRecommendationEndpoint {

    @Autowired
    private lateinit var patientRepository: PatientRepository

    @PayloadRoot(namespace = "http://health.ds/soa/soa-endpoints", localPart = "getPatientRecommendationRequest")
    @ResponsePayload
    fun getPatientRecommendation(@RequestPayload request: GetPatientRecommendationRequest): GetPatientRecommendationResponse {

        val response = GetPatientRecommendationResponse()

        val patientEntity = patientRepository.findById(request.id)
        if (patientEntity.isPresent) {
            if (patientEntity.get().recommendation == null) response.recommendation = "" else response.recommendation = patientEntity.get().recommendation
        } else {
            response.recommendation = ""
        }

        return response
    }
}
