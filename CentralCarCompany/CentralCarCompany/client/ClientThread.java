package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
	Socket threadSocket;
	String answer;
	
	public ClientThread(Socket client){
		this.threadSocket = client;

	}
	
	public void run() {
		// Cr�ation du flux en entr�e
		BufferedReader inFromServer;
		try {
			inFromServer = new BufferedReader(
					new InputStreamReader(
							threadSocket.getInputStream()));
			
			// Lecture des donn�es arrivant du serveur
			while (true){
			answer = inFromServer.readLine();
			System.out.println("" + answer);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
