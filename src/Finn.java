/** Finn specific images and stats
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Image;


public class Finn extends Adventurer{
	
	private static final int HEALTH = 125;
	private static final int DAMAGE = 25;
	private static final int PUNCH_TIME = 500000000;
	private static final int PUNCH_RECHARGE_TIME = 500000000;
	
	private static final double JUMP_VEL = 25;
	
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
	private static Image walking3;
	private static Image walking4;
	private static Image walking5;
	
	private static Image running1;
	private static Image running2;
	private static Image running3;
	private static Image running4;
	private static Image running5;
	private static Image running6;
	private static Image running7;
	private static Image running8;
	
	private static Image dying1;
	private static Image dying2;
	
	public Finn(double x, double y) {
		super("Finn", x, y + 5, PUNCH_TIME, PUNCH_RECHARGE_TIME, JUMP_VEL, HEALTH, DAMAGE);
		passPics();
	}
	public void passPics() {
		//init images
		walkRun.loadSpriteSheet("FinnRunningjumping.png");
		attackBlock.loadSpriteSheet("FinnAttackandBlock.png");
		
		jumping = walkRun.getSprite(548, 716, 138, 151);
		
		hit = attackBlock.getSprite(186, 678, 158, 147);

		blocking = attackBlock.getSprite(165, 248, 164, 274);

		attacking1 = attackBlock.getSprite(359, 244, 177, 270);
		attacking2 = attackBlock.getSprite(584, 244, 242, 270);
		Image [] attackingImages = {attacking1, attacking2};

		standing = walkRun.getSprite(248, 134,94, 153);
		
		walking1 = walkRun.getSprite(12, 137, 137, 156);
		walking2 = walkRun.getSprite(147, 137, 102, 156);
		walking3 = walkRun.getSprite(248,137, 89, 156);
		walking4 = walkRun.getSprite(346, 137, 109, 156);
		walking5 = walkRun.getSprite(457,137, 113, 156);
		Image [] walkingImages = {walking2, walking3, walking4, walking3};
		
		running1 = walkRun.getSprite(0,724,143,161);
		running2 = walkRun.getSprite(155,717,112,158);
		running3 = walkRun.getSprite(301, 719, 103, 154);
		running4 = walkRun.getSprite(411, 722, 118, 171);
		running5 = walkRun.getSprite(539, 716, 141, 175);
		running6 = walkRun.getSprite(700, 730, 117, 159);
		running7 = walkRun.getSprite(829, 729, 105, 150);
		running8 = walkRun.getSprite(949, 707, 113, 172);
		Image[] runningImages = { running1, running2, running3,
				running4, running5, running6, running7, running8};
		
		dying1 = attackBlock.getSprite(364, 690, 158, 105);
		dying2 = attackBlock.getSprite(539, 670, 165, 140);
		Image[] dyingImages = {dying1, dying2};
		
		//give pictures to super
		loadPics(standing, jumping, blocking, hit, attackingImages, 250, walkingImages, 200, runningImages, 175, dyingImages, 350); 
	}
}

