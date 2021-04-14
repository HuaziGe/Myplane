package Myplane;

import java.awt.image.BufferedImage;

public class BigAirPlane extends FlyingObject implements Enemy
{
	private static BufferedImage BigAirPlane[];
	static
	{
		BigAirPlane=new BufferedImage[6];
		BigAirPlane[0]=loadImage("bigairplane0.png");
		BigAirPlane[1]=loadImage("bigairplane1.png");
		for(int i=2;i<BigAirPlane.length;i++)
		{
			BigAirPlane[i]=loadImage("bom"+(i-1)+".png");
		}
	}
	
	private int speed;
	BigAirPlane()
	{
		super(66,89);
		speed=2;
	}
	
	
	int lifeIndex=0;
	int deadIndex=2;
	public BufferedImage getImage() 
	{
		if(isLife())
		{
			return BigAirPlane[lifeIndex++%2];
		}
		else if(isDead())
		{
			BufferedImage img=BigAirPlane[deadIndex++];
			if(deadIndex==BigAirPlane.length)
			{
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public void step()
	{	
		y+=speed;	
	}
	
	public boolean outOfBounds()
	{
		return this.y>=World.HEIGHT;
	}

	
	public int getScore() 
	{
		return 5;
	}
	
	
	
	
}






