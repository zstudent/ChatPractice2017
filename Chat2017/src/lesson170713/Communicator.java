package lesson170713;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;

public class Communicator {
	

	public static void main(String[] args) {
		
		Communicator chat = new Communicator();

		Scanner keyboardScanner = new Scanner(System.in);
		
		new Thread( () -> {
			while (keyboardScanner.hasNextLine()) {
				String line = keyboardScanner.nextLine();
				chat.sendTextToServer(line);
			}
		}).start();
		
//		chat.init(new Consumer<String>() {
//			@Override
//			public void accept(String line) {
//				System.out.println(line);
//			}
//		});
		
		chat.init(System.out::println);
		
	}

	private PrintWriter writer;

	public void init(Consumer<String> consumer) {
		try {
			Socket socket = new Socket("localhost", 10000);
			
			Scanner serverScanner = new Scanner(socket.getInputStream());
			writer = new PrintWriter(socket.getOutputStream());
			
			new Thread( () -> {
				while(serverScanner.hasNextLine()) {
					String line = serverScanner.nextLine();
					consumer.accept(line);
				}
			}).start();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTextToServer(String line) {
		System.out.println("sending to server: " + line);
		writer.println(line);
		writer.flush();
	}

}
