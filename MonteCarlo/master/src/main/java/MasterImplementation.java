import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

import PiCalculation.WorkerPrx;

public class MasterImplementation implements PiCalculation.Master {
    private int totalSamples = 0;
    private int circleHits = 0;
    private int workerPortStart = 10003;
    private int workerPortCurrent = 10002;

    @Override
    public String assignTasks(int numberOfSamples, com.zeroc.Ice.Current current) {
        circleHits = 0;  // Reiniciar el contador de aciertos en el círculo
        System.out.println("------------------------------------------------------");
        System.out.println("Master received request to process " + numberOfSamples + " samples.");
        totalSamples = numberOfSamples;

        // Descubrir trabajadores activos en el rango de puertos
        List<WorkerPrx> activeWorkers = discoverActiveWorkers(current);

        // Verificar disponibilidad de trabajadores
        if (activeWorkers.isEmpty()) {
            return "No available workers to handle the computation.";
        }

        // Calcular la cantidad de muestras por trabajador
        int samplesPerWorker = numberOfSamples / activeWorkers.size();
        System.out.println("Allocating " + samplesPerWorker + " samples to each worker.");

        // Enviar tareas a los trabajadores y recopilar resultados
        executeTasksInParallel(activeWorkers, samplesPerWorker);

        return "Distributed " + numberOfSamples + " samples among " + activeWorkers.size() + " active workers.";
    }

    @Override
    public double computePiEstimate(com.zeroc.Ice.Current current) {
        if (totalSamples == 0) {
            System.out.println("No samples were processed. Unable to compute Pi estimate.");
            return 0.0;
        }
        double piEstimate = 4.0 * circleHits / totalSamples;
        System.out.println("Computed Pi estimate: " + piEstimate);
        return piEstimate;
    }

    @Override
    public int registerCalculator(int increment, com.zeroc.Ice.Current current) {
        workerPortCurrent += increment;
        System.out.println("Worker registered on port " + workerPortCurrent);
        return workerPortCurrent;
    }

    /**
     * Descubre los trabajadores activos en el rango de puertos especificado.
     *
     * @param current El contexto actual de ICE.
     * @return Lista de proxies de trabajadores activos.
     */
    private List<WorkerPrx> discoverActiveWorkers(com.zeroc.Ice.Current current) {
        List<WorkerPrx> workersList = new ArrayList<>();
        for (int port = workerPortStart; port <= workerPortCurrent; port++) {
            String proxyString = "Worker:default -p " + port;
            try {
                com.zeroc.Ice.ObjectPrx baseProxy = current.adapter.getCommunicator().stringToProxy(proxyString);
                WorkerPrx workerProxy = WorkerPrx.checkedCast(baseProxy);

                if (workerProxy != null) {
                    workersList.add(workerProxy);
                    System.out.println("Discovered active worker on port " + port);
                }
            } catch (Exception e) {
                System.out.println("Unable to connect to worker on port " + port + ": " + e.getMessage());
            }
        }
        return workersList;
    }

    /**
     * Ejecuta las tareas de cálculo en paralelo utilizando los trabajadores disponibles.
     *
     * @param workers           Lista de proxies de trabajadores.
     * @param samplesPerWorker  Cantidad de muestras asignadas a cada trabajador.
     */
    private void executeTasksInParallel(List<WorkerPrx> workers, int samplesPerWorker) {
        ExecutorService executorService = Executors.newFixedThreadPool(workers.size());
        List<Future<Integer>> futureResults = new ArrayList<>();

        // Enviar tareas a los trabajadores
        for (WorkerPrx worker : workers) {
            Future<Integer> future = executorService.submit(() -> {
                return worker.countPointsInsideCircle(samplesPerWorker);
            });
            futureResults.add(future);
        }

        // Recopilar resultados de los trabajadores
        for (Future<Integer> result : futureResults) {
            try {
                circleHits += result.get();  // Sumar los aciertos en el círculo
            } catch (Exception e) {
                System.out.println("Error retrieving result from worker: " + e.getMessage());
            }
        }

        executorService.shutdown();  // Finalizar el ExecutorService
    }
}
