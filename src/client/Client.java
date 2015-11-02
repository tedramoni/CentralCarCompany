package client;

import java.io.*;
import java.net.*;

import serveur.ServerThread;

public class Client {
	
		public static void main(String argv[]) throws Exception {
			String request;
			String answer;
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

			// Création de la socket client, demande de connexion
			Socket clientSocket = new Socket("localhost", 8080);
			//Socket clientSocket = new Socket("172.19.37.56", 8080);


			// Création du flux en sortie
			PrintWriter outToServer = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									clientSocket.getOutputStream())),
									true);
			
			
			request="";

			while(!request.equals("stop")){
				
				request = inFromUser.readLine();
				
				// Emission des données au serveur
				outToServer.println(request);
				
				ClientThread client = new ClientThread(clientSocket);
			}
			clientSocket.close();	 
		}
		
	}
