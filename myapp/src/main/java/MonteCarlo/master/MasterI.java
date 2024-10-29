package MonteCarlo.master;

public class MasterI implements Master {
    private final WorkerPrx[] workers;  // Proxies de los trabajadores

    public MasterI(WorkerPrx[] workers) {
        this.workers = workers;
    }

    @Override
    public float estimatePi(int totalPoints, int numWorkers, Ice.Current current) {
        int pointsPerWorker = totalPoints / numWorkers;
        int totalPointsInCircle = 0;

        // Asigna tareas a los trabajadores
        for (WorkerPrx worker : workers) {
            totalPointsInCircle += worker.countPointsInCircle(pointsPerWorker);
        }

        // Estima pi usando la fórmula
        return 4.0f * totalPointsInCircle / totalPoints;
    }
}
