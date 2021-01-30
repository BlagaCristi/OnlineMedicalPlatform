package server;

import com.google.gson.Gson;
import io.grpc.health.*;
import io.grpc.stub.StreamObserver;
import model.Medication;
import model.MedicationIntake;
import model.MedicationPlan;
import model.MedicationPlanDrugs;
import okhttp3.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HealthGrpcService extends HealthServiceGrpc.HealthServiceImplBase {

    private static final String AUTHORIZATION_VALUE = "Basic ZG9jdG9yOmRvY3Rvcg==";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String MEDICATION_PLAN_PATH = "http://localhost:8080/health/medication-plan/patient?patientId=";
    public static final String MEDICATION_INTAKE_PATH = "http://localhost:8080/health/medication-intake";
    public static final String MEDICATION_PLAN_DRUGS_PATH = "http://localhost:8080/health/medication-plan-drugs/medication-plan?medicationPlanId=";
    public static final String MEDICATION_PATH = "http://localhost:8080/health/medication?id=";

    @Override
    public void receiveMessage(PatientMessage request, StreamObserver<Empty> responseObserver) {

        System.out.println("Received message: " + request.getPatientId() + " " + request.getIsTaken() + " " + request.getMedicationName());

        MedicationIntake medicationIntake = new MedicationIntake();
        medicationIntake.setIntakeDate("" + new Date().getTime());
        medicationIntake.setIsTaken(request.getIsTaken());
        medicationIntake.setMedicationName(request.getMedicationName());
        medicationIntake.setPatientId(request.getPatientId());

        Gson g = new Gson();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");


        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, g.toJson(medicationIntake));

        Request httpRequestMedPlan = new Request.Builder()
                .url(MEDICATION_INTAKE_PATH)
                .addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE)
                .post(body)
                .build();

        try {
            client.newCall(httpRequestMedPlan).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void streamMedicationPlans(PatientIdMessage request, StreamObserver<MedicationPlanMessage> responseObserver) {
        OkHttpClient client = new OkHttpClient();

        Request httpRequestMedPlan = new Request.Builder()
                .url(MEDICATION_PLAN_PATH + request.getPatientId())
                .addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE)
                .build();

        List<MedicationPlanMessage> medicationPlanMessageList = new ArrayList<>();
        try {
            Response responseMedPlan = client.newCall(httpRequestMedPlan).execute();

            Gson g = new Gson();
            MedicationPlan[] medicationPlanArray = g.fromJson(responseMedPlan.body().string(), MedicationPlan[].class);

            Arrays.asList(medicationPlanArray).forEach(medicationPlan -> {
                Request httpRequestMedPlanDrugs = new Request.Builder()
                        .url(MEDICATION_PLAN_DRUGS_PATH + medicationPlan.getId())
                        .addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE)
                        .build();
                try {
                    Response responseMedPlanDrugs = client.newCall(httpRequestMedPlanDrugs).execute();
                    MedicationPlanDrugs[] medicationPlanDrugsArray = g.fromJson(responseMedPlanDrugs.body().string(), MedicationPlanDrugs[].class);
                    Arrays.asList(medicationPlanDrugsArray).forEach(medicationPlanDrug -> {
                        Request httpRequestMed = new Request.Builder()
                                .url(MEDICATION_PATH + medicationPlanDrug.getMedicationId())
                                .addHeader(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE)
                                .build();
                        try {
                            Response responseMed = client.newCall(httpRequestMed).execute();
                            Medication medication = g.fromJson(responseMed.body().string(), Medication.class);

                            MedicationPlanMessage medicationPlanMessage = MedicationPlanMessage
                                    .newBuilder()
                                    .setId(medicationPlan.getId())
                                    .setStartDate(medicationPlan.getStartDate())
                                    .setEndDate(medicationPlan.getEndDate())
                                    .setHourInDay(medicationPlan.getHourInDay())
                                    .setName(medication.getName())
                                    .setDosage(medication.getDosage())
                                    .build();
                            medicationPlanMessageList.add(medicationPlanMessage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            medicationPlanMessageList.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
