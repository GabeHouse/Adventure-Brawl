/** Jake specific images and stats
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Image;

public class Jake extends Adventurer {
	
	private static final int HEALTH = 160;
	private static final int PUNCH_TIME = 500000000;
	private static final int PUNCH_RECHARGE_TIME = 500000000;
	private static final int DAMAGE = 30;
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
	private static Image walking3;
	
	private static Image running1;
	private static Image running2;
	private static Image running3;
	private static Image running4;
	private static Image running5;
	
	private static Image dying1;
	private static Image dying2;
	
	public Jake(double x, double y) {
		super("Jake", x, y, PUNCH_TIME, PUNCH_RECHARGE_TIME, JUMP_VEL, HEALTH, DAMAGE);
		passPics();
	}
	public void passPics() {
		//init images
		walkRun.loadSpriteSheet("JakeWalkRun.png");
		attackBlock.loadSpriteSheet("JakeAttackBlock.png");
		
		jumping = walkRun.getSprite(537, 327, 152, 115);

		hit = attackBlock.getSprite(90, 282, 132, 149);
		
		blocking = attackBlock.getSprite(64, 80, 134, 200);

		attacking1 = attackBlock.getSprite(233, 80, 137, 200);
		attacking2 = attackBlock.getSprite(401, 80, 213, 200);
		Image [] attackingImages = {attacking1, attacking2};

		standing = walkRun.getSprite(162, 334, 91, 152);

		walking1 = walkRun.getSprite(260, 334, 105, 152);	
		walking2 = walkRun.getSprite(166, 334, 90, 152);
		walking3 = walkRun.getSprite(51, 334, 100, 152);

		Image [] walkingImages = {walking1, walking2, walking3, walking2};
		
		running1 = walkRun.getSprite(819, 327, 110, 155);
		running2 = walkRun.getSprite(394, 327, 141, 155);
		running3 = walkRun.getSprite(537, 327, 152, 155);
		running4 = walkRun.getSprite(693, 327, 122, 155);

		Image[] runningImages = { running1, running2, running3, running4 };
		
		dying1 = attackBlock.getSprite(271, 286, 131, 159);
		dying2 = attackBlock.getSprite(448, 286, 168, 159);
		Image[] dyingImages = {dying1, dying2};
		//give images to super
		loadPics(standing, jumping, blocking, hit, attackingImages, 250, walkingImages, 250, runningImages, 120, dyingImages, 350);
	}
	
	

}
