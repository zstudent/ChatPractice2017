package lesson170705;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NetExample {

	private static final int DEFAULT_PORT = 10000;

	public static void main(String[] args) {

		System.out.println("start");

		try {
			ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);

			while (true) {
				Socket socket = serverSocket.accept(); // waiting for connection
				System.out.println("Got connection " + socket);
				new Thread(() -> {
					processConnection(socket);
				}).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void processConnection(Socket socket) {

		try {
			Scanner scanner = new Scanner(socket.getInputStream());

			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
				writer.println("Ok");
				writer.flush();
				if (line.equals("bye")) {
					break;
				}
			}
			
			socket.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
