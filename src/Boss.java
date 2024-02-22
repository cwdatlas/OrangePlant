class Boss {
    public static void main(String[] args){

        Boss b = new Boss();

    }
    public static final long PROCESSING_TIME = 5 * 1000;
    static final int numberOfPlants = 5;
    final Plant[] orangePlants = new Plant[numberOfPlants];
    final Thread[] orangeThreads = new Thread[numberOfPlants];

    Boss() {
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
        try{
            Thread.sleep(PROCESSING_TIME);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println("Gathering Plant statistics and sending workers home");
        for(Plant i: orangePlants) {
            processedOranges += i.getProcessedOranges();
            wastedOranges += i.getWaste();
            juiceBottles += i.getBottles();
            i.stopWorkers();
        }
        System.out.println("Closing orange juice plants");
        for(Thread i: orangeThreads) {
            try {
                i.join();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Oranges Processed: " + processedOranges);
        System.out.println("Oranges Wasted: " + wastedOranges);
        System.out.println("Oranges juice Created : " + juiceBottles);

    }
}