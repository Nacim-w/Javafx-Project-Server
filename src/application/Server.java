package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Server {
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
public Server(ServerSocket serverSocket)  {
		this.serverSocket = serverSocket;
		try {
			this.socket = serverSocket.accept();
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			System.out.println("error Creating Server");
			e.printStackTrace();
		}
		
	}
public void sendMessageToClient(String messageToClient) {
	try {
		bufferedWriter.write(messageToClient);
		bufferedWriter.newLine();
		bufferedWriter.flush();
		
	} catch(IOException e) {
		e.printStackTrace();
		System.out.println("error sending message to the client");
		closeEverything(socket , bufferedReader,bufferedWriter);
		
	}
}

public void recieveMessageFromClient(VBox vBox) {
	new Thread(new Runnable() {
		@Override
		public void run() {
			while(socket.isConnected()) {
				try {
				String messageFromClient= bufferedReader.readLine();
				ServerController.addLabel(messageFromClient,vBox );
				System.out.println("hello");
				
				}
				catch(IOException e) {
					e.printStackTrace();
					System.out.println("Error recieving message From the client");
					closeEverything(socket,bufferedReader,bufferedWriter);
					break;
				}
			}
			
		}
	}).start();
}
public void closeEverything(Socket socket , BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
	try {
		if(bufferedReader != null) {
			bufferedReader.close();
		}
		if(bufferedWriter != null) {
			bufferedWriter.close();
		}
		if(socket!=null) {
			socket.close();
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
}


}
