/** Handles key events and mouse events, key listener, mouse listener and mouse motion listener rolled into one
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class GameListener implements KeyListener, MouseListener, MouseMotionListener {
	private boolean keyIsPressed = false;
	private KeyEvent key = null;
	private boolean leftArrow = false;
	private boolean rightArrow = false;
	private boolean upArrow = false;
	private boolean downArrow = false;
	private boolean xKey = false;
	private boolean zKey = false;
	private boolean wKey = false;
	private boolean aKey = false;
	private boolean sKey = false;
	private boolean dKey = false;
	private boolean slashKey = false;
	private boolean periodKey = false;
	private Point mouseClick;
	private Point cursor;

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftArrow = true;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightArrow = true;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			downArrow = true;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			upArrow = true;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			zKey = true;
		if(e.getKeyCode() == KeyEvent.VK_X) 
			xKey = true;
		if(e.getKeyCode() == KeyEvent.VK_W) 
			wKey = true;
		if(e.getKeyCode() == KeyEvent.VK_A) 
			aKey = true;
		if(e.getKeyCode() == KeyEvent.VK_S) 
			sKey = true;
		if(e.getKeyCode() == KeyEvent.VK_D) 
			dKey = true;
		if(e.getKeyCode() == KeyEvent.VK_SLASH) 
			slashKey = true;
		if(e.getKeyCode() == KeyEvent.VK_PERIOD) 
			periodKey = true;
	}


	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftArrow = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightArrow = false;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			downArrow = false;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			upArrow = false;
		if (e.getKeyCode() == KeyEvent.VK_X)
			xKey = false;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			zKey = false;
		if(e.getKeyCode() == KeyEvent.VK_W) 
			wKey = false;
		if(e.getKeyCode() == KeyEvent.VK_A) 
			aKey = false;
		if(e.getKeyCode() == KeyEvent.VK_S) 
			sKey = false;
		if(e.getKeyCode() == KeyEvent.VK_D) 
			dKey = false;
		if(e.getKeyCode() == KeyEvent.VK_SLASH) 
			slashKey = false;
		if(e.getKeyCode() == KeyEvent.VK_PERIOD) 
			periodKey = false;
		key = e;
	}

	
	public void keyTyped(KeyEvent e) {

		

	}
	public KeyEvent getKey() {
		return key;
	}


	public Point getMouseClick() {
		return mouseClick;
	}
	public void setMouseClick(Point click) {
		mouseClick = click;
	}

	
	public boolean isKeyIsPressed() {
		return keyIsPressed;
	}


	public void setKeyIsPressed(boolean keyIsPressed) {
		this.keyIsPressed = keyIsPressed;
	}


	public boolean isLeftArrow() {
		return leftArrow;
	}


	public void setLeftArrow(boolean leftArrow) {
		this.leftArrow = leftArrow;
	}


	public boolean isRightArrow() {
		return rightArrow;
	}


	public void setRightArrow(boolean rightArrow) {
		this.rightArrow = rightArrow;
	}


	public boolean isUpArrow() {
		return upArrow;
	}


	public void setUpArrow(boolean upArrow) {
		this.upArrow = upArrow;
	}


	public boolean isDownArrow() {
		return downArrow;
	}


	public void setDownArrow(boolean downArrow) {
		this.downArrow = downArrow;
	}


	public boolean isXKey() {

		return xKey;

	}


	public void setXKey(boolean punch) {
		this.xKey = punch;
	}


	public boolean isZKey() {
		return zKey;
	}


	public void setZKey(boolean block) {
		this.zKey = block;
	}
	


	public void setKey(KeyEvent key) {
		this.key = key;
	}



	public boolean isWKey() {
		return wKey;
	}


	public void setWKey(boolean wKey) {
		this.wKey = wKey;
	}


	public boolean isAKey() {
		return aKey;
	}


	public void setAKey(boolean aKey) {
		this.aKey = aKey;
	}


	public boolean isSKey() {
		return sKey;
	}


	public void setSKey(boolean sKey) {
		this.sKey = sKey;
	}


	public boolean isDKey() {
		return dKey;
	}


	public void setDKey(boolean dKey) {
		this.dKey = dKey;
	}


	public boolean isSlashKey() {
		return slashKey;
	}


	public void setSlashKey(boolean slashKey) {
		this.slashKey = slashKey;
	}


	public boolean isPeriodKey() {
		return periodKey;
	}


	public void setPeriodKey(boolean periodKey) {
		this.periodKey = periodKey;
	}

	public Point getCursor() {
		return cursor;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		cursor = e.getPoint();

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClick = e.getPoint();
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
