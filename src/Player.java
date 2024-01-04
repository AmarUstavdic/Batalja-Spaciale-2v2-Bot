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


	public static void main(String[] args) throws Exception {

		try {

			Random rand = new Random();

			while (true) {

				getGameState();

				ArrayList<Integer> myPlanets = planetManager.getMyPlanetIds();
				ArrayList<Integer> potentialTargets = planetManager.getAllPotentialTargets();


				if (!potentialTargets.isEmpty() && !myPlanets.isEmpty()) {
					for (int i = 0 ; i < myPlanets.size() ; i++) {
						String myPlanet = myPlanets.get(i).toString();
						int randomEnemyIndex = rand.nextInt(potentialTargets.size());
						String randomTargetPlanet = String.valueOf(potentialTargets.get(randomEnemyIndex));
						/*
							- printing the attack will tell the game to attack
							- be careful to only use System.out.println for printing game commands
							- for debugging you can use logToFile() method
						*/
						System.out.println("A " + myPlanet + " " + randomTargetPlanet);
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

			logToFile(line); 

			switch (line.charAt(0)) {
				case 'U' -> universe.initialize(line);
				case 'P' -> planetManager.parse(line);
			}
		}
	}
}
