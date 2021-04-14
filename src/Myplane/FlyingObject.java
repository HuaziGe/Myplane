package Myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public abstract class FlyingObject 
{
	public static final int LIFE=0;//活着
	public static final int DEAD=1;//死了
	public static final int REMOVE=2;//删除
	protected int state=LIFE;
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;

	public FlyingObject(int width,int height)
	{
		this.width=width;
		this.height=height;
		Random rand=new Random();
		x=rand.nextInt(World.WIDTH-width);
		y=-height;
	}
	
	public FlyingObject(int width,int height,int x,int y)
	{
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
	}
		
	
	
	public static BufferedImage loadImage(String FileName)
	{
		try
		{
			BufferedImage img=ImageIO.read(FlyingObject.class.getResource(FileName));//BufferedImage:内存中的图片对象
			return img;
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public abstract BufferedImage getImage();//图片
	public abstract boolean outOfBounds();//检查是否出界
	public abstract void step();//飞行物走一步
	public long start=System.currentTimeMillis();
	
	
	public boolean isLife()//判断是否活着
	{
		return state==LIFE;
	}
	public boolean isDead()//判断是否死了
	{
		return state==DEAD;
	}
	public boolean isRemove()//判断是否删除
	{
		return state==REMOVE;
	}
	
	
	
	
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,width,height,null);
	}
	
	
	
	
	public boolean hit(FlyingObject other)
	{
		int x1=this.x-other.width;
		int x2=this.x+this.width;
		int y1=this.y-other.height;
		int y2=this.y+this.height;
		int x=other.x;
		int y=other.y;
		return x>=x1&&x<=x2&&y>=y1&&y<=y2;
	}
	
	public void goDead()
	{
		state=DEAD;
	}
	
	
	
}












