import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Player {

	static BufferedWriter fileOut = null;

	public static Universe universe = new Universe();
	public static PlanetManager planetManager = new PlanetManager(universe);
	public static FleetManager fleetManager = new FleetManager(universe);


	public static void main(String[] args) throws Exception {

		try {

			while (true) {

				getGameState();
				// At first iteration of the game calculate distance tables
				if (!planetManager.isDistanceTablesInitialized()) {
					planetManager.initDistanceTables();
				}
				// Clean fleet manager, for not containing dead values
				fleetManager.removeDeadFleets();


				ArrayList<Planet> myPlanets = planetManager.getMyPlanets();

				int[] counter = new int[planetManager.getNumberOfPlanets()];

				if (!myPlanets.isEmpty()) {
					for (Planet p : myPlanets) {

						int closest = p.getClosestEnemy();
						if (closest != -1) {
							counter[closest]++;
						}
					}

					int target = -1;
					int count = 0;
					for (int i = 0; i < counter.length; i++) {
						if (count < counter[i]) {
							target = i;
							count = counter[i];
						}
					}

					if (target != -1) {
						for(Planet p : myPlanets) {
							System.out.println("A " + p.getName() + " " + target + " " + 1);
						}
					}

				}
				
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

			//logToFile(line);

			switch (line.charAt(0)) {
				case 'U' -> universe.initialize(line);
				case 'P' -> planetManager.parse(line);
				case 'F' -> fleetManager.parse(line);
			}
		}
	}
}
