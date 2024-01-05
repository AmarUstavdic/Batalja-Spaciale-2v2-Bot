public class Planet {

    private final Universe universe;
    private final PlanetManager planetManager;

    private final int name; // name of first planet in game is 0
    private final int positionX;
    private final int positionY;
    private final float planetSize;
    private int fleetSize;
    private String planetColor;

    // should be final
    private final Radar radar;

    // Contains distance from this planet, to every other planet
    private double utility;
    private int avgDistanceToOtherPlanetsInTurns;
    private int[] distanceTable;



    public Planet(int name, int x, int y, float pSize, int fSize, String color, PlanetManager planetManager) {
        this.radar = new Radar(planetManager.getUniverse());
        this.planetManager = planetManager;
        this.universe = planetManager.getUniverse();
        this.name = name;
        this.positionX = x;
        this.positionY = y;
        this.planetSize = pSize;
        this.fleetSize = fSize;
        this.planetColor = color;
    }


    // ------------------------------
    // FUNCTIONS FOR STRATEGY MAKING
    // ------------------------------




    public boolean isMine() {
        return universe.getMyColor().equals(this.planetColor);
    }





    public int getNumberOfFleetsOverNTurns(int nTurns) {
        // number of reinforcements + generated fleets
        // for now just for simplicity sake only taking into a count generated
        return (int) (this.planetSize * 10 * nTurns);
    }


    public void calculateUtility(Planet sourcePlanet, double alpha) {
        this.utility += (planetSize * 10) / Math.pow(this.getDistanceInTurns(sourcePlanet),alpha);
    }


    public boolean isAlly() {
        return universe.getMyColor().equals(this.planetColor) || universe.getTeammateColor().equals(this.planetColor);
    }

    public boolean isOwnershipChanged(String newColor) {
        return !this.planetColor.equals(newColor);
    }

    public int getDistanceInTurns(Planet other) {
        double euclidean = Math.sqrt(Math.pow((this.positionX - other.positionX), 2) + Math.pow((this.positionY - other.positionY), 2));
        return (int) euclidean / 2;
    }





    /**
     *  If there is no enemy or neutral planet in universe, the function returns -1;
     */
    public int getClosestEnemy() {
        int name = -1;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < distanceTable.length; i++) {
            if (distance > distanceTable[i] && planetManager.getPlanetByName(i).isAxis()) {
                distance = distanceTable[i];
                name = i;
            }
        }
        return name;
    }

    public boolean isAxis() {
        return  !this.planetColor.equals(universe.getMyColor()) && !this.planetColor.equals(universe.getTeammateColor());
    }

    public int getName() {
        return name;
    }

    public boolean isNeutral() {
        return this.planetColor.equals("null");
    }
    
    public double getUtility() {
        return utility;
    }

    public int getFleetSize() {
        return fleetSize;
    }

    public String getPlanetColor() {
        return planetColor;
    }

    public void setFleetSize(int fleetSize) {
        this.fleetSize = fleetSize;
    }

    public void setPlanetColor(String planetColor) {
        this.planetColor = planetColor;
    }

    public void setAvgDistanceToOtherPlanetsInTurns(int avgDistanceToOtherPlanetsInTurns) {
        this.avgDistanceToOtherPlanetsInTurns = avgDistanceToOtherPlanetsInTurns;
    }

    public int getAvgDistanceToOtherPlanetsInTurns() {
        return avgDistanceToOtherPlanetsInTurns;
    }

    public void setDistanceTable(int[] distanceTable) {
        this.distanceTable = distanceTable;
    }

    public Radar getRadar() {
        return radar;
    }

}
