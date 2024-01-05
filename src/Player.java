import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;


public class Player {

    static BufferedWriter fileOut = null;

    public static Universe universe = new Universe();
    public static PlanetManager planetManager = new PlanetManager(universe);


    public static void main(String[] args) throws Exception {

        try {

            while (true) {

                getGameState();
                // At first iteration of the game calculate distance tables
                if (!planetManager.isDistanceTablesInitialized()) {
                    planetManager.initDistanceTables();
                }


                ArrayList<Planet> myPlanets = planetManager.getMyPlanets();
                ArrayList<Planet> candidates = new ArrayList<>();
                candidates.addAll(planetManager.getNeutralPlanets());
                candidates.addAll(planetManager.getAxisPlanets());


                // do lexicographic sort to figure out which planet to attack
                // defining custom comparator to do so

                Comparator<Planet> lexicographicComparator = Comparator
                        .comparingInt(Planet::avgDistanceFromMyPlanets)
                        .thenComparingDouble(Planet::getPlanetSize)
                        .thenComparingInt(Planet::calculateTurnsToCapture)
                        .thenComparingInt(p -> p.isAlly() ? 0 : 1).reversed()
                        .thenComparingInt(Planet::getFleetSize).reversed();

                //.comparingDouble(Planet::getPlanetSize).reversed();
                        //.thenComparing(Planet::calculateTurnsToCapture)
                        //.thenComparingInt(p -> p.isAlly() ? 0 : 1).reversed()
                        //.thenComparingInt(p -> p.isNeutral() ? 0 : 1).reversed()
                        //.thenComparingInt(p -> p.isAxis() ? 0 : 1).reversed();





                if (!myPlanets.isEmpty()) {

                    candidates.sort(lexicographicComparator);

                    Planet target = candidates.remove(0);
                    while (target.getRadar().getArrivingFleetsDifference() > 0) {
                        if (candidates.isEmpty()) break;
                        target = candidates.remove(0);
                    }

                    // reminder only my bot forces, for now no communication with other one
                    int joinedForces = 0;
                    ArrayList<Planet> attackers = new ArrayList<>();
                    // sort from closest to...
                    myPlanets.sort(Comparator.comparingInt(target::getDistanceInTurns));

                    boolean enough = false;
                    int turns = Integer.MAX_VALUE;
                    for (Planet m : myPlanets) {
                        joinedForces += m.getFleetSize();
                        attackers.add(m);
                        turns = m.getDistanceInTurns(target);
                        if (joinedForces > target.getFleetSize() +
                                (target.isAxis() ? target.getRadar().getAxisReinforcementInNTurns(turns) : 0) +
                                (target.isAxis() ? target.getNumberOfFleetsOverNTurns(turns) : 0)
                        ) {
                            enough = true;
                            break;
                        }
                    }

                    if (enough) {
                        for (Planet a : attackers) {
                            System.out.println("A " + a.getName() + " " + target.getName() + " " + a.getFleetSize());
                        }
                    }
                }


                System.out.println("M Hello");
                System.out.println("E");

                // collect garbage
                for (Planet planet : planetManager.getPlanets().values()) {
                    planet.getRadar().clearArrivedFleets();
                }
            }
        } catch (Exception e) {
            logToFile("ERROR: ");
            logToFile(e.getMessage());
            e.printStackTrace();
        }
        fileOut.close();

    }


    public static void logToFile(String line) throws IOException {
        if (fileOut == null) {
            FileWriter fstream = new FileWriter("Igralec.log");
            fileOut = new BufferedWriter(fstream);
        }
        if (line.charAt(line.length() - 1) != '\n') {
            line += "\n";
        }
        fileOut.write(line);
        fileOut.flush();
    }


    public static void getGameState() throws NumberFormatException, IOException {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String line = "";
        while (!(line = stdin.readLine()).equals("S")) {

            logToFile(line);

            String[] tokens = line.split(" ");

            switch (line.charAt(0)) {
                case 'U':
                    universe.initialize(line);
                    break;
                case 'P':
                    planetManager.parsePlanet(
                            Integer.parseInt(tokens[1]),
                            Integer.parseInt(tokens[2]),
                            Integer.parseInt(tokens[3]),
                            Float.parseFloat(tokens[4]),
                            Integer.parseInt(tokens[5]),
                            tokens[6]);
                    break;
                case 'F':
                    planetManager.parseFleet(
                            Integer.parseInt(tokens[1]),
                            Integer.parseInt(tokens[2]),
                            Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4]),
                            Integer.parseInt(tokens[5]),
                            Integer.parseInt(tokens[6]),
                            tokens[7]
                    );
                    break;
            }
        }
    }
}
