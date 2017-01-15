/** Marceline specific images and stats
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Image;

public class Marceline extends Adventurer {

	private static final int HEALTH = 260;
	private static final int PUNCH_TIME = 500000000;
	private static final int PUNCH_RECHARGE_TIME = 500000000;
	private static final int DAMAGE = 40;
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

	private static Image dying1;
	private static Image dying2;
	private static Image dying3;
	private static Image dying4;
	private static Image dying5;
	private static Image dying6;

	public Marceline(double x, double y) {
		super("Marceline", x, y - 15, PUNCH_TIME, PUNCH_RECHARGE_TIME,
				JUMP_VEL, HEALTH, DAMAGE);
		passPics();
	}

	public void passPics() {
		// init images
		walkRun.loadSpriteSheet("MarcelineWalk.png");
		attackBlock.loadSpriteSheet("MarcelineAttackBlock.png");

		jumping = walkRun.getSprite(486, 247, 82, 164);

		hit = attackBlock.getSprite(276, 477, 87, 166);

		blocking = attackBlock.getSprite(263, 186, 162, 178);

		attacking1 = attackBlock.getSprite(381, 472, 136, 178);
		attacking2 = attackBlock.getSprite(536, 472, 176, 178);
		Image[] attackingImages = { attacking1, attacking2 };

		standing = walkRun.getSprite(297, 248, 66, 163);

		walking1 = walkRun.getSprite(204, 248, 78, 163);
		walking2 = walkRun.getSprite(297, 248, 66, 163);
		walking3 = walkRun.getSprite(368, 248, 79, 163);

		Image[] walkingImages = { walking1, walking2, walking3, walking2 };

		dying1 = attackBlock.getSprite(36, 42, 111, 97);
		dying2 = attackBlock.getSprite(42, 156, 108, 100);
		dying3 = attackBlock.getSprite(36, 269, 109, 99);
		dying4 = attackBlock.getSprite(38, 380, 114, 115);
		dying5 = attackBlock.getSprite(38, 499, 106, 106);
		dying6 = attackBlock.getSprite(0, 0, 1, 1);
		Image[] dyingImages = { dying1, dying2, dying3, dying4, dying5, dying6 };
		// give images to super
		loadPics(standing, jumping, blocking, hit, attackingImages, 250,
				walkingImages, 250, walkingImages, 250, dyingImages, 100);
	}

}
