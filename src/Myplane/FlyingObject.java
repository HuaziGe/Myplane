package Myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public abstract class FlyingObject 
{
	public static final int LIFE=0;//����
	public static final int DEAD=1;//����
	public static final int REMOVE=2;//ɾ��
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
			BufferedImage img=ImageIO.read(FlyingObject.class.getResource(FileName));//BufferedImage:�ڴ��е�ͼƬ����
			return img;
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	

	public abstract BufferedImage getImage();//ͼƬ
	public abstract boolean outOfBounds();//����Ƿ����
	public abstract void step();//��������һ��
	public long start=System.currentTimeMillis();
	
	
	public boolean isLife()//�ж��Ƿ����
	{
		return state==LIFE;
	}
	public boolean isDead()//�ж��Ƿ�����
	{
		return state==DEAD;
	}
	public boolean isRemove()//�ж��Ƿ�ɾ��
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












