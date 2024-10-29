package MonteCarlo.worker;

import java.util.Random;

public class WorkerI implements Worker {
    private final Random random = new Random();

    @Override
    public int countPointsInCircle(int pointsToGenerate, Ice.Current current) {
        int pointsInCircle = 0;

        for (int i = 0; i < pointsToGenerate; i++) {
            double x = random.nextDouble() * 2 - 1;  // Genera x en [-1, 1]
            double y = random.nextDouble() * 2 - 1;  // Genera y en [-1, 1]
            if (x * x + y * y <= 1) {  // Verifica si está dentro del círculo
                pointsInCircle++;
            }
        }

        return pointsInCircle;
    }
}
