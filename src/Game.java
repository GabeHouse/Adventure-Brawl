/** Main class, updates and draws menu, help screen, select screen, count down screen, adventurers and end game
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	// screen dimensions and variables
	static final int WIDTH = 1024;
	static final int HEIGHT = WIDTH / 4 * 3; // 4:3 aspect ratio
	private JFrame frame;

	private boolean singlePlayerMode = true;
	private int stage = 0;

	// Game States
	private final int GAME_MENU = 0;
	private final int GAME_HELP = 1;
	private final int GAME_SELECT = 2;
	private final int GAME_COUNTDOWN = 3;
	private final int GAME_PLAY = 4;
	private final int GAME_OVER = 5;

	private int gameState = 0;

	// Health bars
	// HealthBar jakeHB = new HealthBar
	private HealthBar player1HB;
	private HealthBar player2HB;
	private HealthBar aIHB;

	// Timers
	long startTime;
	long secondTimer;
	long now;
	long aITimer;
	long p1TimerBegin;
	long p2TimerBegin;
	long aITimerBegin;
	long aIRWTimer = 0;
	long aIABTimer = 0;
	long jumpTimer;

	// AI RNG variables
	private int runWalkRNG;
	private int attackBlockRNG;
	private int jumpRNG;

	// game updates per second
	static final int UPS = 60;

	// variables for the thread
	private Thread thread;
	private boolean running;

	// used for drawing items to the screen
	private Graphics2D graphics;

	// start positions
	private int startPosX = 300;
	private int startPosY = 375;

	// adventurers
	private Adventurer player1;
	private Adventurer player2;
	private Adventurer aI;
	private Adventurer winner;

	// help variables
	private Image helpScreen;
	private static final Button HELP_BACK = new Button(Color.white, 600, 600,
			300, 100, "Back", Color.black, Color.LIGHT_GRAY);

	// count down variables
	private boolean countDownPlayed;
	private int countDownTimer;
	private long countDownTimerBegin;
	private static final Font MARCY_TEXT = new Font("Graphite STD", Font.PLAIN,
			40);
	private static final Font JOKER = new Font("jokerman", Font.PLAIN, 100);
	private SpriteSheet controls = new SpriteSheet();

	private Image p1MControls;
	private Image sControls;
	private Image p2MControls;

	// Select Variables
	private boolean marcyUnlocked = false;
	private Button finnSelect;
	private Button lspSelect;
	private Button jakeSelect;
	private static final Button SELECT_BACK = new Button(Color.white, 800, 50,
			200, 100, "Back", Color.black, Color.LIGHT_GRAY);

	private Button marcelineSelect;
	private boolean finnPicked;
	private boolean lspPicked;
	private boolean jakePicked;
	private boolean marcelinePicked;
	private boolean d, a, y, s, l, i, t, e, m, o, n, r;

	private boolean firstPlayerSelecting = true;
	private Font serif = new Font("Serif", Font.PLAIN, 50);

	private SpriteSheet thumbnails = new SpriteSheet();
	private SpriteSheet graphs = new SpriteSheet();
	private BufferedImage selectBackground;

	private Image finnThumbnail;
	private Image jakeThumbnail;
	private Image lspThumbnail;
	private Image marcelineThumbnail;

	private Image finnGraph;
	private Image jakeGraph;
	private Image lspGraph;
	private Image marcelineGraph;

	// Menu Variables
	private static final Button MULTIPLAYER = new Button(Color.white, 350, 200,
			300, 100, "Multiplayer", Color.black, Color.LIGHT_GRAY);
	private static final Button SINGLEPLAYER = new Button(Color.white, 350,
			350, 300, 100, "Single Player", Color.black, Color.LIGHT_GRAY);
	private static final Button HELP = new Button(Color.white, 350, 500, 300,
			100, "Help", Color.black, Color.LIGHT_GRAY);
	private BufferedImage menuBackground = null;

	// endgame variables
	private Button draw;
	private Button victory;
	private Button defeat;
	private static final Button NEXT = new Button(Color.white, 600, 600, 300,
			100, "Next", Color.black, Color.LIGHT_GRAY);

	BufferedImage gameBackground = null;
	// initialize game objects, load media(pics, music, etc)
	private final static GameListener gl = new GameListener();

	public void init() {
		controls.loadSpriteSheet("Controls.png");
		p1MControls = controls.getSprite(18, 13, 147, 163);
		sControls = controls.getSprite(84, 223, 287, 117);
		p2MControls = controls.getSprite(207, 372, 285, 110);

		thumbnails.loadSpriteSheet("Thumbnails.png");
		finnThumbnail = thumbnails.getSprite(436, 172, 86, 71);
		jakeThumbnail = thumbnails.getSprite(322, 172, 85, 71);
		lspThumbnail = thumbnails.getSprite(188, 165, 91, 80);
		marcelineThumbnail = thumbnails.getSprite(559, 172, 82, 78);

		graphs.loadSpriteSheet("StatBarGraphs.png");
		finnGraph = graphs.getSprite(888, 89, 359, 221);
		jakeGraph = graphs.getSprite(18, 82, 358, 213);
		lspGraph = graphs.getSprite(457, 82, 356, 220);
		marcelineGraph = graphs.getSprite(1262, 86, 358, 221);

		finnSelect = new Button(finnThumbnail, 100, HEIGHT / 2, 200, 150);
		lspSelect = new Button(lspThumbnail, 700, HEIGHT / 2, 200, 150);
		jakeSelect = new Button(jakeThumbnail, 400, HEIGHT / 2, 200, 150);
		marcelineSelect = new Button(marcelineThumbnail, 300, 150, 200, 150);

		try {
			helpScreen = ImageIO.read(new File("InstructionsControls.png"));
		} catch (IOException e) {
			System.out.println("No Image");
		}

		try {
			selectBackground = ImageIO.read(new File("SelectBackground.png"));
		} catch (IOException e) {
			System.out.println("No Image");
		}

		try {
			menuBackground = ImageIO.read(new File("TitleScreen.png"));
		} catch (IOException e) {
			System.out.println("No Image");
		}
		try {
			gameBackground = ImageIO.read(new File("Stage.png"));
		} catch (IOException e) {
			System.out.println("No Image");
		}

	}

	public void update() {
		if (gameState == GAME_MENU) {
			updateMenu();
		} else if (gameState == GAME_HELP) {
			updateHelp();
		} else if (gameState == GAME_SELECT) {
			updateSelect();
		} else if (gameState == GAME_COUNTDOWN) {
			updateCountDown();
		} else if (gameState == GAME_PLAY) {
			updatePlay();
		} else if (gameState == GAME_OVER) {
			updateEndGame();
		}
	}
	
	public void updateMenu() {
		if (gl.getMouseClick() != null) {
			if (MULTIPLAYER.contains(gl.getMouseClick())) {
				// go to select screen, mode is now multiplayer
				singlePlayerMode = false;
				gameState = GAME_SELECT;
			} else if (SINGLEPLAYER.contains(gl.getMouseClick())) {
				// go to select screen, mode is now singleplayer
				singlePlayerMode = true;
				gameState = GAME_SELECT;
			} else if (HELP.contains(gl.getMouseClick())) {
				// go to help screen
				gameState = GAME_HELP;
			}
			reset();
		}
	}
	
	private void updateHelp() {
		if (gl.getMouseClick() != null) {
			if (HELP_BACK.contains(gl.getMouseClick())) {
				gameState = GAME_MENU;
			}
		}
	}
	public void updateSelect() {
		KeyEvent key = gl.getKey();

		if (key != null) {
			// check if any of the keys to unlock marcy have been pressed
			int keyCode = key.getKeyCode();
			if (keyCode == KeyEvent.VK_D) {
				d = true;
			} else if (keyCode == KeyEvent.VK_A) {
				a = true;
			} else if (keyCode == KeyEvent.VK_Y) {
				y = true;
			} else if (keyCode == KeyEvent.VK_S) {
				s = true;
			} else if (keyCode == KeyEvent.VK_L) {
				l = true;
			} else if (keyCode == KeyEvent.VK_I) {
				i = true;
			} else if (keyCode == KeyEvent.VK_T) {
				t = true;
			} else if (keyCode == KeyEvent.VK_E) {
				e = true;
			} else if (keyCode == KeyEvent.VK_M) {
				m = true;
			} else if (keyCode == KeyEvent.VK_O) {
				o = true;
			} else if (keyCode == KeyEvent.VK_N) {
				n = true;
			} else if (keyCode == KeyEvent.VK_R) {
				r = true;
			}
		}
		if (d && a && y && s && l && i && t && e && m && o && n && r
				&& !singlePlayerMode) {
			// if all these buttons are pressed, marcy becomes unlocked
			marcyUnlocked = true;
			gl.setMouseClick(null);
			d = false;
		}

		if (gl.getMouseClick() != null) {
			if (SELECT_BACK.contains(gl.getMouseClick())) {
				gameState = GAME_MENU;
			}
			if (singlePlayerMode) {
				int selectRNG = new Random().nextInt(2);
				if (finnSelect.contains(gl.getMouseClick())) {
					// if player 1 is Finn, theres 50% the ai will be Jake, 50%
					// it will be LSP
					player1 = new Finn(startPosX, startPosY);
					if (selectRNG == 0) {
						aI = new LSP(600, startPosY);
					} else {
						aI = new Jake(600, startPosY);
					}

				} else if (lspSelect.contains(gl.getMouseClick())) {
					// if player 1 is LSP, theres 50% the ai will be Jake, 50%
					// it will be Finn
					player1 = new LSP(startPosX, startPosY);
					if (selectRNG == 0) {
						aI = new Finn(600, startPosY);
					} else {
						aI = new Jake(600, startPosY);
					}
				} else if (jakeSelect.contains(gl.getMouseClick())) {
					// if player 1 is Jake, theres 50% the ai will be LSP, 50%
					// it will be Finn
					player1 = new Jake(startPosX, startPosY);
					if (selectRNG == 0) {
						aI = new LSP(600, startPosY);
					} else {
						aI = new Finn(600, startPosY);
					}
				}
				if (player1 != null) {
					// set health bars
					player1HB = new HealthBar(100, 50, 350, 45,
							player1.getHealth());
					aIHB = new HealthBar(550, 50, 350, 45, aI.getHealth());

					// set position
					player1.setLeft(true);

					// set the adventurers opponents
					player1.setOpponent(aI);
					aI.setOpponent(player1);

					// start count down
					gameState = GAME_COUNTDOWN;
					countDownTimerBegin = System.currentTimeMillis();

				}
			} else {
				// multiplayer
				if (finnSelect.contains(gl.getMouseClick())) {
					if (firstPlayerSelecting) {
						// player1 is finn, finn cannot be picked by player2
						player1 = new Finn(startPosX, startPosY);
						firstPlayerSelecting = false;
						finnPicked = true;
						finnSelect.setSelectable(false);
					} else if (!finnPicked) {
						// player 2 is finn
						player2 = new Finn(600, startPosY);
					}
				} else if (lspSelect.contains(gl.getMouseClick())) {

					if (firstPlayerSelecting) {
						// player1 is lsp, lsp cannot be picked by player2
						player1 = new LSP(startPosX, startPosY);
						firstPlayerSelecting = false;
						lspPicked = true;
						lspSelect.setSelectable(false);
					} else if (!lspPicked) {
						// player 2 is lsp
						player2 = new LSP(600, startPosY);
					}
				} else if (jakeSelect.contains(gl.getMouseClick())) {

					if (firstPlayerSelecting) {
						// player 1 is jake, jake cannot be picked by player 2
						player1 = new Jake(startPosX, startPosY);
						firstPlayerSelecting = false;
						jakePicked = true;
						jakeSelect.setSelectable(false);
					} else if (!jakePicked) {
						// player2 is jake
						player2 = new Jake(600, startPosY);
					}
				} else if (marcelineSelect.contains(gl.getMouseClick())
						&& marcyUnlocked) {
					if (firstPlayerSelecting) {
						// player 1 is marcy, marcy cannot be picked by player 2
						player1 = new Marceline(startPosX, startPosY);
						firstPlayerSelecting = false;
						marcelinePicked = true;
						marcelineSelect.setSelectable(false);
					} else if (!marcelinePicked) {
						// player 2 is marcy
						player2 = new Marceline(600, startPosY);
					}
				}
				if (player2 != null) {
					// set position
					player1.setLeft(true);

					// set adventurer's opponents
					player2.setOpponent(player1);
					player1.setOpponent(player2);

					// set health bars
					player1HB = new HealthBar(100, 50, 350, 45,
							player1.getHealth());
					player2HB = new HealthBar(550, 50, 350, 45,
							player2.getHealth());
					gl.setMouseClick(null);

					// go to count down
					gameState = GAME_COUNTDOWN;
					countDownTimerBegin = System.currentTimeMillis();
				}
			}
		}
	}

	private void updateCountDown() {
		countDownTimer = (int) ((System.currentTimeMillis() - countDownTimerBegin) / 1000);
		if (countDownTimer >= 4) {
			countDownPlayed = true;
			gameState = GAME_PLAY;

		}

	}

	public void updatePlay() {
		// updates appropriate adventurers based on mode

		if (!singlePlayerMode) {
			updatePlayer(player1, p1TimerBegin, player1HB);
			updatePlayer(player2, p2TimerBegin, player2HB);
			updateInteraction(player1);
			updateInteraction(player2);
		}

		if (singlePlayerMode) {
			updatePlayer(player1, p1TimerBegin, player1HB);
			updateAi();
			updateInteraction(player1);
			updateInteraction(aI);
		}
	}

	
	
	public void updateInteraction(Adventurer player) {
		if (player.getAdvBox() != null
				&& player.getOpponent().getAdvBox() != null) {
			if (player.getAdvBox().intersects(player.getOpponent().getAdvBox())) {
				// adventurers are in contact
				if (player.isPunching()
						&& !player.getOpponent().isHit()
						&& (!player.getOpponent().isBlocking() || player
								.getIsJumping())
						&& !player.getOpponent().isPunching()) {
					// determines if an attack is successful and deals damage if
					// it is
					player.getOpponent().setHealth(
							player.getOpponent().getHealth()
									- player.getDamage());
					player.getOpponent().setHit(true);

					// move the adventurer who got hit back
					if (player.isLeft()) {
						player.getOpponent().setX(
								player.getAdvBox().getX()
										+ player.getAdvBox().getWidth());
					} else {
						player.getOpponent().setX(
								player.getAdvBox().getX()
										- player.getOpponent().getAdvBox()
												.getWidth());
					}
				} else if (player.getOpponent().isPunching()
						&& player.isPunching() && !player.isHit()
						&& !player.getOpponent().isHit()) {
					// if both adventurers attack at the same time
					// if an adventurer is jumping, their attack is not
					// successful
					if (!player.getIsJumping()
							&& player.getOpponent().getIsJumping()) {
						player.getOpponent().setHit(true);
						player.getOpponent().setHealth(
								player.getOpponent().getHealth()
										- player.getDamage());
						player.getOpponent().setPunching(false);
					} else if (player.getIsJumping()
							&& !player.getOpponent().getIsJumping()) {
						player.setHit(true);
						player.setHealth(player.getHealth()
								- player.getOpponent().getDamage());
						player.setPunching(false);

						// if both attacks are successful, both take damage and
						// are moved back
					} else {
						player.setHit(true);
						player.setHealth(player.getHealth()
								- player.getOpponent().getDamage());
						player.getOpponent().setHit(true);
						player.getOpponent().setHealth(
								player.getOpponent().getHealth()
										- player.getDamage());
					}
					if (player.isLeft()) {
						player.getOpponent().setX(
								player.getOpponent().getX() + 50);
						player.setX(player.getX() - 50);
					} else {
						player.getOpponent().setX(
								player.getOpponent().getX() - 50);
						player.setX(player.getX() + 50);
					}
				}
			}
		}

	}

	public void updatePlayer(Adventurer player, long timerBegin, HealthBar hb) {
		boolean run = false;
		boolean walk = false;

		boolean punch, block, up, right, left;

		if (!singlePlayerMode) {
			if (player.equals(player1)) {
				// player 1's controls in multiplayer mode are w a d z x
				punch = gl.isXKey();
				block = gl.isZKey();
				up = gl.isWKey();
				right = gl.isDKey();
				left = gl.isAKey();
			} else {
				// player 2's controls in mulitplayer mode are up, left, right,
				// period, slash
				punch = gl.isSlashKey();
				block = gl.isPeriodKey();
				up = gl.isUpArrow();
				right = gl.isRightArrow();
				left = gl.isLeftArrow();
			}
		} else {
			// controls in singleplayer mode are up, left, right, z, x
			punch = gl.isXKey();
			block = gl.isZKey();
			up = gl.isUpArrow();
			right = gl.isRightArrow();
			left = gl.isLeftArrow();
		}

		int runningVel;
		int walkingVel;

		attack(player, timerBegin);

		// adventures are unable to move off of the screen
		if (player.getX() + 100 >= WIDTH) {
			player.setX(WIDTH - 100);
		} else if (player.getX() <= 0) {
			player.setX(0);
		}

		// determine position relative to opponent
		if (player.getX() < player.getOpponent().getX()) {
			player.getOpponent().setLeft(false);
			player.setLeft(true);
		} else {
			player.getOpponent().setLeft(true);
			player.setLeft(false);
		}

		if (player.isLeft()) {
			// set direction of running and walking velocity
			if (player.getWidth() + player.getX() <= player.getOpponent()
					.getX() || player.getIsJumping()) {
				// adventurers cannot move through each other
				run = right;
			}
			walk = left;
			runningVel = 5;
			walkingVel = -3;
		} else {
			// set direction of running and walking velocity
			if (player.getX() > player.getOpponent().getX()
					+ player.getOpponent().getWidth()
					|| player.getIsJumping()) {
				// adventurers cannot move through each other
				run = left;
			}
			walk = right;
			runningVel = -5;
			walkingVel = 3;
		}
		if (up && !player.isRecharging() && !player.isPunching()) {
			// start jump
			player.jump();
		}
		if (player.getIsJumping()) {
			// apply gravity if the player is jumping
			gravity(player);
		}
		if (punch && !player.isBlocking() && !player.isRecharging()
				&& !player.isPunching()) {
			// start punching
			timerBegin = System.nanoTime();
			player.punch();
		}
		if (!player.isPunching()) {
			// set adventurer velocities and determine actions
			if (block && !player.isRecharging()) {
				// blocking
				if (!player.getIsJumping()) {
					player.setVelX(0);
				}
				// adventurer cannot move while blocking
				player.setTimelessActions(false, false, true);
			}

			else if (run) {
				// adventurer cannot walk or block while running
				player.setTimelessActions(true, false, false);
				player.setVelX(runningVel);

			} else if (walk) {
				// adventurer cannot run or block while walking
				player.setTimelessActions(false, true, false);
				player.setVelX(walkingVel);
			} else {
				player.setTimelessActions(false, false, false);
				player.setVelX(0);
			}
		}
		// set remaining health of the adventurer
		hb.setRemainingQuantity(player.getHealth());

		// set position of the adventurer
		player.setY((int) player.getY() + player.getVelY());
		player.setX((int) player.getX() + player.getVelX());

		// update begin times
		if (player.equals(player1)) {
			p1TimerBegin = timerBegin;
		} else {
			p2TimerBegin = timerBegin;
		}

		if (player.getHealth() <= 0 || player.getOpponent().getHealth() <= 0) {
			// if an adventurer has died, go to endgame
			gameState = GAME_OVER;
			gl.setMouseClick(null);
		}
		
	}
	
	public void updateEndGame() {
		if (!singlePlayerMode) {
			gravity(player1);
			gravity(player2);
		} else {
			gravity(player1);
			gravity(aI);
		}
		//determine winner
		if (player1.getOpponent().getHealth() <= 0) {
			winner = player1;
		} else {
			winner = player1.getOpponent();
		}
		
		draw = new Button(Color.white, 350, 100, 250, 100, "Draw!",
				Color.black, Color.LIGHT_GRAY);
		
		//choose what victory button displays based on winner and mode
		if (!singlePlayerMode) {
			victory = new Button(Color.white, 330, 100, 330, 100,
					winner.getName() + " wins!", Color.black, Color.LIGHT_GRAY);
		} else {
			victory = new Button(Color.white, 350, 100, 250, 100, "Victory!",
					Color.black, Color.LIGHT_GRAY);
			defeat = new Button(Color.white, 350, 100, 250, 100, "Defeat",
					Color.black, Color.LIGHT_GRAY);
		}
		
		//display correct button, kill the adventurer with health <= 0
		if (winner.getHealth() <= 0 && winner.getOpponent().getHealth() <= 0) {
			winner.setDead();
			winner.getOpponent().setDead();
			draw.draw(graphics, gl.getCursor(), false);
		} else {
			winner.setWon();
			winner.setPunching(false);
			winner.setHit(false);
			winner.getOpponent().setDead();
		}
		if (singlePlayerMode && player1.getHealth() > 0 && stage == 0 && gl.getMouseClick() != null) {
			//if in single player mode and on first stage, player goes on to second stage vs marceline
			if (victory.contains(gl.getMouseClick())) {
				//change stage
				stage = 1;
				
				//set new health bars
				aI = new Marceline(600, startPosY);
				aIHB = new HealthBar(550, 50, 350, 45, aI.getHealth());
				
				//give the player full health
				player1.setHealth(player1.getMaxHealth());
				
				//go to count down
				gameState = GAME_COUNTDOWN;
				countDownTimerBegin = System.currentTimeMillis();
				
				//put player1 back at the beginning position/state
				player1.setX(startPosX);
				player1.setY(startPosY);
				player1.setVelY(0);
				player1.setLeft(true);
				
				//set adventurer's opponents
				aI.setOpponent(player1);
				player1.setOpponent(aI);
				
				//set running, walking, blocking and jumping to false
				player1.setTimelessActions(false, false, false);
				player1.setJumping(false);
				gl.setMouseClick(null);
			}
		}

		else {
			if (gl.getMouseClick() != null) {
				if (victory.contains(gl.getMouseClick())) {
					//if a button is pressed after beating marceline, another player or losing to anyone, go to menu
					player1 = null;
					aI = null;
					player2 = null;
					gameState = GAME_MENU;
				}
			}
		}
	}


	public void updateAi() {
		int playerFaceX;
		int aIFaceX;
		int walkingVel = 0;
		int runningVel = 0;
		boolean run = false;
		boolean walk = false;
		Random rn = new Random();

		// AI cannot go off screen
		if (aI.getX() + 100 >= WIDTH) {
			aI.setX(WIDTH - 100);
		} else if (aI.getX() <= 0) {
			aI.setX(0);
		}

		if (System.currentTimeMillis() >= aIABTimer) {
			// 50% chance to block, 50% chance to attack for 800 milliseconds
			attackBlockRNG = rn.nextInt(2);
			aIABTimer = System.currentTimeMillis() + 800;
		}

		if (System.currentTimeMillis() >= aIRWTimer) {
			// 50% chance to move forward, 50% chance to move back for 1000
			// milliseconds2);
			aIRWTimer = System.currentTimeMillis() + 1000;
		}

		if (System.currentTimeMillis() >= jumpTimer) {
			// 50% to jump for 300 milliseconds
			jumpRNG = rn.nextInt(2);
			jumpTimer = System.currentTimeMillis() + 300;
		}

		if (aI.getX() == 0 || aI.getX() >= WIDTH - aI.getWidth()) {
			// if the AI has backed up to the end of the screen, make it move
			// forward
			runWalkRNG = 0;
		}

		attack(aI, aITimerBegin);
		gravity(aI);

		// set direction of velocities
		if (aI.isLeft()) {

				runningVel = 5;
				walkingVel = -3;
			} else {
				runningVel = -5;
				walkingVel = 3;
			
		}

		// locate points on adventurers closest to each other
		if (aI.isLeft()) {
			playerFaceX = (int) player1.getAdvBox().getX();
			aIFaceX = (int) (aI.getAdvBox().getX() + aI.getWidth());
		} else {
			playerFaceX = (int) (player1.getAdvBox().getX() + player1
					.getWidth());
			aIFaceX = (int) aI.getAdvBox().getX();
		}
		if ((Math.abs(playerFaceX - aIFaceX) <= aI.getRange() || aI.getAdvBox()
				.intersects(aI.getOpponent().getAdvBox()))
				&& !aI.isPunching()
				&& !aI.isRecharging() && !aI.isHit()) {
			// if the AI is in range of the player, there is a 50% chance to
			// attack
			if (attackBlockRNG == 0) {
				// if the AI is attacking, there is a 50% to jump as well
				if (jumpRNG == 0) {
					aI.jump();
				}

				aI.punch();
				aITimerBegin = System.nanoTime();
				// AI cannot run, walk or block while attacking
				aI.setTimelessActions(false, false, false);
			} else {
				// if the AI is in range of the player, there is a 50% chance to
				// block
				// AI cannot run or walk while blocking
				aI.setTimelessActions(false, false, true);
			}
		} else if (aI.isPunching() && !aI.isBlocking()) {
			//
			aITimer = System.nanoTime() - aITimerBegin;
			// AI cannot run, walk or block while attacking
			aI.setTimelessActions(false, false, false);
			if (!aI.getIsJumping()) {
				aI.setVelX(0);
			}
		} else if (!(Math.abs(playerFaceX - aIFaceX) <= aI.getRange())) {
			// when out of range, there is a 50% chance for the AI to run
			// forward and a 50% chance for the AI to walk backward
			if (((aI.getX() > aI.getOpponent().getX()
					+ aI.getOpponent().getWidth()
					|| aI.getIsJumping()) && !aI.isLeft()) ||((aI.getWidth() + aI.getX() <= aI.getOpponent()
							.getX() || aI.getIsJumping())&& aI.isLeft())) {
			if (runWalkRNG == 0) {
				
				run = true;
				walk = false;
				// AI cannot block or walk while running
				aI.setTimelessActions(true, false, false);
			} else {
				walk = true;
				run = false;
				// AI cannot block or run while walking
				aI.setTimelessActions(false, true, false);
			}
			}

		}
		//set velocities of AI
		if (run && !aI.isBlocking() && !aI.isPunching()) {
			// AI cannot block or walk while running
			aI.setTimelessActions(true, false, false);
			aI.setVelX(runningVel);

		} else if (walk && !aI.isBlocking() && !aI.isPunching()) {
			// AI cannot block or run while walking
			aI.setTimelessActions(false, true, false);
			aI.setVelX(walkingVel);
		} else {
			aI.setVelX(0);

		}
		//set remaining health
		aIHB.setRemainingQuantity(aI.getHealth());

		//set AI's position
		aI.setY((int) aI.getY() + aI.getVelY());
		aI.setX((int) aI.getX() + aI.getVelX());

	}
	
	public void reset() {
		// set all necessary variables back to original state
		firstPlayerSelecting = true;
		finnPicked = false;
		lspPicked = false;
		jakePicked = false;
		jakeSelect.setSelectable(true);
		finnSelect.setSelectable(true);
		lspSelect.setSelectable(true);
		marcelineSelect.setSelectable(true);
		marcyUnlocked = false;
		d = false;
		a = false;
		y = false;
		s = false;
		l = false;
		i = false;
		t = false;
		e = false;
		m = false;
		o = false;
		n = false;
		r = false;
		gl.setMouseClick(null);
		gl.setKey(null);
		stage = 0;

	}

	public void attack(Adventurer adv, long timerBegin) {
		long timer;
		timer = System.nanoTime() - timerBegin;
		if (adv.isPunching() && !adv.isBlocking()) {
			//adventurer cannot block, run or walk while attacking
			adv.setTimelessActions(false, false, false);
			if (!adv.getIsJumping()) {
				adv.setVelX(0);
			}
			if (timer > adv.getPunchTime()) {
				//adventurer must attack for a certain amount of time, then recharge
				adv.setRecharging(true);
				adv.setPunching(false);
			}
		} else if (adv.isRecharging()) {
			//adventurer must recharge for a certain amount of time
			if (timer > adv.getPunchRechargeTime() + adv.getPunchTime()) {
				adv.setRecharging(false);
				adv.getOpponent().setHit(false);
			}
		}
	}

	public void gravity(Adventurer adv) {
		if (adv.getIsJumping()) {
			//fall
			adv.setVelY(adv.getVelY() + 1.2);
			if (adv.getY() > startPosY) {
				//adventurer cannot fall below the ground
				adv.setY(startPosY);
				adv.setVelY(0);
				adv.setJumping(false);
			}
			if (adv.getHealth() <= 0 || adv.getOpponent().getHealth() <= 0) {
				//even when the game is over, the player must fall
				adv.setY((int) adv.getY() + adv.getVelY());
				adv.setX((int) adv.getX() + adv.getVelX());
			}
		}

	}

	
	// draw things to the screen
	public void draw() {
		if (gameState == GAME_MENU) {
			drawMenu();
		} else if (gameState == GAME_HELP) {
			drawHelp();
		} else if (gameState == GAME_SELECT) {
			drawSelect();
		} else if (gameState == GAME_COUNTDOWN) {
			drawCountDown();
		} else if (gameState == GAME_PLAY) {
			drawPlay();
		} else if (gameState == GAME_OVER) {
			drawEnd();
		}
	}

	private void drawHelp() {
		graphics.drawImage(helpScreen, 0, 0, WIDTH, HEIGHT, null);
		HELP_BACK.draw(graphics, gl.getCursor(), false);
	}

	private void drawCountDown() {
		graphics.drawImage(gameBackground, 0, 0, WIDTH, HEIGHT, null);
		//draw players and controls during count down
		if (!singlePlayerMode) {
			player1.draw(graphics);
			player2.draw(graphics);
			graphics.drawImage(p1MControls, 50, 50, null);
			graphics.drawImage(p2MControls, 700, 50, null);
		} else {
			player1.draw(graphics);
			aI.draw(graphics);
			graphics.drawImage(sControls, 50, 50, null);

		}
		if (stage == 1) {
			//if playing against marceline, display her speech
			graphics.setFont(MARCY_TEXT);
			graphics.setColor(Color.red);
			graphics.drawString("I don't wanna hurt you. But you should ", 370,
					250);
			graphics.drawString("know things get crazy when I'm hungry.", 370,
					310);
		}
		graphics.setColor(Color.black);
		graphics.setFont(JOKER);
		switch (countDownTimer) {
		// display different text at different times in the count down
		case 0:
			graphics.drawString("", 370, 200);
			break;
		case 1:
			graphics.drawString("Ready", 370, 200);
			break;
		case 2:
			graphics.drawString("Set", 400, 200);
			break;

		case 3:
			graphics.drawString("Fight!", 370, 200);
			break;

		}
	}

	public void drawMenu() {
		graphics.drawImage(menuBackground, 0, 0, WIDTH, HEIGHT, null);
		//display buttons
		MULTIPLAYER.draw(graphics, gl.getCursor(), false);
		SINGLEPLAYER.draw(graphics, gl.getCursor(), false);
		HELP.draw(graphics, gl.getCursor(), false);
	}

	public void drawSelect() {

		graphics.drawImage(selectBackground, 0, 0, WIDTH, HEIGHT, null);

		graphics.setColor(Color.white);
		SELECT_BACK.draw(graphics, gl.getCursor(), false);

		// draw thumnails
		finnSelect.draw(graphics, gl.getCursor(), true);
		lspSelect.draw(graphics, gl.getCursor(), true);
		jakeSelect.draw(graphics, gl.getCursor(), true);
		if (marcyUnlocked) {
			// display marcy thumbnail and graph if she has been unlocked
			marcelineSelect.draw(graphics, gl.getCursor(), true);
			graphics.drawImage(marcelineGraph, 520, 160, 200, 150, null);
		}
		// draw graphs
		graphics.drawImage(finnGraph, 100, HEIGHT / 2 + 170, 200, 150, null);
		graphics.drawImage(jakeGraph, 400, HEIGHT / 2 + 170, 200, 150, null);
		graphics.drawImage(lspGraph, 700, HEIGHT / 2 + 170, 200, 150, null);

		graphics.setColor(Color.black);
		graphics.setFont(serif);
		if (!singlePlayerMode) {
			// display which player is selecting in multiplayer mode
			if (firstPlayerSelecting) {
				graphics.drawString("Player 1 is selecting", 300, 80);
			} else {
				graphics.drawString("Player 2 is selecting", 300, 80);
			}
		} else {
			graphics.drawString("Select your fighter", 320, 80);
		}
	}

	public void drawPlay() {
		graphics.drawImage(gameBackground, 0, 0, WIDTH, HEIGHT, null);

		if (singlePlayerMode) {
			aI.draw(graphics);
			player1.draw(graphics);
			
			player1HB.draw(graphics);
			aIHB.draw(graphics);
			
			graphics.setColor(Color.black);
			graphics.setFont(serif);
			
			graphics.drawString(player1.getName(), 100, 50);
			graphics.drawString(aI.getName(), 550, 50);
		} else {
			// adventurers
			player1.draw(graphics);
			player2.draw(graphics);

			// health bars
			player1HB.draw(graphics);
			player2HB.draw(graphics);

			graphics.setColor(Color.black);
			graphics.setFont(serif);

			// names on top of health bar
			graphics.drawString(player1.getName(), 100, 50);
			graphics.drawString(player2.getName(), 550, 50);
		}
	}

	public void drawEnd() {
		graphics.drawImage(gameBackground, 0, 0, WIDTH, HEIGHT, null);
		if (singlePlayerMode) {
			// draw adventurers
			aI.draw(graphics);
			player1.draw(graphics);
		} else {
			// draw adventurers
			player1.draw(graphics);
			player2.draw(graphics);
		}
		if (winner != null) {
			if (!singlePlayerMode) {
				if (winner.getHealth() <= 0
						&& winner.getOpponent().getHealth() <= 0) {
					// if both players have died display draw
					draw.draw(graphics, gl.getCursor(), false);
				} else {
					// display victory button if one player is still alive
					victory.draw(graphics, gl.getCursor(), false);
				}
			} else {
				if (player1.getHealth() <= 0) {
					// if player has died, display defeat
					defeat.draw(graphics, gl.getCursor(), false);
				} else {
					// if player is still alive display victory
					victory.draw(graphics, gl.getCursor(), false);
				}
			}
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.add(game); // game is a component because it extends Canvas
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
		game.addKeyListener(gl);
		game.addMouseListener(gl);
		game.addMouseMotionListener(gl);
	}

	public Game() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		frame = new JFrame();
	}

	// starts a new thread for the game
	public synchronized void start() {
		thread = new Thread(this, "Game");
		running = true;
		thread.start();
	}

	// main game loop
	public void run() {
		init();

		double ns = 1000000000.0 / UPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;

		startTime = System.nanoTime();
		secondTimer = System.nanoTime();
		while (running) {
			now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			while (delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;

			if (System.nanoTime() - secondTimer > 1000000000) {
				this.frame.setTitle(updates + " ups  ||  " + frames + " fps");
				secondTimer += 1000000000;
				frames = 0;
				updates = 0;
			}
		}
		System.exit(0);
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy(); // method from Canvas class

		if (bs == null) {
			createBufferStrategy(3); // creates it only for the first time the
										// loop runs (trip buff)
			return;
		}

		graphics = (Graphics2D) bs.getDrawGraphics();
		draw();
		graphics.dispose();
		bs.show();
	}

	// stops the game thread and quits
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}