package Myplane;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;





//主程序
public class World extends JPanel
{	
	public static final int WIDTH=512;
	public static final int HEIGHT=768;
	
	
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int PAUSE=2;
	public static final int GAME_OVER=4;
	private int state=START;
	
	
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameover;
	
	static
	{
		start=FlyingObject.loadImage("start.jpg");
		pause=FlyingObject.loadImage("pause.jpg");
		gameover=FlyingObject.loadImage("gameover.jpg");
	}
	
	private Sky sky=new Sky();
	private Hero hero=new Hero();
	private FlyingObject[] enemies= {};
	private Bullet[] bullets= {};
	private Bullet1[] bullets1= {};
	private Boss boss=new Boss();
	static Music music =new Music();
	
	//生成敌人对象
	public FlyingObject nextOne()
	{
		Random rand=new Random();
		int type=rand.nextInt(100);
		if(type>70)
		{
			return new Bee();
		}else if(type>40)
		{
			return new AirPlane();
		}else if(type<6)
		{
			return new BigAirPlane();
		}
		else {
			return boss;
			
		}
	}
	
	//生成Boss机
	/*public FlyingObject bossOne()
	{
		Random rand=new Random();
		int type=rand.nextInt(20);
		if(type<20) {
			return boss;
		}else
		return boss;
		
		
	}*/
	
	//敌人入场
	int enterIndex=0;
	public void enterAction()
	{
		enterIndex++;
		if(enterIndex%40==0){	
			//获取敌人对象
			FlyingObject obj=nextOne();
			//扩容
			enemies=Arrays.copyOf(enemies,enemies.length+1);
			enemies[enemies.length-1]=obj;
		}
//		if(enterIndex%200==0&&score>100){	
//			//扩容
//			enemies=Arrays.copyOf(enemies,enemies.length+1);
//			enemies[enemies.length-1]=new boss;
//		}
//		
	}
	
	//Boss入场
	/*int BenterIndex=0;
	public void BenterAction()
	{
		enterIndex++;
		if(enterIndex%40==0)
		{
			FlyingObject bo=bossOne();
		}
	}*/
	
	int shootIndex=0;
	public void shootAction()
	{
		shootIndex++;
		if(shootIndex%30==0)
		{
			Bullet[] bs=hero.shoot();
			bullets=Arrays.copyOf(bullets,bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	
	int shootIndex2=0;
	public void bossAction()
	{
		if(boss.isLife()) {
			shootIndex2++;
			if(shootIndex%100==0)
			{
				Bullet1[] bs=boss.bossfire();
				bullets1=Arrays.copyOf(bullets1,bullets1.length+bs.length);
				System.arraycopy(bs, 0, bullets1, bullets1.length-bs.length, bs.length);
			}
		}
		
	}
	
	
	
	public void stepAction()
	{
		sky.step();
		boss.step();
		if(boss.y==0) {
			boss.step1();
		}
		for(int i=0;i<enemies.length;i++)
		{
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++)
		{
			bullets[i].step();
		}
		for(int i=0;i<bullets1.length;i++) {
			bullets1[i].step();
		}
	}
	
	public void outOfBoundsAction()
	{
		//删除三种敌人
		int index=0;
		//不越界的敌人
		FlyingObject[] enemiesLife=new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++)
		{
			FlyingObject obj=enemies[i];
			if(!obj.outOfBounds())
			{
				enemiesLife[index]=obj;
				index++;
			}
		}
		enemies=Arrays.copyOf(enemiesLife,index);
		
		//删除hero子弹
		int index1=0;
		Bullet[] bulletsLife=new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++)
		{
			Bullet obj=bullets[i];
			if(!obj.outOfBounds())
			{
				bulletsLife[index1]=obj;
				index1++;
			}
		}
		bullets=Arrays.copyOf(bulletsLife,index1);		
		
		//删除boss子弹
		int index2=0;
		Bullet1[] bullets1Life=new Bullet1[bullets1.length];
		for(int i=0;i<bullets1.length;i++)
		{
			Bullet1 obj=bullets1[i];
			if(!obj.outOfBounds())
			{
				bullets1Life[index2]=obj;
				index2++;
			}
		}
		bullets1=Arrays.copyOf(bullets1Life,index2);		
	
	}
	
	//英雄机与敌人碰撞
	public void heroBangAction() 
	{
		for(int i=0;i<enemies.length;i++)
		{
			FlyingObject f=enemies[i];
			if(hero.isLife()&&f.isLife()&&f.hit(hero))
			{
				f.goDead();
				hero.substractLife();
				hero.clearDoubleFire();
			}
		}
			
	}
	
	//hero子弹与boss机碰撞
	public void bossBangAction() 
	{
		System.out.println(boss.life);
		for(int i=0;i<bullets.length;i++)
		{
			FlyingObject f=bullets[i];
			if(boss.isLife()&&boss.hit(f))
			{
				f.goDead();

				boss.substractLife();
				boss.clearDoubleFire();
				if(boss.life==0) {
					boss.goDead();
				}
			}
		}
			
	}
	
	//boss子弹与hero机碰撞
	public void bullet1BangAction() 
	{
		for(int i=0;i<bullets1.length;i++)
		{
			FlyingObject f=bullets1[i];
			if(hero.isLife()&&f.isLife()&&f.hit(hero))
			{
				f.goDead();
				hero.substractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	
	int score=0;
	public void bulletBangAction()
	{
		for(int i=0;i<bullets.length;i++)
		{
			Bullet b=bullets[i];
			for(int j=0;j<enemies.length;j++)
			{
				FlyingObject f=enemies[j];
				if(b.isLife() && f.isLife() && f.hit(b))
				{
					f.goDead();
					b.goDead();
					if(f instanceof Enemy)
					{
						Enemy en=(Enemy)f;
						score+=en.getScore();
					}
					if (f instanceof Award)
					{
						Award aw=(Award)f;
						int type=aw.getAwardType();
						switch(type)
						{
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}
				}
			}
		}
	}
	
	
	
	
	public void checkGameOverAction()
	{
		if(hero.getLife()<=0)
		{
			state=GAME_OVER;
		}
		
	}
	
	
	
	//启动程序执行
	public void action()
	{	
		MouseAdapter ma=new MouseAdapter()
		{
			//创建侦听器对象
			public void mouseMoved(MouseEvent e)
			{
				if(state==RUNNING)
				{
				int x=e.getX();
				int y=e.getY();
				hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e)
			{
				switch(state)
				{
				case START:
					state=RUNNING;
					break;
				case GAME_OVER:
					score=0;
					sky=new Sky();
					hero=new Hero();
					enemies=new FlyingObject[0];
					bullets=new Bullet[0];
					state=START;
					break;
				}
			}
			
			//移出事件
			public void mouseExited(MouseEvent e)
			{
				if(state==RUNNING)
				{
					state=PAUSE;
				}
			}
			
			//移入事件
			public void mouseEntered(MouseEvent e)
			{
				if(state==PAUSE)
				{
					state=RUNNING;
				}
			}
		};
		//加载到窗口
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
		
		
		//创建定时器对象
		Timer timer=new Timer();
		int intervel=10;
		timer.schedule(new TimerTask() 
		{
			public void run()
			{
				if(state==RUNNING)
				{
					enterAction();
					//BenterAction();
					shootAction();
					bossAction();
					stepAction();
					outOfBoundsAction();
					bulletBangAction();
					heroBangAction();
					bullet1BangAction();
					bossBangAction();
					checkGameOverAction();
				}
				
				repaint();
			}
		},intervel,intervel);
	}
	
	
	
	//画笔方法
	public void paint(Graphics g)
	{
		boss.paintObject(g);
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i=0;i<bullets.length;i++)
		{
			bullets[i].paintObject(g);
		}
		for(int i=0;i<bullets1.length;i++)
		{
			bullets1[i].paintObject(g);
		}
		for(int i=0;i<enemies.length;i++)
		{
			enemies[i].paintObject(g);
		}
		
		
	
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
		g.setColor(new Color(255,200,0));
		g.drawString("分数："+score,10,25);
		g.setColor(new Color(255,0,0));
		g.drawString("命数："+hero.getLife(),10,55);
		
		switch(state)
		{
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
	         
		
	
	}
	

	
	public static void main(String[] args)
	{
			
		
		JFrame frame=new JFrame();
		World world=new World();
		
		frame.add(world);
		frame.setVisible(true);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("雷霆战机");
		
		world.action();
		music.player();
				
	}
	
}