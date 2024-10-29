package MonteCarlo.client;

import Ice.Communicator;
import Ice.ObjectPrx;

public class Client {
    public static void main(String[] args) {
        try (Communicator communicator = Ice.Util.initialize(args)) {
            ObjectPrx base = communicator.stringToProxy("master:default -p 10000");
            MasterPrx master = MasterPrxHelper.checkedCast(base);

            if (master == null) {
                throw new Error("Invalid proxy");
            }

            int totalPoints = 100000;  // Ejemplo de número de puntos
            int numWorkers = 2;        // Ejemplo de número de trabajadores

            // Solicitar la estimación de pi al maestro
            float piEstimate = master.estimatePi(totalPoints, numWorkers);
            System.out.println("Estimación de π: " + piEstimate);
        }
    }
}
