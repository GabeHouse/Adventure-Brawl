/** Creates either text buttons or image buttons that can respond to clicks/cursor
 * 	Adventure Time
 * ICS 3UP 2013,-104
 * @Gabe House
 * @version 2014/06/08
 */ 

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Button {
	private int x;
	private int y;
	private int width;
	private int height;
	private String text;
	private Color textColor, darkened, buttonColor;
	private static Font serif = new Font("Serif", Font.PLAIN, 50);
	private boolean selectable = true;
	private Image img;
	private boolean pic;
	private Rectangle2D bgRec;
	private Rectangle2D rec;

	
	public void setColor(Color color) {
		this.buttonColor = color;
	}

	public Button(Color buttonColor, int x, int y, int width, int height, String text, Color textColor, Color darkened) {
		//button with text
		this.buttonColor = buttonColor;
		this.text = text;
		this.textColor = textColor;
		this.x =x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.darkened = darkened;
		pic = false;
		bgRec = new Rectangle2D.Double(x-5, y - 5, width + 10, height + 10);
		rec = new Rectangle2D.Double(x,y,width,height);
	}
	public Button (Image img, int x, int y, int width, int height) {
		//button with image
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		pic = true;
		bgRec = new Rectangle2D.Double(x-1, y - 1, width + 2, height + 2);
	}
	
	public void draw (Graphics2D graphics, Point mousePosition, boolean pic) {
		if (!pic) {
		graphics.setColor(Color.black);
		graphics.fill(bgRec);
		graphics.setColor(buttonColor);
		if (mousePosition != null) {
			if (!selectable) {
				graphics.setColor(Color.gray);
			}
			else if (contains(mousePosition)) {
				graphics.setColor(darkened);
			}
			}
			graphics.fill(rec);
			graphics.setFont(serif);
			graphics.setColor(textColor);
			graphics.drawString(text, x + width/2 - text.length()*11, y + 60);
		} else {
			//to be used for border
			graphics.setColor(Color.black);
			graphics.setStroke(new BasicStroke(5f));

			if (contains(mousePosition) && selectable) {
				//if cursor is inside button, change the color of the border to white
				graphics.setColor(Color.white);
			}
		}
		graphics.draw(bgRec);
		graphics.drawImage(img, x, y, width, height, null);
		if (!selectable) {
			//draw an X over the button if it cannot be selected
			graphics.drawLine(x, y, x + width, y + height);
			graphics.drawLine(x + width, y, x, y + height);
		}

	}
	public boolean contains (Point mousePosition) {
		//checks if the cursor is inside an object
		int mouseY = (int) mousePosition.getY();
		int mouseX = (int) mousePosition.getX();
		boolean contained = false;
		if (mouseY < y + height && mouseY > y && mouseX < x + width && mouseX > x) 
			{
				contained = true;
			}
		return contained;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	
}