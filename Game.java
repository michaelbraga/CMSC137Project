import java.net.*;
import java.io.*;
import java.util.*;

import chatclient.*;
import chatserver.*;

public class Game {
	public static void main(String[] args) {
		/*
			Temporary environment to test chat system
		*/
		System.out.println("[1] - Host Game\n[2] - Join Game");
		int choice = new Scanner(System.in).nextInt();

		if(choice == 1){
			System.out.print("Port:(8080) ");
			String port = new Scanner(System.in).nextLine();
			System.out.print("Username: ");
			String un = new Scanner(System.in).nextLine().trim();
			System.out.println("==========================================");
			new ChatServer((port.isEmpty()? 8080:Integer.parseInt(port)), un).run();
		}

		else{
			System.out.print("Port:(8080) ");
			String port = new Scanner(System.in).nextLine();
			System.out.print("Username: ");
			String un = new Scanner(System.in).nextLine().trim();
			System.out.print("Ip address:(localhost) ");
			String ip = new Scanner(System.in).nextLine().trim();
			ChatClient c = new ChatClient((ip.isEmpty()?"localhost":ip), (port.isEmpty()? 8080:Integer.parseInt(port)), un);
			System.out.println("==========================================");
			if(c.connect()){
				c.run();
			}
		}
	}
}
