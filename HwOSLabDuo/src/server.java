import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

	server(int room, int player) throws IOException {
		this.roomNum = room;
		this.playerNum = player;
	}

	@Override
	public void run() {
		boolean player1Connect = false;
		boolean player2Connect = false;
		boolean stateGame = false;
		System.out.println("\tRoom "+roomNum+" is ready");
//		Thread player1;
//		Thread player2;
		
		// Check players are connected
		while (true) {	
			try {
				if(playerNum ==1){
					while (!player1Connect) {
						ServerSocket  serverA = new ServerSocket(ports[roomNum - 1][0]);
						try {
							socketA = serverA.accept();
//							player1 = new Thread(() -> {
//								taskA(socketA);
//							});
//							player1.start();
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
//						player2 = new Thread(() -> {
//							taskB(socketB);
//						});
//						player2.start();
						player2Connect = true;

					} catch (Exception error) {
						System.out.println(error);
					}

				}
			} catch (Exception error) {
				System.out.println(error);
			}
			System.out.println("Players are ready");
			break;

		}
		
		
		//-------------------------In game------------------------
		while(!stateGame) {
			System.out.println("hi");
		}
		
		// System.out.println("Players are in the room");
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
