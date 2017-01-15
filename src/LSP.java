/** LSP specific images and stats
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Image;


public class LSP extends Adventurer {
	
	private static final int HEALTH = 170;
	private static final int DAMAGE = 21;
	private static final int PUNCH_TIME = 500000000;
	private static final int PUNCH_RECHARGE_TIME = 500000000;
	private static final double JUMP_VEL = 22;
	
	private static SpriteSheet walkRun = new SpriteSheet();
	private static SpriteSheet attackBlock = new SpriteSheet();
	
	private static Image jumping;

	private static Image hit;
	
	private static Image blocking;
	
	private static Image attacking1;
	private static Image attacking2;
	
	private static Image standing;
	
	private static Image walking1;
	private static Image walking2;
	
	private static Image running1;
	private static Image running2;
	private static Image running3;
	private static Image running4;
	private static Image running5;
	
	private static Image dying1;
	private static Image dying2;
	
	public LSP(double x, double y) {
		super("LSP", x, y, PUNCH_TIME, PUNCH_RECHARGE_TIME, JUMP_VEL, HEALTH, DAMAGE);
		passPics();
	}
	public void passPics() {
		//init images
		walkRun.loadSpriteSheet("LSPRunningjumping.png");
		attackBlock.loadSpriteSheet("LSPAttackandBlock.png");
		
		jumping = walkRun.getSprite(1213, 965, 91, 110);

		hit = attackBlock.getSprite(75, 467, 149, 145);
		
		blocking = attackBlock.getSprite(15, 5, 121, 269);

		attacking1 = attackBlock.getSprite(199,	5, 150, 269);
		attacking2 = attackBlock.getSprite(375, 5, 248, 269);
		Image [] attackingImages = {attacking1, attacking2};

		standing = walkRun.getSprite(1254, 344, 109, 144);

		walking1 = walkRun.getSprite(1254, 344, 109, 144);	
		walking2 = walkRun.getSprite(1373, 344, 104, 144);

		Image [] walkingImages = {walking1, walking2};
		
		running1 = walkRun.getSprite(1213, 965, 91, 138);
		running2 = walkRun.getSprite(1312,965,91, 138);
		running3 = walkRun.getSprite(1407, 965, 98, 138);
		running4 = walkRun.getSprite(1506, 965, 114, 138);
		running5 = walkRun.getSprite(1625, 965, 126, 138);
		
		Image[] runningImages = { running1, running2, running3,
				running4, running5, running4, running3, running2, running1};
		
		dying1 = attackBlock.getSprite(221, 450, 151, 164);
		dying2 = attackBlock.getSprite(409, 450, 117, 164);
		Image[] dyingImages = {dying1, dying2};
		// give images to super
		loadPics(standing, jumping, blocking, hit, attackingImages, 250, walkingImages, 150, runningImages, 75, dyingImages, 350);
	}
	
	

}
