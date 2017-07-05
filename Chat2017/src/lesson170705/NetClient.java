package lesson170705;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetClient {
	
	public static void main(String[] args) {
		
		try {
			Socket socket = new Socket("utcnist.colorado.edu", 13);
			
			Scanner scanner = new Scanner(socket.getInputStream());

//			PrintWriter writer = new PrintWriter(socket.getOutputStream());
//			
//			writer.println("GET / HTTP/1.0");
//			writer.println("");
//			writer.flush();
//			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				System.out.println(line);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
