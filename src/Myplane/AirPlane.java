package Myplane;

import java.awt.image.BufferedImage;

public class AirPlane extends FlyingObject implements Enemy
{
	private static BufferedImage[] AirPlanes;
	static
	{
		AirPlanes=new BufferedImage[6];
		AirPlanes[0]=loadImage("airplane0.png");
		AirPlanes[1]=loadImage("airplane1.png");
		for(int i=2;i<AirPlanes.length;i++)
		{
			AirPlanes[i]=loadImage("bom"+(i-1)+".png");
		}
	}
	
	private int speed;

	public AirPlane()
	{
		super(48,50);
		speed=2;
		
	}
	
	
	int lifeIndex=0;
	int deadIndex=2;
	public BufferedImage getImage() 
	{
		if(isLife())
		{
			return AirPlanes[lifeIndex++%2];
		}
		else if(isDead())
		{
			BufferedImage img=AirPlanes[deadIndex++];
			if(deadIndex==AirPlanes.length)
			{
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	
	public boolean outOfBounds()
	{
		return this.y>=World.HEIGHT;
	}
	
	
	
	public void step()
	{
		y+=speed;
	}


	public int getScore() 
	{
		return 2;
	}
	
}






