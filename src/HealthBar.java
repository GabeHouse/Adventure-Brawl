/** Health bar is drawn here
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class HealthBar{
	
	private static Font serif = new Font("Serif", Font.BOLD, 30);
	private int height;	
	private int remainingWidth;
	private int maxWidth;
	private int maxQuantity;
	private int remainingQuantity;
	private String maxQuantityText;
	private String remainingQuantityText;
	private Color color;
	private Color textColor;
	private int x;
	private int y;
	
	public void setRemainingQuantity(int remainingQuantity) {
		//health bar changes based on the percent of health the fighter has left, sets text to display health as well
		this.remainingQuantity = remainingQuantity;
		this.remainingQuantityText = String.valueOf(remainingQuantity);
		this.remainingWidth = (int)((double)remainingQuantity/(double)maxQuantity * (double)maxWidth);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public HealthBar(int x, int y, int maxWidth, int height,
			 int maxQuantity) {
		this.x = x;
		this.y = y;
		this.remainingWidth = maxWidth;
		this.maxWidth = maxWidth;
		this.height = height;
		this.remainingQuantity = maxQuantity;
		this.maxQuantity = maxQuantity;
		this.remainingQuantityText = String.valueOf(maxQuantity);
		this.maxQuantityText = this.remainingQuantityText;
	}
	
	public void draw (Graphics2D graphics) {
		//changes color of the health bar based on its amount left 
		if (remainingQuantity > maxQuantity / 2) {
			color = Color.green;
		} else if (remainingQuantity > maxQuantity / 4){
			color = Color.yellow;
		} else {
			color = Color.red;
		}
		
		graphics.setColor(color);
		Rectangle2D remainingHealthBar = new Rectangle2D.Double(x,y,remainingWidth,height);
		graphics.fill(remainingHealthBar);
	}
	
}