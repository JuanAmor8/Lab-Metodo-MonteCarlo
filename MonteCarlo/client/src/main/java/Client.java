import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Obtener el proxy del maestro
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10002");
            PiCalculation.MasterPrx masterProxy = PiCalculation.MasterPrx.checkedCast(base);
            if (masterProxy == null) {
                throw new Error("The master proxy is invalid.");
            }

            // Solicitar al usuario el número total de puntos para el cálculo
            Scanner scanner = new Scanner(System.in);
            System.out.print("Type type the total number of points to calculate π: ");
            int totalPoints = scanner.nextInt();

            // Asignar tareas al maestro
            masterProxy.assignTasks(totalPoints);

            // Obtener la estimación de π del maestro
            double piEstimate = masterProxy.computePiEstimate();
            System.out.println("The π estimation is: " + piEstimate);

            scanner.close();
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

