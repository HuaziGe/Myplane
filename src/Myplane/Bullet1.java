package Myplane;

import java.awt.image.BufferedImage;

public class Bullet1 extends FlyingObject{
	
		private static BufferedImage Bullet;
		static
		{
				Bullet=loadImage("bossbullet.png");
		}
		
		private int speed;	
		Bullet1(int x,int y)	{
			super(8,20,x,y);
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
			y+=speed;
		}

		
		public boolean outOfBounds()
		{
			return this.y<=-this.height;
		}
		
	
}
