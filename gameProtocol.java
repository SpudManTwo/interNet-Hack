import java.util.TreeMap;

public class gameProtocol {
		//All enemies are stored by their location
		TreeMap<String,Entity> enemies = new TreeMap<String,Entity>();
		
		public static char[][] world = {
				{'_','_','_','_','_','_','_','_','_','_','_','_','_'},	
				{'|','.','.','%','!','.','.','.','%','.','.','.','|'},
				{'|','.','%','.','.','.','%','.','+','.','%','.','|'},
				{'|','.','.','.','_','_','_','_','_','.','.','.','|'},
				{'|','.','.','.','|','.','^','.','|','.','.','.','|'},
				{'|','.','%','.','|','.','.','.','|','.','%','.','|'},
				{'|','.','.','.','|','_','%','_','|','.','.','.','|'},
				{'|','%','.','.','|','+','.','+','|','.','.','%','|'},
				{'|','.','.','.','_','_','#','_','_','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','%','.','.','.','.','.','%','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','+','.','.','.','.','.','|'},
				{'|','.','.','.','.','+','.','+','.','.','.','.','|'},
				{'|','.','.','.','.','.','+','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','.','|'},
				{'_','_','_','_','_','_','_','_','_','_','_','_','_'}
		};
		public gameProtocol() {
			this.initMonsters();
		}
		public void initMonsters() {
			//Boars First
			Entity alfred = new Entity("Boar Alfred",6,8,1,7,4);
			Entity bonnie = new Entity("Boar Bonnie",6,8,11,7,4);
			Entity cullen = new Entity("Boar Cullen",6,8,3,11,4);
			Entity donal = new Entity("Boar Donal",6,8,9,11,4);
			
			enemies.put("7 1",alfred);
			enemies.put("7 11",bonnie);
			enemies.put("11 3",cullen);
			enemies.put("11 9", donal);
			
			//Zombies Next
			
			Entity aaron = new Entity("Zombie Aaron",12,12,3,1,6);
			Entity baxter = new Entity("Zombie Baxter",12,12,8,1,6);
			Entity clarise = new Entity("Zombie Clarise",12,12,2,5,6);
			Entity derek = new Entity("Zombie Derek",12,12,10,5,6);
			
			enemies.put("1 3",aaron);
			enemies.put("1 8",baxter);
			enemies.put("5 2",clarise);
			enemies.put("5 10", derek);
			
			//Time for some spooky scary skeletons
			
			Entity zed = new Entity("Skeleton Zed",18,12,2,2,8);
			Entity yang = new Entity("Skeleton Yang",18,12,6,2,8);
			Entity xilo = new Entity("Skeleton Xilo",18,12,10,2,8);
			
			enemies.put("2 2",zed);
			enemies.put("2 6",yang);
			enemies.put("2 10",xilo);
			
			//And now the Dragon
			
			Entity dargon = new Entity("Dragon Dargon",40,17,6,6,24);
			
			enemies.put("6 6",dargon);
			
		}
		public String processInput(char input,Player p) {
			String returnStr;
			if(input == 'A'||input=='a') {
				//Attack the mob
				if(world[p.yCoord][p.xCoord]!='%') {
					//Enemy doesn't exist
					returnStr = p.toString()+"\n";
					returnStr += "You swing at nothing.";
					return returnStr;
				}
				else {
					//Enemy exists
					Entity enemy = enemies.get(p.yCoord+" "+p.xCoord);
					p.attackEntity(enemy);
					if(p.HP<=0) {
						returnStr = p.toString()+"\n";
						returnStr += "Your ghostly hands fail to hurt the "+enemy.name;
						return returnStr;
					}
					if(enemy.HP>0) {
						//Enemy is still alive
						enemy.attackEntity(p);
						if(p.HP>0) {
							//Just trading blows
							returnStr = p.toString()+"\n";
							returnStr += "You trade blows with the "+enemy.name;
							return returnStr;
						}
						else {
							//Enemy killed you
							returnStr = p.toString()+"\n";
							returnStr += enemy.name+" kills you. You are now a ghost.";
							return returnStr;
						}
					}
					else {
						//You killed the enemy
						returnStr = p.toString()+"\n";
						returnStr += "You kill the "+enemy.name;
						p.playerMap[p.yCoord][p.xCoord] = '.'; 
						world[p.yCoord][p.xCoord] = '.';
						return returnStr;
					}
				}
			}
			else if(world[p.yCoord][p.xCoord]=='%') {
				//Running away from an enemy				
				int priorHP = p.HP;
				Entity enemy = enemies.get(p.yCoord+" "+p.xCoord);
				enemy.attackEntity(p);
				if(priorHP!=p.HP) {
					//Didn't escape
					if(p.HP>0) {
						//Just got hurt
						returnStr = p.toString()+"\n";
						returnStr += "You get stopped by an attack from the "+enemy.name;
						return returnStr;
					}
					else {
						//Enemy killed you
						returnStr = p.toString()+"\n";
						returnStr += enemy.name+" kills you. You are now a ghost.";
						return returnStr;
					}
				}
				else {
					//Got away
					//Functionally the same code as below for moving
					int newX=p.xCoord,newY=p.yCoord;
					if(input=='W'||input=='w')
						newX--;
					else if(input=='E'||input=='e') 
						newX++;
					else if(input=='S'||input=='s')
						newY++;
					else if(input=='N'||input=='n')
						newY--;
					String extra="";
					if(world[newY][newX]=='|'||world[newY][newX]=='_') {
						newX=p.xCoord;
						newY=p.yCoord;
						extra = "You bump into a wall while trying to escape.";
						
					}
					if(world[newY][newX]=='.'||world[newY][newX]=='^') {
						//Normal Move
						extra ="You escape the "+enemy.name;
					}
					else if(world[newY][newX]=='%') {
						//Encounter Enemy
						enemy = enemies.get(newY+" "+newX);
						extra = "You encounter a new foe. A "+enemy.name;
					}
					if(world[newY][newX]=='|'||world[newY][newX]=='_') {
						newX=p.xCoord;
						newY=p.yCoord;
						extra = "You bump into a wall while trying to escape.";
					}
					else if(world[newY][newX]=='!') {
						p.hasKey = true;
						extra = "You run away and find the key.";
						world[newY][newX] = '.';
					}
					else if(world[newY][newX]=='#') {
						if(p.hasKey) {
							//Unlock the door
							extra = "You escape, unlock a door, and step through.";
						}
						else {
							//Door is locked
							newX=p.xCoord;
							newY=p.yCoord;
							extra = "You bump into a locked door trying to get away.";
						}
					}
					else if(world[newY][newX]=='+') {
						//Health station
						if(p.HP>0) {
							extra = "You feel a magical force heal you as you get away.";
							p.HP = p.maxHP;
						}
						else {
							extra = "You escape to a healing shrine. If you only you had found it earlier.";
						}
					}
					p.playerMap[newY][newX] = '*';
					p.playerMap[p.yCoord][p.xCoord] = world[p.yCoord][p.xCoord];
					p.yCoord = newY;
					p.xCoord = newX;
					returnStr = p.toString()+"\n"+extra;
				}
			}
			else {
				//Moving normally
				int newX=p.xCoord,newY=p.yCoord;
				if(input=='W'||input=='w')
					newX--;
				else if(input=='E'||input=='e') 
					newX++;
				else if(input=='S'||input=='s')
					newY++;
				else if(input=='N'||input=='n')
					newY--;
				else 
					return "Invalid input";
				String extra = "";
				if(world[newY][newX]=='.'||world[newY][newX]=='^') {
					//Normal Move
				}
				else if(world[newY][newX]=='%') {
					//Encounter Enemy
					Entity enemy = enemies.get(newY+" "+newX);
					extra = "You encounter a "+enemy.name;
				}
				else if(world[newY][newX]=='|'||world[newY][newX]=='_') {
					newX=p.xCoord;
					newY=p.yCoord;
					extra = "You bump into a wall.";
				}
				else if(world[newY][newX]=='!') {
					p.hasKey = true;
					extra = "You found the key.";
					world[newY][newX] = '.';
				}
				else if(world[newY][newX]=='#') {
					if(p.hasKey) {
						//Unlock the door
						world[newY][newX] = '.';
						extra = "You unlock the door and step through.";
					}
					else {
						//Door is locked
						newX=p.xCoord;
						newY=p.yCoord;
						extra = "You bump into a locked door.";
					}
				}
				else if(world[newY][newX]=='+') {
					//Health station
					if(p.HP>0) {	
						extra = "You feel a magical force heal you.";
						p.HP = p.maxHP;
					}
					else {
						extra = "A health shrine. If only you had found this place earlier.";
					}
				}
				p.playerMap[newY][newX] = '*';
				p.playerMap[p.yCoord][p.xCoord] = world[p.yCoord][p.xCoord];
				p.yCoord = newY;
				p.xCoord = newX;
				returnStr = p.toString()+"\n"+extra;
			}
			return returnStr;
		}
}
