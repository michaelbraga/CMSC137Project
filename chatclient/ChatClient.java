package chatclient;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient{
	private String ipAddress;
	private int portNumber;
	private String username;

	private Socket socket = null;

	private BufferedReader in = null;
	private PrintWriter out = null;

	private MessageReceiver mr = null;
	private MessageSender ms = null;


	public ChatClient(String ip, int port, String name){
		this.ipAddress = ip;
		this.portNumber = port;
		this.username = name;
	}

	public boolean connect(){
		try{
			this.socket = new Socket(ipAddress, portNumber);
			DataOutputStream toServer = new DataOutputStream(this.socket.getOutputStream());
			DataInputStream fromServer = new DataInputStream(this.socket.getInputStream());

			// check if full
			boolean full = fromServer.readBoolean();
			if(full){
				this.socket.close();
				this.socket = null;
				System.out.println("Error: Server denied request! Server is full.");
				return false;
			}

			// send username to server to check for duplicates
			toServer.writeUTF(this.username);
			toServer.flush();
			// wait for server response
			boolean requestAccepted = fromServer.readBoolean();
			// if server accepted the request return true, else return false
			if(!requestAccepted){
				this.socket.close();
				this.socket = null;
				System.out.println("Error: Server denied request! Use a different username.");
			}
			return requestAccepted;
		}
		catch(Exception e){
			System.out.println("Error: Cannot connect to server!");
			this.socket = null;
			return false;
		}
	}

	public void run(){
		if(this.socket != null){
			try{
				// Stream readers and writers for send and receiving strings
				this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	           		this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));

				// thread for receiving message
				this.mr = new MessageReceiver(this.in, this);
				this.mr.start();

				// object for sending message
				this.ms = new MessageSender(this.out);

				System.out.println("*Connected to chat server at port " + portNumber);
				System.out.println("'~!exit' to leave.\n");

				// infinite loop for sending messages
				Scanner s = new Scanner(System.in);
				String m;
				while (true) {
					m = s.nextLine().toString();
					this.ms.sendMessage(m);

					if(m.equals("~!exit")){
						this.close(1);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Cannot get streams from sockets input/ouput!");
			}
		}
		else{
			System.out.println("Error: Connection to server has not been established!");
		}
	}

	public void close(int stat){
		try{
			if(stat == 1) this.mr.stop();
			if(this.in != null) this.in.close();
			if(this.out != null) this.out.close();
			if(this.socket != null) this.socket.close();
			System.exit(0);

		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
