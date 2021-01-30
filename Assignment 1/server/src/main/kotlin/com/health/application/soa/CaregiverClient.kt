package com.health.application.soa

import ds.health.soa.soa_endpoints.GetPatientRecommendationRequest
import ds.health.soa.soa_endpoints.GetPatientRecommendationResponse
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback

class CaregiverClient : WebServiceGatewaySupport() {

    fun getPatientRecommendation(patientId: Int): GetPatientRecommendationResponse {
        var request = GetPatientRecommendationRequest()
        request.id = patientId

        var response: GetPatientRecommendationResponse = webServiceTemplate
                .marshalSendAndReceive("http://localhost:8082/soa/patient_recommendation", request,
                        SoapActionCallback(
                                "http://health.ds/soa/soa-endpoints")) as GetPatientRecommendationResponse
        return response
    }

}