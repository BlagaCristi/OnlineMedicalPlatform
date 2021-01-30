package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.health.HealthServiceGrpc;
import io.grpc.health.HealthServiceGrpc.HealthServiceStub;
import io.grpc.health.MedicationPlanMessage;
import io.grpc.health.PatientIdMessage;
import io.grpc.health.PatientMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static io.grpc.health.HealthServiceGrpc.HealthServiceBlockingStub;

public class HealthGrpcClient {
    private static final Logger logger = Logger.getLogger(HealthGrpcClient.class.getName());

    private final ManagedChannel channel;
    private final HealthServiceBlockingStub healthServiceBlockingStub;
    private final HealthServiceStub healthServiceStub;

    public HealthGrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public HealthGrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        healthServiceBlockingStub = HealthServiceGrpc.newBlockingStub(channel);
        healthServiceStub = HealthServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown();
    }

    public void receiveMessage(int patientId, String isTaken, String medicationName) {

        PatientMessage request = PatientMessage.newBuilder().build().newBuilder()
                .setIsTaken(isTaken)
                .setMedicationName(medicationName)
                .setPatientId(patientId)
                .build();

        try {
            healthServiceBlockingStub.receiveMessage(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
    }

    public List<MedicationPlanMessage> streamMedicationPlans(Integer patientId) {
        PatientIdMessage request = PatientIdMessage.newBuilder().setPatientId(patientId).build();
        Iterator<MedicationPlanMessage> medicationPlanMessageIterator;
        try {
            medicationPlanMessageIterator = healthServiceBlockingStub.streamMedicationPlans(request);

            List<MedicationPlanMessage> medicationPlanMessageList = new ArrayList<>();
            medicationPlanMessageIterator.forEachRemaining(medicationPlanMessageList::add);

            return medicationPlanMessageList;
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
