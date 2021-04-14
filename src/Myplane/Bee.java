package Myplane;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award
{
	private int xspeed;
	private int yspeed;
	private int awardType;
	private static BufferedImage[] Bees;
	static
	{
		Bees=new BufferedImage[6];
		Bees[0]=loadImage("bee0.png");
		Bees[1]=loadImage("bee1.png");
		for(int i=2;i<Bees.length;i++)
			
		{
			Bees[i]=loadImage("bom"+(i-1)+".png");
		}
	}
	
	
	Bee()
	{
		super(60,51);
		Random rand=new Random();
		xspeed=1;
		yspeed=2;
		awardType=rand.nextInt(2);//生成0.1.2三个数字
	}
	
	int lifeIndex=0;
	int deadIndex=2;
	public BufferedImage getImage()
	{
		if(isLife())
		{
			return Bees[lifeIndex++%2];
		}else if(isDead())
		{
			BufferedImage img=Bees[deadIndex++];
			if(deadIndex==Bees.length)
			{
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	
	public void step() 
	{
		x+=xspeed;
		y+=yspeed;
		if(x<=0 || x>=World.WIDTH-this.width)
		{
			xspeed*=-1;
		}
	}

	
	
	public boolean outOfBounds()
	{
		return this.y>=World.HEIGHT;
	}

	
	public int getScore() 
	{
		return 1;
	}
	
	
	public int getAwardType() 
	{
		return awardType;
	}
	
	
}