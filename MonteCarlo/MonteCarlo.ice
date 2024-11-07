module PiCalculation {
    interface Master {
        string assignTasks(int totalPoints);
        double computePiEstimate();
        int registerCalculator(int id);
    }

    interface Worker {
        int countPointsInsideCircle(int pointsToGenerate);
    }
}
