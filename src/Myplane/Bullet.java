package Myplane;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject
{
	private static BufferedImage Bullet;
	static
	{
			Bullet=loadImage("bullet.png");
	}
	
	private int speed;	
	Bullet(int x,int y)	{
		super(8,12,x,y);
		speed=3;
	}
	
	
	public BufferedImage getImage() 
	{
		if(isLife())
		{
			return Bullet;
		}
		else if(isDead())
		{
			state=REMOVE;
		}
		return null;
	}
	
	
	public void step()
	{	
		y-=speed;
	}

	
	public boolean outOfBounds()
	{
		return this.y<=-this.height;
	}
	
}