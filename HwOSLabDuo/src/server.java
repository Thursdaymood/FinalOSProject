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
	static ArrayList<String> wordRandom = new ArrayList<String>(5);
	server(int room, int player) throws IOException {
		this.roomNum = room;
		this.playerNum = player;
	}

	@Override
	public void run() {
		System.out.println("\tRoom "+roomNum+" is ready");
		randomWords(); // Generate 1 set of vocabulary

		// ------------------------Connection-----------------------------------------------------
		boolean allPlayerConnect = false;
		boolean player1Connect = false;
		boolean player2Connect = false;
		while (!allPlayerConnect) {	
			try {
				if(playerNum ==1){
					while (!player1Connect) {
						ServerSocket  serverA = new ServerSocket(ports[roomNum - 1][0]);
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
					ServerSocket  serverB = new ServerSocket(ports[roomNum - 1][1]);
					try {
						socketB = serverB.accept();

						player2Connect = true;

					} catch (Exception error) {
						System.out.println(error);
						System.out.println("allPlayerConnection section");
					}

				}
			} catch (Exception error) {
				System.out.println(error);
			}
			System.out.println("Players are ready");
			allPlayerConnect = true;
		}
		
		
		//-------------------------In game------------------------
		boolean stateGame = false;
		String sentMessage;
		String receivedMessage;
		while(!stateGame) {
			
			try{
				// Create the input & output socket
				DataInputStream inputA = new DataInputStream(socketA.getInputStream());
				DataOutputStream outputA = new DataOutputStream(socketA.getOutputStream());
				
				DataInputStream inputB = new DataInputStream(socketB.getInputStream());
				DataOutputStream outputB = new DataOutputStream(socketB.getOutputStream());

				// Send the set of vocabulary
				for(int i = 0 ; i < wordRandom.size() ; i++){
					outputA.writeUTF(wordRandom.get(i));
					outputB.writeUTF(wordRandom.get(i));
				}
				outputA.writeUTF("end");
				outputB.writeUTF("end");
				// (loop: play -> wait)
				// (receive word -> check the word -> return correct/wrong -> return "your turn" to player2)
				boolean inPlaySection = true;
				while(inPlaySection){
					
				}
				
				
				
				
				
				
				
				
				
				
				stateGame = true;
			// The end of try catch block	
			}catch(Exception error) {
				System.out.println(error);
				System.out.println("In-game section");
			}

			
		}
		
	}
	// Random word in gameFile and insert it in woed_random
	private static void randomWords() {
		Random ran = new Random();
		File file = new File("HwOSLabDuo\\resc\\miniVocab.txt");
		int tmpNum = ran.nextInt(834) + 1;
		String line;
		Scanner scan;
		try {

			for (int i = 0; i < 5; i++) {

				int count = 0;
				scan = new Scanner(file); // must create a new one before start loop

				while (scan.hasNextLine()) {

					line = scan.nextLine();
					if (count == tmpNum) {
						System.out.println(line);
					}
					count++;
				}
				tmpNum = ran.nextInt(835) + 1; // generate new number
				// System.out.println(i+": "+tmpNum); //details

			}
		} catch (Exception error) {
			System.out.println(error);
		}

	}
	
	private static boolean checkLetterServer(int turn, String letter) {
		
		String tmpWord = wordRandom.get(turn-1);
		
		if(tmpWord.contains(tmpWord)){
			return true;
		}
		return false;
	}
	
	private void setPlayerNum(int tmp) {
		this.playerNum = tmp;
	}

	private void taskA(Socket socket) {
		System.out.println("HelloA");

	}

	private void taskB(Socket socket) {
		System.out.println("HelloB");
	}

}
