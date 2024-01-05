import java.util.HashMap;

public class Radar {

    // Radar is for tracking all incoming fleets
    // Each planet has its own Radar
    private final Universe universe;
    // <distance from destination in turns, <fleet name, fleet>>
    private final HashMap<Integer, HashMap<Integer, Fleet>> ally;
    private final HashMap<Integer, HashMap<Integer, Fleet>> axis;

    public Radar(Universe universe) {
        this.universe = universe;
        this.ally = new HashMap<>();
        this.axis = new HashMap<>();
    }

    /**
     *  Takes fleet data that is passed to Radar of this Planet.
     *  If fleet.getCurrentTurn() == 1 => newly space born fleet.
     *  Otherwise, we have to update distance from its destination.
     */
    public void update(Fleet fleet) {

        // newly space born fleet
        if (fleet.getCurrentTurn() == 1) {
            HashMap<Integer, HashMap<Integer, Fleet>> map = universe.isAllyFleet(fleet.getFleetColor()) ? ally : axis;
            int distance = distance(fleet.getCurrentTurn(), fleet.getNeededTurns());
            map.computeIfAbsent(distance, k -> new HashMap<>()).put(fleet.getFleetName(), fleet);

        // already existing record of fleet
        } else {
            int oldDistance = distance(fleet.getCurrentTurn() - 1, fleet.getNeededTurns());
            Fleet tmp = null;
            if (ally.containsKey(oldDistance)) {
                tmp = ally.get(oldDistance).remove(fleet.getFleetName());
            }
            boolean isAlly = true;
            if (tmp == null) {
                isAlly = false;
                tmp = axis.get(oldDistance).remove(fleet.getFleetName());
            }
            tmp.setCurrentTurn(fleet.getCurrentTurn());
            if (isAlly) ally.computeIfAbsent(oldDistance - 1, k -> new HashMap<>()).put(tmp.getFleetName(), tmp);
            else axis.computeIfAbsent(oldDistance - 1, k -> new HashMap<>()).put(tmp.getFleetName(), tmp);
        }
    }

    /**
     *  This function should be called after end of attack command is sent!
     *  Clears garbage, fleets that have already reached their destination.
     *  Clears the fleets that will arrive next turn.
     */
    public void clearArrivedFleets() {
        ally.put(1, new HashMap<>());
        axis.put(1, new HashMap<>());
    }

    /**
     * Returns distance in turns from this planet (planet that has this instance of radar).
     */
    private int distance(int currentTurn, int neededTurns) {
        return neededTurns - currentTurn;
    }

    public int getArrivingFleetsDifference() {
        return totalIncomingFleets(ally) - totalIncomingFleets(axis);
    }

    public int getAxisReinforcementInNTurns(int nTurns) {
        int acc = 0;
        for (int i = 0; i < nTurns; i++) {
            if (axis.containsKey(i)) {
                for (Fleet f : axis.get(i).values()) {
                    acc += f.getFleetSize();
                }
            }
        }
        return acc;
    }

    private int totalIncomingFleets(HashMap<Integer, HashMap<Integer, Fleet>> map) {
        return map.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .mapToInt(Fleet::getFleetSize)
                .sum();
    }


}
