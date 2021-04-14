package Myplane;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject
{
	private static BufferedImage[] Heros;
	static
	{
		Heros=new BufferedImage[2];
		for(int i=0;i<Heros.length;i++)
		{
			Heros[i]=loadImage("hero"+i+".png");
		}
	}
	private int life;
	private int fire;
	
	Hero()
	{
		super(97,139,200,500);
		life=100;
		fire=0;
	}
	
	
	int index=0;
	public BufferedImage getImage() 
	{
		if(isLife())
		{
			return Heros[index++%2];
		}
		return null;
	}
	
	
	public Bullet[] shoot()
	{
		if(fire>0)
		{
			Bullet[] bs=new Bullet[2];
			bs[0]=new Bullet(this.x+1*this.width/4,this.y-20);
			bs[1]=new Bullet(this.x+3*this.width/4,this.y-20);
			fire-=5;
			return bs;
		}else
		{

			Bullet[] bs=new Bullet[1];
			bs[0]=new Bullet(this.x+1*this.width/2,this.y-20);
			return bs;
		}
	}
	
	public void step()
	{	
		
	}
	
	
	public void moveTo(int x,int y)
	{
		this.x=x-this.width/2;
		this.y=y-this.height/2;
	}


	
	public boolean outOfBounds() 
	{
		return false;
	}
	
	public void addLife()
	{
		life++;
	}
	
	public void addDoubleFire()
	{
		fire+=50;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public void substractLife()
	{
		life--;
	}
	
	public void clearDoubleFire()
	{
		fire=0;
	}
	
	
	
	
}