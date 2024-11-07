public class Worker {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Conectar con el Master
            com.zeroc.Ice.ObjectPrx baseProxy = communicator.stringToProxy("Master:default -p 10002");
            PiCalculation.MasterPrx master = PiCalculation.MasterPrx.checkedCast(baseProxy);
            if (master == null) {
                throw new Error("Failed to obtain Master proxy.");
            }

            // Registrar el Worker y obtener el puerto asignado
            int workerPort = master.registerCalculator(1);
            System.out.println("Worker registered, listening on port " + workerPort);
            String endpoint = "default -p " + workerPort;

            // Crear el adaptador para el Worker
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WorkerAdapter", endpoint);
            PiCalculation.Worker workerServant = new WorkerImplementation();
            adapter.add(workerServant, com.zeroc.Ice.Util.stringToIdentity("Worker"));
            adapter.activate();
            System.out.println("Worker started and ready to receive tasks.");
            System.out.println("------------------------------------------------------");
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Worker encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
