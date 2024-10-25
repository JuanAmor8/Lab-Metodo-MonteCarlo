module MonteCarlo {
    interface Worker {
        int countPointsInCircle(int pointsToGenerate);
    };

    interface Master {
        float estimatePi(int totalPoints, int numWorkers);
    };
};
