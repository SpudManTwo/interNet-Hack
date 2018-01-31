import java.net.*;
import java.io.*;

public class interNetHackClient {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
            System.err.println(
                "Usage: java interNetClient <host name> <port number>");
            System.exit(1);
        } 
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        boolean playing = true;
        boolean waitingForResponse = false;
        try (
                Socket netHackSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(netHackSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(netHackSocket.getInputStream()));
            ) {
                BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
                char fromServer;
                System.out.println("Please input your selection of class. R = Rogue, B = Brawler, T = Tank, G = Glass Cannon");
                
                for(;playing;) {               	
                	if(!waitingForResponse) {
                		out.println(stdIn.readLine());                		
                		waitingForResponse = true;
                	}
                	
                	for(;in.ready();)  {
                		fromServer = (char)in.read();
                		System.out.print("" + fromServer);
                		if(!in.ready())
                			waitingForResponse = false;
                	}
        	}
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
                System.exit(1);
            }
	}
}
