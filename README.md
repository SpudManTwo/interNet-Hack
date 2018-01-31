# interNet-Hack
Short little proof of concept of a Java based client and server model for a game akin to Net Hack. Supports up to 4 players concurrently and begins the game when the first connects.

Author: Matt Kaufman

Setup:
0. Take Note of the Server's IP Address
1. Compile and run the interNetHackServer.java code with the parameter of the port number. e.g. java interNetHackServer 99999
2. Compile and run the interNetHackClient.java code with the parameters of the Server's IP Address and Port Number. e.g. java interNetHackClient 10.0.0.1 99999
3. When prompted by the client, select your class using R,B,T, or G.
4. Play and slay the dragon.

Commands (Not Case Sensitive):
A - Attack the monster in your square.
N - Move North one space
W - Move West one space.
E - Move East one space.
S - Move South one space.