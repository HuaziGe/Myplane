package Myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject
{
	
	private static BufferedImage Sky;
	static
	{
			Sky=loadImage("background.jpg");
	}
	private int speed;
	private int y1;//第二张图片的坐标
	
	Sky()
	{
		super(World.WIDTH,World.HEIGHT,0,0);
		speed=1;
		y1=-height;
	}
		
	public void step()
	{	
		y+=speed;
		y1+=speed;
		if(y>=World.HEIGHT)
		{
			y=-World.HEIGHT;
		}
		if(y1>=World.HEIGHT)
		{
			y1=-World.HEIGHT;
		}
	}

	public BufferedImage getImage() 
	{ 
			return Sky;
	}
	
	
	
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
		g.drawImage(getImage(), x, y1, null);
	}

	
	public boolean outOfBounds() {
		return false;
	}
	
		
}





