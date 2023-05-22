import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class server implements Runnable {
	// Network
	Socket socketA;
	Socket socketB;

//	DataInputStream inputA;
//	DataOutputStream outputA;
//	DataInputStream inputB;
//	DataOutputStream outputB;
//	inputB = new DataInputStream(socketB.getInputStream());
//	outputB = new DataOutputStream(socketB.getOutputStream());

	int roomNum;
	int playerNum;

	int[][] ports = { { 2221, 2222 }, { 2223, 2224 }, { 2225, 2226 }, { 2227, 2228 } };
	static ArrayList<String> wordRandom = new ArrayList<String>();
	ArrayList<ArrayList<String>> Winner = new ArrayList<ArrayList<String>>();

	server(int room, int player) throws IOException {
		this.roomNum = room;
		this.playerNum = player;
		this.wordRandom = randomWords();
	}

	@Override
	public void run() {
		System.out.println("\tRoom " + roomNum + " is ready");
		randomWords(); // Generate 1 set of vocabulary

		// ------------------------Connection-----------------------------------------------------
		boolean player1Connect = false;
		boolean player2Connect = false;
		while (!player1Connect || !player2Connect) {
			try {
				if (playerNum == 1) {
					while (!player1Connect) {
						ServerSocket serverA = new ServerSocket(ports[roomNum - 1][0]);
						try {
							socketA = serverA.accept();
							setPlayerNum(2);
							player1Connect = true;
						} catch (Exception error) {
							System.out.println(error);
						}
					}
				}
				System.out.println("Waiting player2");
				while (!player2Connect) {
					ServerSocket serverB = new ServerSocket(ports[roomNum - 1][1]);
					try {
						socketB = serverB.accept();

						player2Connect = true;

					} catch (Exception error) {
						System.out.println(error);
						System.out.println("allPlayerConnection section (Server)");
					}

				}
			} catch (Exception error) {
				System.out.println(error);
				System.out.println("Connect section (Server)");
			}
		}
		System.out.println("Players are ready (Server)");
		// -------------------------In game------------------------
		boolean stateGame = false;
		while (!stateGame) {

			try {
				// Create the input & output socket
				// player1
				DataInputStream inputA = new DataInputStream(socketA.getInputStream());
				DataOutputStream outputA = new DataOutputStream(socketA.getOutputStream());
				// player2
				DataInputStream inputB = new DataInputStream(socketB.getInputStream());
				DataOutputStream outputB = new DataOutputStream(socketB.getOutputStream());

				// Send the set of vocabulary
				for (int i = 0; i < wordRandom.size(); i++) {
					outputA.writeUTF(wordRandom.get(i));
					outputB.writeUTF(wordRandom.get(i));
				}
				outputA.writeUTF("end");
				outputB.writeUTF("end");
				
	
				System.out.println("Complete (Server)");
				// The end of try catch block
			} catch (Exception error) {
				System.out.println(error);
				System.out.println("In-game section (Server)");
			}

		}

	}

	// Random word in gameFile and insert it in woed_random
	private static ArrayList<String> randomWords() {
		Random ran = new Random();
		ArrayList<String> tmpList = new ArrayList<String>();
		File file = new File("HwOSLabDuo/resc/miniVocab.txt");
		String line;
		Scanner scan;
		try {

			for (int i = 0; i < 5; i++) {
				int count = 0;
				int tmpNum = ran.nextInt(834) + 1; // generate new number
				scan = new Scanner(file); // must create a new one before start loop

				while (scan.hasNextLine()) {
					line = scan.nextLine();
					count++;
					if (count == tmpNum) {
						// System.out.println(line);
						tmpList.add(line);
					}

				}
				// System.out.println(i+": "+tmpNum); //details

			}
		} catch (Exception error) {
			System.out.println(error);
			System.out.println("Random Words (Server)");
		}
		return tmpList;

	}

	private static boolean checkLetterServer(int turn, String letter) {

		String tmpWord = wordRandom.get(turn - 1);

		if (tmpWord.contains(tmpWord)) {
			return true;
		}
		return false;
	}

	private void setPlayerNum(int tmp) {
		this.playerNum = tmp;
	}

}
