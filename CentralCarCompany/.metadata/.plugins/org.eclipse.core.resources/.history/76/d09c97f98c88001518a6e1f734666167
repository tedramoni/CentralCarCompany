package client;

import java.io.*;
import java.net.*;

public class Client {

	public static void main(String argv[]) throws Exception {
		String request;
		String answer;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));


		// Creation de la socket client, demande de connexion
		Socket clientSocket = new Socket("localhost", 8080);


		// Creation du flux en sortie
		PrintWriter outToServer = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								clientSocket.getOutputStream())),
								true);

		new ClientThread(clientSocket).start();
		request = "";
		while(!request.equals("stop")){

			request = inFromUser.readLine();



			// Emission des donnees au serveur

			outToServer.println(request);
		}
		clientSocket.close();
	}
}
