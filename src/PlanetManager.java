import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlanetManager {

    private final Universe universe;
    private final HashMap<Integer, Planet> allPlanets;
    private final HashSet<Integer> myPlanets;
    private final HashSet<Integer> myTeammatePlanets;
    private final HashSet<Integer> enemyPlanets;
    private final HashSet<Integer> neutralPlanets;

    public PlanetManager(Universe universe) {
        this.universe = universe;
        this.allPlanets = new HashMap<>();
        this.myPlanets = new HashSet<>();
        this.myTeammatePlanets = new HashSet<>();
        this.enemyPlanets = new HashSet<>();
        this.neutralPlanets = new HashSet<>();
    }


    /**
     *  Takes game state line as input and syncs data bout planets,
     *  to that of game.
     */
    public void sync(String line) {

        String[] tokens = line.split(" ");
        int name = Integer.parseInt(tokens[1]);

        if (!allPlanets.containsKey(name)) {
            allPlanets.put(name, new Planet(tokens, universe));
            assignOwner(tokens, name);
            return;
        }

        boolean colorChange = allPlanets.get(name).getPlanetColor().equals(tokens[6]);
        int currentFleetSize = Integer.parseInt(tokens[5]);
        allPlanets.get(name).setFleetSize(currentFleetSize);

        if (!colorChange) {
            myPlanets.remove(name);
            myTeammatePlanets.remove(name);
            enemyPlanets.remove(name);
            neutralPlanets.remove(name);

            assignOwner(tokens, name);
        }
    }

    private void assignOwner(String[] tokens, int name) {
        if (tokens[6].equals(universe.getMyColor())) {
            myPlanets.add(name);
        } else if (tokens[6].equals(universe.getMyTeammateColor())) {
            myTeammatePlanets.add(name);
        } else if (tokens[6].equals("null")) {
            neutralPlanets.add(name);
        } else {
            enemyPlanets.add(name);
        }
    }

    public ArrayList<Planet> getAllPlanets() {
        return new ArrayList<>(allPlanets.values());
    }

    public ArrayList<Integer> getMyPlanetIDs() {
        return new ArrayList<>(myPlanets);
    }


}
