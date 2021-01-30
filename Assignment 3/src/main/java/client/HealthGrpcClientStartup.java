package client;

public class HealthGrpcClientStartup {
    public static void main(String[] args) throws InterruptedException {

//        HealthGrpcClient client = new HealthGrpcClient("localhost", 23970);
//        client.receiveMessage("Hello!");
//        client.streamMedicationPlans(55);
        Thread thread = new Thread(() -> {
            UserInterface userInterface = new UserInterface();
        });
        thread.start();
        thread.join();
    }
}
