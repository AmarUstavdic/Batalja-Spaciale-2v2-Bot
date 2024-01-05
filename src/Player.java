import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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


                ArrayList<Planet> minePlanets = planetManager.getMyPlanets();
                ArrayList<Planet> neutralPlanets = planetManager.getNeutralPlanets();
                ArrayList<Planet> axisPlanets = planetManager.getAxisPlanets();

                if (!minePlanets.isEmpty()) {
                    if (!neutralPlanets.isEmpty()) {
                        // focus on neutral planets while there are neutral planets
                        for (Planet m : minePlanets) {
                            for (Planet n : neutralPlanets) n.calculateUtility(m, 0.7);
                        }
                        neutralPlanets.sort(Comparator.comparingDouble(Planet::getUtility).reversed());

                        int target = 0;
                        while (target < neutralPlanets.size() - 1 && (neutralPlanets.get(target).isAlly() || target == neutralPlanets.size() - 1)) {
                            target++;
                        }

                        for (Planet m : minePlanets) {
                            System.out.println("A " + m.getName() + " " + neutralPlanets.get(target).getName() + " " + m.getFleetSize());
                        }

                    } else {




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

        BufferedReader stdin = new BufferedReader(new java.io.InputStreamReader(System.in));

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
