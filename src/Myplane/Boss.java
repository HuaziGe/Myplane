package Myplane;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss extends FlyingObject implements Enemy {
	
	int xspeed=2;
	private static BufferedImage[] Boss;
	static
	{
		Boss=new BufferedImage[6];
		Boss[0]=loadImage("boss0.png");
		Boss[1]=loadImage("boss1.png");
		for(int i=2;i<Boss.length;i++)
		{
			Boss[i]=loadImage("bom"+(i-1)+".png");
		}
	}
	
	private int fire;
	 int life;
	private int speed;
	Boss()
	{
		super(300,198);
		life=3000;
		fire=1;
		speed=1;
	}

	int lifeIndex=0;
	int deadIndex=2;
	public BufferedImage getImage() 
	{
		if(isLife())
		{
			return Boss[lifeIndex++%2];
		}
		else if(isDead())
		{
			BufferedImage img=Boss[deadIndex++];
			if(deadIndex==Boss.length)
			{
				state=REMOVE;
			}
			return img;
		}
		return null;
	}
	


	public Bullet1[] bossfire() {
		Bullet1 [] b=new Bullet1[2];
		b[0]=new Bullet1(this.x+1*width/3,height+20);
		b[1]=new Bullet1(this.x+2*width/3,height+20);
		return b;
	}


	
	public boolean outOfBounds() {
		return this.y>=World.HEIGHT;	
		}

	public int getScore() {
		
		return 1;
	}
	
	public void step()
	{
		y+=speed;
		
		if(y==0)
		{
			speed=0;
		}
	}
	public void step1() {
		x+=xspeed;
		if(x<=0 || x>=World.WIDTH-this.width)
		{
			xspeed*=-1;
		}
	}
	
	public void goDead()
	{
		if(life==0)
		{
			
			state=DEAD;
			
		}
		
		
	}

	public void substractLife()
	{
		life--;
	}
	
	public void clearDoubleFire()
	{
		fire--;
	}


	
	
}
	
	
