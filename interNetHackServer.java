import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class interNetHackServer {
	private static Player makePlayer(char c,int X,int Y) {
		Player n;
		if(c=='R'||c=='r') {
			n = new Player("Rogue\t\t\t",10,8,X,Y,8,17);
		}
		else if(c=='T'||c=='t') {
			n = new Player("Tank\t\t\t",20,16,X,Y,8,20);
		}
		else if(c=='B'||c=='b') {
			n = new Player("Brawler\t\t\t",20,8,X,Y,24,19);
		}
		else {
			n = new Player("Glass Cannon\t\t",10,16,X,Y,24,19);
		}
		n.playerMap[Y][X] = '*';
		return n;
	}
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java interNetHackServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		gameProtocol protocol = new gameProtocol();
		
		try 
			(
			//Try with these parameters
			ServerSocket serverSocket = new ServerSocket(portNumber);
			
			Socket player1Socket = serverSocket.accept();
			PrintWriter player1out = new PrintWriter(player1Socket.getOutputStream(), true);
			BufferedReader player1in = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
			
			Socket player2Socket = serverSocket.accept();
			PrintWriter player2out = new PrintWriter(player2Socket.getOutputStream(), true);
			BufferedReader player2in = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
			
			Socket player3Socket = serverSocket.accept();
			PrintWriter player3out = new PrintWriter(player3Socket.getOutputStream(), true);
			BufferedReader player3in = new BufferedReader(new InputStreamReader(player3Socket.getInputStream()));
				
			Socket player4Socket = serverSocket.accept();
			PrintWriter player4out = new PrintWriter(player4Socket.getOutputStream(), true);
			BufferedReader player4in = new BufferedReader(new InputStreamReader(player4Socket.getInputStream()));
				
		)
			{
			
			Player one = null;		
			Player two = null;
			Player three = null;
			Player four = null;
			
			for(;true;) {
				if(player1in.ready()) {
					String tmp = player1in.readLine();
					if(tmp.length()>0) {
						char input = tmp.charAt(0);
						if(one==null) {
							one = makePlayer(input,6,12);
							player1out.println("A = Attack,N = North, S = South, W = West, E = East"+one.toString());
						}
						else {
							player1out.println(protocol.processInput(input,one));
						}
					}
					else {
						player1out.println("Please enter a command.");
					}
				}
				
				else if(player2in.ready()) {
					String tmp = player2in.readLine();
					if(tmp.length()>0) {
						char input = tmp.charAt(0);
						if(two==null) {
							two = makePlayer(input,8,14);
							player2out.println("A = Attack,N = North, S = South, W = West, E = East"+two.toString());					
						}
						else {
							player2out.println(protocol.processInput(input,two));
						}
					}
					else {
						player2out.println("Please enter a command.");
					}
				}
				else if(player3in.ready()) {
					String tmp = player3in.readLine();
					if(tmp.length()>0) {
						char input = tmp.charAt(0);
						if (three==null) {
							three = makePlayer(input,6,16);
							player3out.println("A = Attack,N = North, S = South, W = West, E = East"+three.toString());						
						}
						else {
							player3out.println(protocol.processInput(input,three));
						}
					}
					else {
						player3out.println("Please enter a command.");
					}
				}
				else if(player4in.ready()) {
					String tmp = player4in.readLine();
					if(tmp.length()>0) {
						char input = tmp.charAt(0);
						if(four==null) {
							four = makePlayer(input,4,14);
							player4out.println("A = Attack,N = North, S = South, W = West, E = East"+four.toString());
						}
						else {
							player4out.println(protocol.processInput(input,four));
						}
					}
					else {
						player4out.println("Please enter a command.");
					}
				}
				
			}
		}
		catch (Exception e) {
			if(e instanceof IOException) {
				System.out.println("Exception caught when trying to listen on port "+ portNumber+" or listening for a connection");
		        System.out.println(e.getMessage());
			}
			else {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
}
