
public class Player extends Entity {
	int critRange;
	boolean hasKey;
	int maxHP;
	char[][] playerMap = {
			{'_','_','_','_','_','_','_','_','_','_','_','_','_'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','_','_','_','_','_','?','?','?','|'},
			{'|','?','?','?','|','?','?','?','|','?','?','?','|'},
			{'|','?','?','?','|','?','?','?','|','?','?','?','|'},
			{'|','?','?','?','|','_','?','_','|','?','?','?','|'},
			{'|','?','?','?','|','?','?','?','|','?','?','?','|'},
			{'|','?','?','?','_','_','?','_','_','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'|','?','?','?','?','?','?','?','?','?','?','?','|'},
			{'_','_','_','_','_','_','_','_','_','_','_','_','_'}
	};
	public Player(String n, int health, int armor, int X, int Y, int damage,int minCrit) {
		super(n, health, armor, X, Y, damage);
		maxHP = health;
		critRange = minCrit;
		hasKey = false;
	}
	public void die() {
		if(hasKey) {
			gameProtocol.world[1][4]='!';
		}
		HP = 0;
		name = name.trim();
		if(!name.trim().equals("Glass Cannon"))
			name = name+"\t";
		name = "Ghost of "+name+"\t";
		AC = 99;
		damageDiceNumber = 0;
		critRange = 99;
	}
	public String toString() {
		if(xCoord==6&&yCoord==4)
			return "The "+this.name.trim()+" lives happily ever after.";
		String master="";
		for(int r=0;r<18;r++) {
			if(r==1) {
				master+="Name: "+this.name+"";
			}
			else if(r==2) {
				master+="HP: "+this.HP+"\t\t\t\t";
			}
			else if(r==4) {
				master+="AC: "+this.AC+"\t\t\t\t";
			}
			else {
				master+="\t\t\t\t";
			}
			for(int c=0;c<13;c++) {
				master+=playerMap[r][c];
			}
			master+="\n";
		}
		return master;
	}
}
