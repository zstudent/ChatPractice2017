package lesson170713;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ChatServer {

	private static final int DEFAULT_PORT = 10000;
	private static List<ChatSession> sessions;
	private static ExecutorService broadcastService;
	static int userCount = 0;

	public static void main(String[] args) {

		System.out.println("start");
		
		sessions = new ArrayList<>();
		
		broadcastService = Executors.newCachedThreadPool();

		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
			

			while (true) {
				Socket socket = serverSocket.accept(); // waiting for connection
				System.out.println("Got connection " + socket);
				new Thread(() -> {  // closure
					long delay = sessions.isEmpty()? 2000 : 100;
					String name = "User" + userCount++;

					ChatSession chatSession = new ChatSession(socket, name, delay);
					
					broadcastUserName(chatSession);
					
					sessions.add(chatSession);
					sendNameList2Client(chatSession);
					
					System.out.println("Sessions size = " + sessions.size());
					chatSession.processConnection( 
							ChatServer::broadcast,
							ChatServer::removeSession);
					
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void broadcastUserName(ChatSession chatSession) {
		String command = "/add " + chatSession.getName();
		broadcast(command);
	}

	private static void sendNameList2Client(ChatSession chatSession) {
		String nameList = "/list";
		for (ChatSession s : sessions) {
			nameList += " " + s.getName();
		}
		chatSession.send2Client(nameList);
	}

	private static void broadcast(String line) {
		for (ChatSession session : sessions) {
			broadcastService.execute( () -> {
				session.send2Client(line);
			});
		}
	}
	
	private static void removeSession(ChatSession session) {
		sessions.remove(session);
		broadcast("/remove " + session.getName());
		System.out.println("removed " + session);
		System.out.println("Sessions size = " + sessions.size());
	}

}
