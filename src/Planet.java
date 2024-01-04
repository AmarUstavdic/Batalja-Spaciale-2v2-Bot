import java.util.Objects;

public class Planet {

    private final Universe universe;
    private final PlanetManager planetManager;

    private final int name; // name of first planet in game is 0
    private final int positionX;
    private final int positionY;
    private final float planetSize;
    private int fleetSize;
    private String planetColor;

    // Contains distance from this planet, to every other planet
    private int[] distanceTable;


    public Planet(int name, int x, int y, float pSize, int fSize, String color, PlanetManager planetManager) {
        this.planetManager = planetManager;
        this.universe = planetManager.getUniverse();
        this.name = name;
        this.positionX = x;
        this.positionY = y;
        this.planetSize = pSize;
        this.fleetSize = fSize;
        this.planetColor = color;
    }

    public boolean isOwnershipChanged(String newColor) {
        return !(Objects.equals(planetColor, newColor));
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
            if (distance > distanceTable[i] && planetManager.getPlanet(i).isEnemy()) {
                distance = distanceTable[i];
                name = i;
            }
        }
        return name;
    }

    public boolean isEnemy() {
        return  !this.planetColor.equals(universe.getMyColor()) && !this.planetColor.equals(universe.getTeammateColor());
    }

    public int getName() {
        return name;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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

    public void setDistanceTable(int[] distanceTable) {
        this.distanceTable = distanceTable;
    }
}
