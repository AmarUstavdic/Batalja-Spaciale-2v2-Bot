import java.util.*;
import java.util.stream.Collectors;

public class PlanetManager {

    private boolean distanceTablesInitialized = false;

    private final Universe universe;
    private final HashMap<Integer, Planet> planets;

    public PlanetManager(Universe universe) {
        this.universe = universe;
        this.planets = new HashMap<>();
    }


    /**
     *  Function takes all fleet information from the game, converts it to Fleet object,
     *  and redirects that object to planet (destination), planet does further information processing.
     */
    public void parseFleet(int name, int size, int org, int dst, int currentTurn, int neededTurns, String color) {
        Planet planet = planets.get(dst);
        planet.getRadar().update(new Fleet(name, size, org, dst, currentTurn, neededTurns, color));
    }


    public void parsePlanet(int name, int x, int y, float planetSize, int fleetSize, String color) {

        if (!planets.containsKey(name)) {
            planets.put(name, new Planet(name, x, y, planetSize, fleetSize, color, this));
            return;
        }
        planets.get(name).setFleetSize(fleetSize);
        planets.get(name).setPlanetColor(color);
    }


    public void initDistanceTables() {
        // first planet in game has index 0
        for (int i = 0; i < planets.size(); i++) {

            Planet p = planets.get(i);
            int[] distanceTable = new int[planets.size()];
            int avgDstInTurns = 0;
            for (int j = 0; j < planets.size(); j++) {
                Planet o = planets.get(j);
                int dst = p.getDistanceInTurns(o);
                distanceTable[j] = dst;
                avgDstInTurns += dst;
            }
            p.setAvgDistanceToOtherPlanetsInTurns(avgDstInTurns / planets.size());
            p.setDistanceTable(distanceTable);
        }
        distanceTablesInitialized = true;
    }


    public Planet getPlanetByName(int name) {
        return planets.get(name);
    }


    public ArrayList<Planet> getMyPlanets() {
        return planets.values().stream().filter(Planet::isMine).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Planet> getAllyPlanets() {
        return planets.values().stream().filter(Planet::isFriendly).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Planet> getAxisPlanets() {
        return planets.values().stream().filter(Planet::isAxis).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Planet> getNeutralPlanets() {
        return planets.values().stream().filter(Planet::isNeutral).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     *  This function should not be called before all planets are initialized.
     *  Returns total number of planets.
     */
    public int getNumberOfPlanets() {
        return planets.size();
    }

    public Universe getUniverse() {
        return universe;
    }

    public boolean isDistanceTablesInitialized() {
        return distanceTablesInitialized;
    }

    public HashMap<Integer, Planet> getPlanets() {
        return planets;
    }
}
