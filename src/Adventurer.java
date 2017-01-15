/** draws adventurer images, adventurer stats are recorded and health is altered
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Adventurer {
	private String name;

	private Adventurer opponent;

	private Animation attacking = new Animation();
	private Animation walking = new Animation();
	private Animation running = new Animation();
	private Animation dying = new Animation();

	private Rectangle advBox;

	private int heightDifference;
	private double x;
	private double y;
	private double velX = 0;
	private double velY = 0;
	private double jumpVel;
	private double width;
	private int range;

	private boolean dead = false;
	private boolean won = false;
	private boolean jumping = false;
	private boolean punching = false;
	private boolean blocking = false;
	private boolean forward = false;
	private boolean backward = false;
	private boolean recharging = false;
	private boolean leftSide = false;
	private boolean hit = false;

	private int maxHealth;
	private int health;
	private int damage;

	private int punchTime;
	private int punchRechargeTime;

	long startingTime = System.currentTimeMillis();
	long totalTime = startingTime;

	private Image jumpImage;
	private Image blockImage;
	private Image standImage;
	private Image hitImage;

	private int flippedX;

	public Adventurer(String name, double x, double y, int punchTime,
			int punchRechargeTime, double jumpVel, int maxHealth, int damage) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.punchTime = punchTime;
		this.punchRechargeTime = punchRechargeTime;
		this.jumpVel = jumpVel;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.damage = damage;

	}

	public void loadPics(Image standImage, Image jumpImage, Image blockImage,
			Image hitImage, Image[] attackingImages, int attackMS,
			Image[] walkingImages, int walkMS, Image[] runningImages,
			int runMS, Image[] dyingImages, int deathMS) {
		for (Image w : walkingImages) {
			walking.addScene(w, walkMS);
		}
		for (Image r : runningImages) {
			running.addScene(r, runMS);
		}

		for (Image a : attackingImages) {
			attacking.addScene(a, attackMS);
		}
		for (Image d : dyingImages) {
			dying.addScene(d, deathMS);
		}
		this.standImage = standImage;
		this.jumpImage = jumpImage;
		this.blockImage = blockImage;
		this.hitImage = hitImage;
		this.width = standImage.getWidth(null);
		this.range = (int) (attackingImages[1].getWidth(null) - width - 30);
		this.heightDifference = attackingImages[1].getHeight(null)
				- standImage.getHeight(null);

	}

	public void draw(Graphics2D graphics) {
		//draws adventurer depending on position relative to opponent
		if (isLeft()) {
			//draws adventurer depending on circumstances or actions facing the right

			if (velX == 0 && velY == 0) {
				//if the fighter is still, set running and walking animations to start from the beginning
				running.start();
				walking.start();
			}
			if (punching && !hit) {
				//animate attack
				animationLoop(attacking, false);
				graphics.drawImage(attacking.getImage(), (int) x, (int) y
						- heightDifference, null);
				setAdvBox((int) x, (int) y,
						attacking.getImage().getWidth(null), attacking
								.getImage().getHeight(null), graphics);

			}
			if (dead) {
				//animate death
				animationLoop(dying, false);
				graphics.drawImage(dying.getImage(), (int) x, (int) y, null);
			} else if (hit) {
				//animate reaction to being hit
				graphics.drawImage(hitImage, (int) x, (int) y, null);
				setAdvBox((int) x, (int) y, hitImage.getWidth(null),
						hitImage.getHeight(null), graphics);
			} else if (blocking) {
				//animate block
				graphics.drawImage(blockImage, (int) x, (int) y
						- heightDifference, null);
				setAdvBox((int) x, (int) y, blockImage.getWidth(null),
						blockImage.getHeight(null) - 110, graphics);

			} else if (jumping && !punching) {
				//animate jump
				graphics.drawImage(jumpImage, (int) x, (int) y, null);
				setAdvBox((int) x, (int) y, jumpImage.getWidth(null),
						jumpImage.getHeight(null), graphics);
			} else if (backward) {
				//animate walking
				animationLoop(walking, true);
				graphics.drawImage(walking.getImage(), (int) x, (int) y, null);
				setAdvBox((int) x, (int) y, walking.getImage().getWidth(null),
						walking.getImage().getHeight(null), graphics);
			} else if (forward) {
				//animate running
				animationLoop(running, true);
				graphics.drawImage(running.getImage(), (int) x, (int) y, null);
				setAdvBox((int) x, (int) y, running.getImage().getWidth(null),
						running.getImage().getHeight(null), graphics);
			} else if (!blocking && !punching) {
				//animate standing still
				graphics.drawImage(standImage, (int) x, (int) y, null);

				setAdvBox((int) x, (int) y, standImage.getWidth(null),
						standImage.getHeight(null), graphics);
			}

		} else {
			//draws adventurer depending on circumstances or actions facing the left
			if (velX == 0 && velY == 0) {
				//if the fighter is still, set running and walking animations to start from the beginning
				running.start();
				walking.start();
			}

			if (punching && !hit) {
				//animate attack
				animationLoop(attacking, false);
				flipDraw(attacking.getImage(), graphics, (int) y
						- heightDifference);
				setAdvBox((int) x, (int) y - heightDifference, attacking
						.getImage().getWidth(null), attacking.getImage()
						.getHeight(null), graphics);
			}
			if (dead) {
				//animate death
				animationLoop(dying, false);
				flipDraw(dying.getImage(), graphics, y);
			}
			else if (hit) {
				//animate reaction to being hit
				flipDraw(hitImage, graphics, (int) y);
				setAdvBox((int) x, (int) y, hitImage.getWidth(null),
						hitImage.getHeight(null), graphics);
			} else if (blocking) {
				//animate block
				flipDraw(blockImage, graphics, (int) y - heightDifference);
				setAdvBox((int) x, (int) y, blockImage.getWidth(null),
						blockImage.getHeight(null) - heightDifference, graphics);
			} else if (jumping && !punching) {
				//animate jump
				flipDraw(jumpImage, graphics, (int) y);
				setAdvBox((int) x, (int) y, jumpImage.getWidth(null),
						jumpImage.getHeight(null), graphics);
			} else if (backward) {
				//animate walk
				animationLoop(walking, true);
				flipDraw(walking.getImage(), graphics, (int) y);
				setAdvBox((int) x, (int) y, walking.getImage().getWidth(null),
						walking.getImage().getHeight(null), graphics);
			} else if (forward) {
				//animate run
				animationLoop(running, true);
				flipDraw(running.getImage(), graphics, (int) y);
				setAdvBox((int) x, (int) y, running.getImage().getWidth(null),
						running.getImage().getHeight(null), graphics);
			} else if (!blocking && !punching) {
				//animate standing still
				flipDraw(standImage, graphics, (int) y);
				setAdvBox((int) x, (int) y, standImage.getWidth(null),
						standImage.getHeight(null), graphics);
			}
		}
	}

	public void jump() {
		//begin jump
		if (!jumping && !blocking) {
			velY = -jumpVel;
			jumping = true;
		}
	}

	public void punch() {
		//begin punch
		if (!punching && !recharging && !hit) {

			attacking.start();
			punching = true;
		}
	}

	public void animationLoop(Animation a, boolean repeating) {
		//calculate time passed to decide when the animation should change scenes
		long timePassed = System.currentTimeMillis() - totalTime;
		totalTime += timePassed;
		a.update(timePassed, repeating);
	}

	public void flipDraw(Image img, Graphics g, double chary) {
		//draw flipped version of the image
		flippedX = (int) (x + width);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		g.drawImage(img, flippedX, (int) chary, -w, h, null);

	}

	public void setTimelessActions(boolean isForward, boolean isBackward, boolean isBlocking) {
		this.forward = isForward;
		this.backward = isBackward;
		this.blocking = isBlocking;
	}

	public boolean isLeft() {
		return leftSide;
	}

	public void setLeft(boolean leftSide) {
		this.leftSide = leftSide;
	}

	public boolean isPunching() {
		return punching;
	}

	public boolean isRecharging() {
		return recharging;
	}

	public void setRecharging(boolean recharging) {
		this.recharging = recharging;
	}

	public void setPunching(boolean isPunching) {
		this.punching = isPunching;
	}

	public int getPunchTime() {
		return punchTime;
	}

	public void setPunchTime(int punchTime) {
		this.punchTime = punchTime;
	}

	public int getPunchRechargeTime() {
		return punchRechargeTime;
	}

	public void setPunchRechargeTime(int punchRechargeTime) {
		this.punchRechargeTime = punchRechargeTime;
	}

	public boolean isBlocking() {
		return blocking;
	}

	public boolean isForward() {
		return forward;
	}

	public boolean isBackward() {
		return backward;
	}

	public double getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double d) {
		this.velY = d;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean getIsJumping() {
		return jumping;
	}

	public void setJumping(boolean isJumping) {
		this.jumping = isJumping;
	}

	public Rectangle getAdvBox() {
		return advBox;
	}

	public void setAdvBox(int x, int y, int w, int h, Graphics g) {
		//create box used to determine contact
		if (!isLeft()) {
			x = flippedX - w;
		}
		this.advBox = new Rectangle(x, y, w, h);
	}

	public double getWidth() {
		return width;
	}

	public int getRange() {
		return range;
	}

	public int getFlippedX() {
		return flippedX;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public Adventurer getOpponent() {
		return opponent;
	}

	public void setOpponent(Adventurer opponent) {
		this.opponent = opponent;
	}

	public void setDead() {
		dead = true;
	}

	public void setWon() {
		won = true;
	}

	public String getName() {
		return name;
	}

	public int getDamage() {
		return damage;
	}

	public String toString() {
		String str = "[forward = " + forward + ", backward = " + backward
				+ ", attacking = " + punching + ", blocking = " + blocking
				+ ", VelX = " + velX + "]";
		return str;
	}

}
