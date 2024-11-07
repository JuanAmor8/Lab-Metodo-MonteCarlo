import java.security.SecureRandom;
import java.util.stream.IntStream;

public class WorkerImplementation implements PiCalculation.Worker {

    @Override
    public int countPointsInsideCircle(int sampleSize, com.zeroc.Ice.Current current) {
        System.out.println("Worker initiating computation with " + sampleSize + " samples.");
        SecureRandom randomGenerator = new SecureRandom();

        // Usar programación funcional y streams para generar y procesar puntos aleatorios
        long pointsInsideCircle = IntStream.range(0, sampleSize)
            .parallel() // Procesamiento en paralelo
            .mapToObj(i -> {
                double x = randomGenerator.nextDouble() * 2 - 1;
                double y = randomGenerator.nextDouble() * 2 - 1;
                return x * x + y * y;
            })
            .filter(distanceSquared -> distanceSquared <= 1.0)
            .count();

        System.out.println("Worker counted " + pointsInsideCircle + " points inside the circle.");
        return (int) pointsInsideCircle;
    }
}
