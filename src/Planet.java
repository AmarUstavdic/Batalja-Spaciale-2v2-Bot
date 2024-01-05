import java.util.Comparator;
import java.util.ArrayList;
import java.util.stream.IntStream;


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
    private int distanceToTarget;

    // variables needed for lexicographic sort




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


    public int avgDistanceFromMyPlanets() {
        ArrayList<Planet> attackingPlanets = planetManager.getMyPlanets();
        if (!attackingPlanets.isEmpty()) {
            int sum = attackingPlanets.stream().flatMapToInt(p -> IntStream.of(p.getDistanceInTurns(this))).sum();
            return sum / attackingPlanets.size();
        } else {
            return Integer.MAX_VALUE;
        }
    }


    public int calculateTurnsToCapture() {

        ArrayList<Planet> attackingPlanets = planetManager.getMyPlanets();
        attackingPlanets.remove(this);
        attackingPlanets.sort(Comparator.comparingInt(this::getDistanceInTurns));

        int cumulativeFleetSize = 0;
        int turns = Integer.MAX_VALUE;

        for (Planet attacker : attackingPlanets) {
            cumulativeFleetSize += attacker.getFleetSize();
            turns = attacker.getDistanceInTurns(this);

            // check if cumulative fleet size is enough to capture
            int genFleetsInTurns = this.isAxis() ? this.getNumberOfFleetsOverNTurns(turns) : 0;
            int reinforcements = this.isAxis() ? this.radar.getAxisReinforcementInNTurns(turns) : 0;
            if (cumulativeFleetSize >= this.getFleetSize() + genFleetsInTurns + reinforcements) {
                break;
            } else {
                turns = Integer.MAX_VALUE;
            }
        }
        return turns;
    }



    public boolean isMine() {
        return universe.getMyColor().equals(this.planetColor);
    }





    public int getNumberOfFleetsOverNTurns(int nTurns) {
        return (int) (this.planetSize * 10 * nTurns);
    }


    public void calculateUtility(Planet sourcePlanet, double alpha) {
        this.utility = (planetSize * 10) / Math.pow(this.getDistanceInTurns(sourcePlanet),alpha);
        this.utility += (planetSize * 10) / getAvgDistanceToOtherPlanetsInTurns();
    }

    public void addToUtility(double x) {
        this.utility += x;
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

    public boolean isFriendly() {
        return this.planetColor.equals(universe.getTeammateColor());
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

    public int getDistanceToTarget() {
        return distanceToTarget;
    }

    public int getClosestPlanetName() {
        int name = Integer.MAX_VALUE;
        for (int i = 0; i < distanceTable.length; i++) {
            // TODO: Return the index of minimal value taht is not this planet.
        }
        return 0;
    }

    public double getPlanetSize() {
        return planetSize;
    }

    public void setDistanceToTarget(Planet target) {
        this.distanceToTarget = distanceTable[target.getName()];
    }
}
