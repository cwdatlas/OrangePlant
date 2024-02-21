class Boss {
    public static final long PROCESSING_TIME = 5 * 1000;
    static final int numberOfPlants = 5;
    final Plant[] orangePlants = new Plant[numberOfPlants];
    Boss(){

        // create all the needed plants, they will initialize when created
        for(int i = 0; i<=numberOfPlants; i++)
            orangePlants[i] = new Plant();
    }

}
