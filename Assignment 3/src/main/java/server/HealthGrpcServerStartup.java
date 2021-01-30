package server;

public class HealthGrpcServerStartup {

    public static void main(String[] args) throws Exception {
        HealthGrpcServer server = new HealthGrpcServer(23970);
        server.start();
        server.blockUntilShutdown();
    }
}
