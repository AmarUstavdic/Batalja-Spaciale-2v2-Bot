import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class FleetManager {

    private final Universe universe;
    private final HashMap<Integer, Fleet> myFleets;
    private final HashMap<Integer, Fleet> tmFleets;
    private final HashMap<Integer, Fleet> e1Fleets;
    private final HashMap<Integer, Fleet> e2Fleets;

    public FleetManager(Universe universe) {
        this.universe = universe;
        this.myFleets = new HashMap<>();
        this.tmFleets = new HashMap<>();
        this.e1Fleets = new HashMap<>();
        this.e2Fleets = new HashMap<>();
    }


    public void parse(String lineFromGame) {

        String[] tokens = lineFromGame.split(" ");
        int fleetName = Integer.parseInt(tokens[1]);
        int fleetSize = Integer.parseInt(tokens[2]);
        int origin = Integer.parseInt(tokens[3]);
        int destination = Integer.parseInt(tokens[4]);
        int currentTurn = Integer.parseInt(tokens[5]);
        int neededTurns = Integer.parseInt(tokens[6]);

        // since exactly one of the conditions bellow will be true, table won't be null!
        HashMap<Integer, Fleet> table = null;
        if (tokens[7].equals(universe.getMyColor())) table = myFleets;
        if (tokens[7].equals(universe.getTeammateColor())) table = tmFleets;
        if (tokens[7].equals(universe.getEnemy1Color())) table = e1Fleets;
        if (tokens[7].equals(universe.getEnemy2Color())) table = e2Fleets;

        Fleet fleet;
        // but let's check again just in case :)
        if (table != null) {
            if ((fleet = table.get(fleetName)) != null) {
                fleet.setCurrentTurn(currentTurn);
                fleet.setReferenced(true);
                return;
            }
            fleet = new Fleet(fleetName, fleetSize, origin, destination, currentTurn,neededTurns, tokens[7]);
            fleet.setReferenced(true);
            table.put(fleetName, fleet);
        }
    }


    public void removeDeadFleets() {
        cleanTable(myFleets);
        cleanTable(tmFleets);
        cleanTable(e1Fleets);
        cleanTable(e2Fleets);
    }


    private void cleanTable(HashMap<Integer, Fleet> table) {
        Iterator<Map.Entry<Integer, Fleet>> iterator = table.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Fleet> entry = iterator.next();
            Fleet fleet = entry.getValue();

            // Remove if unreferenced
            if (!fleet.isReferenced()) {
                iterator.remove();
            }
            // Resetting referenced flag
            fleet.setReferenced(false);
        }
    }

    // F 422 1 24 18 8 9 cyan --> this is the last report about fleet
}
