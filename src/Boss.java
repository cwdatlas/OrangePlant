/**
 * @author Aidan Scott
 * Boss is the class that creates, manages and stops all of the plants plus aggregating their product
 */
class Boss {
    // How long the boss thread will wait until it starts telling plants to shutdown
    public static final long PROCESSING_TIME = 5 * 1000;
    // Total number of plants
    static final int numberOfPlants = 5;
    // Plant and Thread arrays for easy access
    final Plant[] orangePlants = new Plant[numberOfPlants];
    final Thread[] orangeThreads = new Thread[numberOfPlants];
    Boss() {
        // Keep track of each product so it can be reported to the console
        int processedOranges = 0;
        int wastedOranges = 0;
        int juiceBottles = 0;

        // create all the needed plants, then start them
        for (int i = 0; i < numberOfPlants; i++) {
            orangePlants[i] = new Plant();
            orangeThreads[i] = new Thread(orangePlants[i]);
            orangeThreads[i].start();
        }

        // Give the plants time to do work
        System.out.println("Waiting for Plants to produce the juice!");
        try {
            /*
            Thread.sleep() is able to be used in this case because all plants are their own threads
            We can be confident that sleeping the boss thread will not stop other processes
             */
            Thread.sleep(PROCESSING_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Customary interruption handling
        }

        // Boss collects all products from across all plants then asks them to stop their workers
        System.out.println("Gathering Plant statistics and sending workers home");
        for (Plant i : orangePlants) {
            processedOranges += i.getProcessedOranges();
            wastedOranges += i.getWaste();
            juiceBottles += i.getBottles();
            i.stopWorkers();
        }
        // After all workers are stopped, the boss goes to each plant and asks them to shutdown
        System.out.println("Closing orange juice plants");
        for (Thread i : orangeThreads) {
            try {
                i.join(); // If you aren't doing anything, stop the thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Customary interruption handling
            }
        }
        // List all processed products to the console
        System.out.println("Oranges Processed: " + processedOranges);
        System.out.println("Oranges Wasted: " + wastedOranges);
        System.out.println("Oranges juice Created : " + juiceBottles);

    }

    // Entrypoint for the program
    public static void main(String[] args) {
        Boss b = new Boss();

    }
}