
public class Entity {
	String name;
	int HP,AC;
	int xCoord,yCoord;
	int damageDiceNumber;
	public Entity(String n,int health,int armor, int X, int Y, int damage) {
		name = n;
		HP = health;
		AC = armor;
		xCoord = X;
		yCoord = Y;
		damageDiceNumber = damage;
	}
	public void attackEntity(Entity underAttack) {
		//Roll to hit
		int toHit = (int)(Math.random() * 20)+1;
		
		if (toHit<underAttack.AC)
			return;
		
		//Attack landed, damage time.
		int damage;
		if(damageDiceNumber>10) {
			damageDiceNumber/=10;
			damage = (int)(Math.random() * damageDiceNumber)+1;
			damage += (int)(Math.random() * damageDiceNumber)+1;
		}
		else {

			damage = (int)(Math.random() * damageDiceNumber)+1;
		}
		
		//Silly ghosts, you don't do damage.
		if(this instanceof Player && toHit>= ((Player)this).critRange) {
			damage*=2;
		}
		if(this instanceof Player && this.HP==0)
			damage--;
		
		underAttack.HP -= damage;
		if(underAttack.HP<=0)
			underAttack.die();
	}
	public void die() {
		//Only for when enemy creatures die
		gameProtocol.world[yCoord-1][xCoord] = '.';
	}
}
