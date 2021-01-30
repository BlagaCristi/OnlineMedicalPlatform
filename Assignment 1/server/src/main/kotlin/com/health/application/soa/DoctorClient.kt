package com.health.application.soa

import ds.health.soa.soa_endpoints.*
import org.springframework.ws.client.core.support.WebServiceGatewaySupport
import org.springframework.ws.soap.client.core.SoapActionCallback

class DoctorClient : WebServiceGatewaySupport() {

    fun getPatientActivity(patientId: Int): GetPatientActivityResponse {
        var request = GetPatientActivityRequest()
        request.id = patientId

        var response: GetPatientActivityResponse = webServiceTemplate
                .marshalSendAndReceive("http://localhost:8081/soa/patient_activity", request,
                        SoapActionCallback(
                                "http://health.ds/soa/soa-endpoints")) as GetPatientActivityResponse
        return response
    }

    fun getPatientMedicationIntake(patientId: Int): GetMedicationIntakeResponse {
        var request = GetMedicationIntakeRequest()
        request.id = patientId

        var response: GetMedicationIntakeResponse = webServiceTemplate
                .marshalSendAndReceive("http://localhost:8081/soa/medication_intake", request,
                        SoapActionCallback(
                                "http://health.ds/soa/soa-endpoints")) as GetMedicationIntakeResponse
        return response
    }

    fun setPatientRecommendation(patientId: Int, recommendation: String): SetPatientRecommendationResponse {
        var request = SetPatientRecommendationRequest()
        request.id = patientId
        request.recommendation = recommendation

        var response: SetPatientRecommendationResponse = webServiceTemplate
                .marshalSendAndReceive("http://localhost:8081/soa/patient_recommendation", request,
                        SoapActionCallback(
                                "http://health.ds/soa/soa-endpoints")) as SetPatientRecommendationResponse
        return response
    }
}