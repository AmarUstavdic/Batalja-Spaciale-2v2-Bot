import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class Player {


    static BufferedWriter fileOut = null;




    public static void main(String[] args) throws Exception {

        boolean loggingEnabled = true;

        Universe universe = new Universe();
        PlanetManager planetManager = new PlanetManager(universe);



        ArrayList<Layer> layers = new ArrayList<>();
        layers.add(new Layer(20, new SigmoidActivation()));
        layers.add(new Layer(20, new SigmoidActivation()));
        layers.add(new Layer(1, new SigmoidActivation()));

        NeuralNetwork neuralNetwork = new NeuralNetwork(5, layers);
        neuralNetwork.loadWeightsAndBiasesFromCSV("./data.csv");


        try {

            BufferedReader stdin = new BufferedReader(new java.io.InputStreamReader(System.in));

            while (true) {

                String line = "";
                while (!(line = stdin.readLine()).equals("S")) {

                    if (loggingEnabled) logToFile(line);
                    char command = line.charAt(0);

                    switch (command) {
                        case 'U' -> universe.init(line);
                        case 'P' -> planetManager.sync(line);
                        case 'F' -> {
                            // do something with fleet data
                        }
                        case 'M' -> {
                            // do something with message that ally sent you last turn
                        }
                        default -> {

                        }
                    }
                }

                ArrayList<Planet> planets = planetManager.getAllPlanets();
                ArrayList<Integer> myPlanets = planetManager.getMyPlanetIDs();
                Random random = new Random();
                Planet p = planets.get(random.nextInt(planets.size()));
                for (int i : myPlanets) {
                    System.out.println("A " + i + " " + p.getName());
                }


                /*
                for (Planet planet : planets) {
                    double score = neuralNetwork.predict(planet.toNeuralNetworkInput())[0];
                    planet.setCandidateTargetScore(score);
                }

                // sorting them in descending order based on score from ANN
                planets.sort(Comparator.comparingDouble(Planet::getCandidateTargetScore).reversed());


                ArrayList<Integer> myPlanetIDs = planetManager.getMyPlanetIDs();
                if (!myPlanetIDs.isEmpty()) {
                    for (Integer mpid : myPlanetIDs) {
                        System.out.println("A " + mpid + " " + planets.getFirst().getName());
                    }
                }


                 */



				/*
					- send a hello message to your teammate bot :)
					- it will receive it form the game next turn (if the bot parses it)
				 */
                System.out.println("M Hello");
				/*
				  	- E will end my turn.
				  	- you should end each turn (if you don't the game will think you timed-out)
				  	- after E you should send no more commands to the game
				 */
                System.out.println("E");
            }
        } catch (Exception e) {
            if (loggingEnabled) {
                logToFile("ERROR: ");
                logToFile(e.getMessage());
                logToFile(e.toString());
            }
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

}