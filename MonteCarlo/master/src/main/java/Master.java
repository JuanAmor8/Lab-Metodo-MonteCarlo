public class Master {
    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            // Crear el adaptador de objetos para el maestro
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MasterAdapter", "default -p 10002");
            com.zeroc.Ice.Object masterServant = new MasterImplementation();
            adapter.add(masterServant, com.zeroc.Ice.Util.stringToIdentity("Master"));
            adapter.activate();
            System.out.println("The master is active and listening to answer requests.");
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Master Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
