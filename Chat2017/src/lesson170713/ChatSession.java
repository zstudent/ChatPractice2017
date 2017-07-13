package lesson170713;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class ChatSession {

	private PrintWriter writer;

	void processConnection(Socket socket, Consumer<String> broadcaster) {
	
		try {
			Scanner scanner = new Scanner(socket.getInputStream());
	
			writer = new PrintWriter(socket.getOutputStream());
	
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
				broadcaster.accept(line);
				if (line.equals("bye")) {
					break;
				}
			}
			
			socket.close();
	
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void send2Client(String line) {
		writer.println(" > " + line);
		writer.flush();
	}

}
