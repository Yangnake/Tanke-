package tanke;

/*
 * 功能：坦克游戏
 * 1.画坦克
 * 要点一:
 * 为了使mytank在变换方向的时候能够连贯的移动
 * 需要将键盘的按压和释放两个接受的信号都实现
 * 2.能够实现暂停
 * 3.防止敌人坦克出现重叠，将判断函数写到enemytank中
 * 4.可以分关
 * 5.可以暂停和继续
 * 当用户点击暂停时将子弹的速度和坦克的速度设置为0。
 * 并让坦克的方向不要变化
 * 6.可以记录自己的成绩
 * 用文件流（小游戏用文件，大型游戏用数据库）
 */
import java.awt.*;
import java.awt.event.*;//引入事件包
import java.util.Vector;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

class Tank
{
	 int m_x=10;
	 int m_y=15;
	 int m_color=0;//颜色
	 int m_direct=1;//初始方向
	 int m_speed=4;//速度
	 int m_type=0;//坦克的类型，0是游戏的玩家，1是敌人的坦克
	 public boolean islive=true;
	 public boolean turnoff=false;
	 public boolean bU=false, bD=false, bL=false, bR=false;
	 public Tank(int x,int y) {
		 m_x=x;
		 m_y=y;
	 }
	 public void setDirect(int dir)
	 {
		 this.m_direct=dir;
	 }
	 public int getM_x() {
		 return this.m_x;
	 }
	 public int getM_y()
	 {
		 return this.m_y;
	 }
	 public void setM_x(int x) {
		 this.m_x=x;
	 }
	 public void setM_y(int y) {
		 this.m_y=y;
	 }
	 public void setSpeed(int speed) {
		 this.m_speed=speed;
	 }
	 public int getDirect()
	 {
		 return this.m_direct;
	 }
	 public int getType() {
		 return this.m_type;
	 }
	 public void setType(int type) {
		 this.m_type=type;
	 }
}

class Mytank extends Tank 
{
	
//要实现父类的构造函数
	public Mytank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	shot s=null;
	Vector<shot>shotcont=new Vector<shot>();
	
	//开火
	public void shotEnemy()
	{
		//0123上右下左
		switch(this.m_direct)
		{
		case 0:
			//shot(int x,int y,int speed,int direct)
		{
			s=new shot(m_x-2,m_y-16,6,0);
			shotcont.add(s);
		}
			break;
		case 1:
		{
			s=new shot(m_x+16,m_y-1,6,1);
			shotcont.add(s);
		}
			break;
		case 2:
		{
			s=new shot(m_x-2,m_y+16,6,2);
			shotcont.add(s);
		}
			break;
		case 3:
		{
			s=new shot(m_x-16,m_y-1,6,3);
			shotcont.add(s);
		}
			break;
		}
		//启动子弹的线程
		Thread t=new Thread(s);
		t.start();
	}

	public void moveUp() {
		this.m_y-=this.m_speed;
	}
	public void moveRight() {
		this.m_x+=this.m_speed;
	}
	public void moveLeft() {
		this.m_x-=this.m_speed;
	}
	public void moveDown() {
		this.m_y+=this.m_speed;
	}
	
	public void move()
	{
		if(this.bU==true)this.moveUp();
		else if(this.bL==true)this.moveLeft();
		else if(this.bR==true)this.moveRight();
		else if(this.bD==true)this.moveDown();
	}	
}

class enemytank extends Tank implements Runnable{

	Vector<shot>etshot=new Vector<shot>();

	//定义一个向量，可以访问到Panel上的所有人的坦克
	Vector<enemytank>ets=new Vector<enemytank>(); 
	
	public enemytank(int x, int y) {
		super(x, y);

    this.setType(1);
	}
	//的到游戏中敌人坦克
	public void setEts(Vector<enemytank>et)
	{
		this.ets=et;
	}
	//判断是否碰到了别人的坦克
	public boolean isTouchOtherEnemy()
	{
		switch(this.m_direct)
		{
		case 0:
		case 2:
			//该敌人的坦克向上或向下
			//取出所有的敌人坦克
			//0123上右下左
			for(int i=0;i<ets.size();i++)
			{
				//取出一个敌人坦克
				enemytank et=ets.get(i);
				//判断不是自己
				if(et!=this)
				{
					//如果敌人的方向是向下或者向上
					if(et.m_direct==0||et.m_direct==2)
					{
						if(et.m_x-10>=this.m_x-30 && et.m_x-10<=this.m_x+10 && Math.abs(this.m_y-et.m_y)<=30)
							return true;
					}
					//敌人的坦克向左向右
					else if(et.m_direct==1||et.m_direct==3)
					{
						if(et.m_x+15>=this.m_x-10 && et.m_x<=this.m_x+40 && Math.abs(this.m_y-et.m_y)<=25)
							return true;
					}
				}
			}
			break;
		case 1:
		case 3:
			//该坦克向右或向右
			for(int i=0;i<ets.size();i++)
	       {
				enemytank et=ets.get(i);  
				//取出一个坦克
				if(et!=this)
				{
				  //如果遇到其余坦克向上或者向下
					if(et.m_direct==0||et.m_direct==2)
					{
						if(et.m_x+10>=this.m_x-15&&et.m_x-10<=this.m_x+15&&Math.abs(et.m_y-this.m_y)<=25)
							return true;
					}
					//其余坦克向左或者向右
					else if(et.m_direct==1||et.m_direct==3)
					{
						if(et.m_y-10<=this.m_y+10&&et.m_y-10>=this.m_y-30 &&Math.abs(et.m_x-this.m_x)<=30)
							return true;
					}
				}
			}
			break;
		}
		return false;
	}
	@Override
	public void run() {//线程
		// TODO Auto-generated method stub
		
		while(true)
		{
			if(!turnoff) {
			for(int i=0;i<this.etshot.size();i++)
				if(etshot.get(i).islive==false)etshot.removeElementAt(i);
			
			//如果坦克死亡就让其退出线程
			if(this.islive==false)break;
			
			//System.out.println(this.getDirect());
			switch(this.getDirect())
			{
			//0123上右下左
			case 0: //up
			{
				for(int i=0;i<20;i++ ) {
					if(m_y>0&& !this.isTouchOtherEnemy())this.m_y-=m_speed;
					try {
						Thread.sleep(50);//睡眠50毫秒
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			};break;
			case 1:{
				for(int i=0;i<20;i++)
				{
					if(m_x<400 && !this.isTouchOtherEnemy())this.m_x+=m_speed;
				try {
					Thread.sleep(50);
				}catch(Exception e) {
					e.printStackTrace();
				}}
			};break;
			case 2:{
				for(int i=0;i<20;i++)
				{
				if(m_y<300 && !this.isTouchOtherEnemy())this.m_y+=m_speed;
				try {
					Thread.sleep(50);
				}catch(Exception e) {
					e.printStackTrace();
				}}
			};break;
			case 3:{
				for(int i=0;i<20;i++)
				{
					if(m_x>0 && !this.isTouchOtherEnemy())this.m_x-=m_speed;
				try {
					Thread.sleep(50);
				}catch(Exception e) {
					e.printStackTrace();
				}}
			};break;
			}

			
				//判断是否需要加入一颗子弹
						if(this.etshot.size()<3)
						{
							shot s=new shot(this.m_x,this.m_y,6,this.m_direct);
							this.etshot.add(s);
							//启动的子弹
							Thread t=new Thread(s);
							t.start();
						}
			
			//Math.random()
			//generator 0-1 double 
			this.m_direct=(int)(Math.random()*4);
		}
			else 
			{
				try {
					Thread.sleep(50);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
	}
	}
    
	
}

//子弹
//每个子弹需要有一个线程
 class shot implements Runnable{
	int m_x;
	int m_y;
	int m_speed=6;
	int m_direct;
	boolean islive=true;
	public boolean turnoff=false;
	public shot(int x,int y,int speed,int direct)
	{
		m_x=x;
		m_y=y;
		m_speed=speed;
		m_direct=direct;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//0123上右下左
		while(true)
		{
			try {
				Thread.sleep(35);
			}catch(Exception e) 
			{ 
				e.printStackTrace();
			}
			if(!turnoff) {
			
			switch(m_direct)
			{
			case 0:
				//上
				m_y-=m_speed;
				break;
			case 1:
				//右
				m_x+=m_speed;
				break;
			case 2:
				//下
				m_y+=m_speed;
				break;
			case 3:
				//左
				m_x-=m_speed;
				break;
			}
			if(m_x<=0||m_x>=400||m_y<=0||m_y>=300)
				this.islive=false;
			
			if(this.islive==false)
				break;
		}
		}
	}
}
 
class Bomb
{
	//定义炸弹坐标
	int x,y;
	//炸弹的生命
	int life=6;
	boolean isLive=true;
	
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//减少生命值
	public void lifeDown()
	{
		if(life>0)
		{
			--life;
		}else {
			this.isLive=false;
		}
	}
}
 
public class tanke extends JFrame implements ActionListener{
	Mypanel mp=null; //创建一个指向Mypanel对象的指针，开始为空
	
	//定义一个开始面板
	mystart ms=null;
	
	//做出需要的菜单
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//退出系统
	JMenuItem jmi2=null;
	
	JMenuItem jmi3=null;
	
 public tanke()
 { 
	 //创建菜单和菜单选项
	 jmb=new JMenuBar();
	 jm1=new JMenu("游戏(G)");
	 //设置快捷方式
	 jm1.setMnemonic('G');
	 
	 jmi1=new JMenuItem("开始游戏(N)");
	 jmi2=new JMenuItem("退出游戏(E)");
	 jmi3=new JMenuItem("退出并保存(S)");

	 //对jmi1添加注册
	 jmi1.addActionListener(this);
	 jmi1.setActionCommand("newgame");//给组件添加名称
	 
	 jmi2.addActionListener(this);
	 jmi2.setActionCommand("exit");
	 
	 jmi3.addActionListener(this);
	 jmi3.setActionCommand("save");
	 
	 jm1.add(jmi1);
	 jm1.add(jmi2);
	 jm1.add(jmi3);
	 jmb.add(jm1);
	 this.setJMenuBar(jmb);
	 
	 ms=new mystart();
	 Thread t=new Thread(ms);
	 t.start();
	 this.add(ms);
     this.setSize(400, 400);
     this.setVisible(true);   
     this.setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      tanke tk=new tanke();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("newgame"))
		{
			 mp=new Mypanel();
			 //创建线程
			 Thread t=new Thread(mp);
			 t.start();
			 //先删除旧的面板
			 this.remove(ms);
			 
			 this.add(mp);//坦克类添加这个面板
			 this.addKeyListener(mp);//注册监听
			 //显示
			 this.setVisible(true);
		}else if(arg0.getActionCommand().equals("exit"))
		{
			//用户点击了退出系统的菜单
			//保存击毁敌人数量
			Recorder.setEts(mp.ets);
			Recorder.keepRecording();
			System.exit(0);
		}
		else if(arg0.getActionCommand().equals("save"))
		{
			//将信息保存在文件中
			
		}
	}
}

//定义一个自己的面板
//用于绘图和显示的区域
class Mypanel extends JPanel implements KeyListener,Runnable{
	Mytank mt=null;
	//构造敌人的坦克
	Vector<enemytank> ets=new Vector<enemytank>();
	int ensize=5;
	
	//定义炸弹集合
	Vector<Bomb> bombs=new Vector<Bomb>();

	//暂停表示
	boolean turnoff=false;
	int count=0;
	
	//定义6张图片组成炸弹
	Image image1=null;
	Image image2=null;
	Image image3=null;
	Image image4=null;
	Image image5=null;
	Image image6=null;
	
	
    //for(int i=0;i<ensize;i++) {};
	//覆盖JPanel中的paint方法
	//Graphics是绘图的重要类，可以用于绘图和实现绘图的区域
	public void paint(Graphics g) {//重写的paint类,g相当于一支画笔，所以要用同一支画笔
		super.paint(g);//调用父类完成初始华
		//绘制界面，当鼠标拖动界面和界面的缩放都会进行重画
		g.fillRect(0, 0, 400, 300);//设置一个400*300的矩形，用预定的颜色来填充这个矩形
		
		//画出提示信息
		this.showInfo(g);
		
		//画出我的坦克
		if(mt.islive)
		 this.drawTank(mt.getM_x(), mt.getM_y(), g,mt.getDirect() , mt.getType());
		 mt.move();
		 for(int i=0;i<mt.shotcont.size();i++)
		 {
			 shot myshot=mt.shotcont.get(i);
			 if(myshot!=null && myshot.islive==true)
			 {
				 g.draw3DRect(myshot.m_x, myshot.m_y, 1, 1, false);
			 }
			 else if(myshot.islive==false)
			 {
				 //删除子弹
				 mt.shotcont.remove(myshot);
			 }
		 }
		 
		 /*
		  * 画出敌人的坦克
		  */
		 
		 for(int i=0;i<ets.size();i++)
		 {
			 enemytank et = ets.get(i);
			 if(et.islive)
			 {
				 this.drawTank(et.getM_x(), et.getM_y(), g, et.getDirect(), et.getType());
				 //画出敌人坦克的子弹 
				 for(int j=0;j<et.etshot.size();j++)
				 {
					 //取出子弹
					 shot eshot=et.etshot.get(j);
					 if(eshot.islive)
					 {
						 g.draw3DRect(eshot.m_x, eshot.m_y, 1, 1, false);
					 }
				 }
			 }
		 }
		 
		 
		//System.out.println(ets.size());
		 
		 //画出炸弹
		 for(int i=0;i<bombs.size();i++)
		 {
			 Bomb b=bombs.get(i);
			 if(b.isLive==false)
				 bombs.remove(b);
			 else {
			 if(b.life>5)
			 {
				boolean j= g.drawImage(image1, b.x, b.y, 30, 30, this);
				if(j==false)System.out.print("false1");
			 }else if(b.life>4)
			 {
				 boolean j=g.drawImage(image2, b.x, b.y, 30, 30, this);
				 if(j==false)System.out.print("false2");
			 }else if(b.life>3)
			 {
				 boolean j=g.drawImage(image3, b.x, b.y, 30, 30, this);
				 if(j==false)System.out.print("false3");
			 }else if(b.life>2)
			 {
				 boolean j=g.drawImage(image4, b.x, b.y, 30, 30, this);
				 if(j==false)System.out.print("false4");
			 }else if(b.life>1)
			 {
				 boolean j=g.drawImage(image5, b.x, b.y, 30, 30, this);
				 if(j==false)System.out.print("false5");
			 }else
			 {
				 boolean j=g.drawImage(image6, b.x, b.y, 30, 30, this);
				 if(j==false)System.out.print("false6");
			 }
			 //让生命值减小
			 b.lifeDown();
			 if(b.life==0)
			 {
				 bombs.remove(b);
			 }
		 }
		 }
	}
	//Graphics r=null;
	public Mypanel(){//在面板的构造函数中加入我的坦克
		
		//初始化图片
		//"images/jinshan.jpg"

		//读取记录
		Recorder.getRecording();
		 ensize=Recorder.getEnNum();
		//MediaTracker对象
		MediaTracker mediaTracker = new MediaTracker(this);
		
		image1=Toolkit.getDefaultToolkit().getImage("./images/one.gif");;
		image2=Toolkit.getDefaultToolkit().getImage("./images/two.gif");
		image3=Toolkit.getDefaultToolkit().getImage("./images/three.gif");
		image4=Toolkit.getDefaultToolkit().getImage("./images/four.gif");
		image5=Toolkit.getDefaultToolkit().getImage("./images/five.gif");
		image6=Toolkit.getDefaultToolkit().getImage("./images/six.gif");
		//将图片添加到媒体追踪器
		//第二个参数是控制取图优先次序的一个整数id。载入图像时，小id具有较高优先
		//这个id也可以用来询问mediaTracker关于注册图像的状态
		mediaTracker.addImage(image1, 1);
		mediaTracker.addImage(image2, 2);
		mediaTracker.addImage(image3, 3);
		mediaTracker.addImage(image4, 4);
		mediaTracker.addImage(image5, 5);
		mediaTracker.addImage(image6, 6);
		
		//检查图片是否加载完，如果没有则加载完
		mediaTracker.checkAll(true);

		//创建我的坦克
		mt=new  Mytank(10,15);
		//创建敌人的坦克
		for(int i=0;i<ensize;i++)
		{
			//创建敌人坦克的对象
			enemytank et=new enemytank((i+1)*50+15,100);
			//设置方向
			et.setDirect(0);
			ets.add(et);
			//将panel的敌人坦克向量交给该敌人坦克
			et.setEts(ets);
			
			//启动敌人坦克的线程
			Thread t=new Thread(et);
			t.start();
		}
	}
	
	 //显示坦克的信息
public void showInfo(Graphics g)
{
		 //画出提示信息
		 this.drawTank(20, 320, g,0 , 0);
		 g.setColor(Color.red);
		 g.drawString(Recorder.getMyLife()+"", 40, 325);
		 this.drawTank(80, 320, g,0 , 1);
		 g.setColor(Color.red);
		 g.drawString(Recorder.getEnNum()+"", 100, 325);
		 
		 //计算分数
		 g.setColor(Color.black);
		 Font f=new Font("宋体",Font.BOLD,20);
		 g.setFont(f);
		 g.drawString("当前得分：", 130, 325);
		 g.drawString(Recorder.getGrade()+"", 230, 325);

}

	//判断敌人的坦克是否击中我
public void hitMe()
{
	//取出每一个敌人的坦克
	for(int i=0;i<this.ets.size();i++)
	{
		//取出坦克
		enemytank et=ets.get(i);
		//取出每一颗子弹
		for(int j=0;j<et.etshot.size();j++)
		{
			shot enemtshot=et.etshot.get(j);
			if(mt.islive==true)
			{
				this.hitTank(enemtshot, mt);
			}
		}
	}
}

//判断敌人坦克是否被子弹击中
public void hitTank(shot s,Tank et)
{
	//判断该坦克的方向
	switch(et.m_direct)
	{
	//如果敌人坦克的方向是向上或是向下
	case 0:
	case 2:
		if(s.m_x>(et.m_x-10) && s.m_x<(et.m_x+10)&&s.m_y>(et.m_y-15)&&s.m_y<(et.m_y+15))
		{
			//击中子弹死亡
			s.islive=false;
			//敌人坦克死亡
			et.islive=false;
			//创建一颗炸弹，放入vector中管理
		    Bomb b=new Bomb(et.m_x-10,et.m_y-15);
			bombs.add(b);
			//放出声音./images/one.gif
			AePlayWave aeplaywave=new AePlayWave("./images/bomb.wav");
			Thread t=new Thread(aeplaywave);
			t.start();
			//静态方法，减一个敌人的坦克
			if(et.m_type==0)
				Recorder.reduceMtNum();
			else
				{
				Recorder.reduceEnNum();
				Recorder.addGrade();
				}
			
		};break;
	case 1:
	case 3:
		if(s.m_x>(et.m_x-15)&&s.m_x<(et.m_x+15)&&s.m_y>(et.m_y-10)&&s.m_y<(et.m_y+10))
		{
			//击中子弹死亡
			s.islive=false;
			//坦克死亡
			et.islive=false;
			Bomb b=new Bomb(et.m_x-15,et.m_y-10);
			bombs.add(b);
			//放出声音
			AePlayWave aeplaywave=new AePlayWave("./images/bomb.wav");
			Thread t=new Thread(aeplaywave);
			t.start();
			if(et.m_type==0)
				Recorder.reduceMtNum();
			else
				{
				Recorder.reduceEnNum();
				Recorder.addGrade();
				}
		};break;
	}
}
/*
 * 坦克的属性
 * 坦克的坐标x,y 
 * 图片对象，在哪个上面画坦克
 * 坦克的方向
 * 坦克的类型
 */
	public void drawTank(int x,int y,Graphics g,int direct,int type)//画坦克
	{
		switch(type)//涂色
		{
		//设置颜色的函数是向下覆盖的，设置颜色后只会覆盖下面的所绘制的图形的颜色
		case 0: g.setColor(Color.cyan);break;
		case 1:g.setColor(Color.yellow);break;
		}
		//判断方向
				switch(direct)
				{
				case 0://画出我的坦克,朝上
					//画出左面的矩形
					
					g.fill3DRect(x-10, y-15, 5, 30,false);
					//g.draw3DRect(x, y, width, height, raised);//raised是否突出，突出则true否则false 
					g.fill3DRect(x+5, y-15, 5, 30,false);
					//画出中间的矩形
					g.fill3DRect(x-5, y-10, 10, 20,false);
					//画圆形
					g.fillOval(x-6, y-7, 10, 10);
					//画炮管
					g.drawLine(x-1, y,x-1, y-15);
					break;
				case 1:
					//朝右
					g.fill3DRect(x-15, y-10, 30, 5,false);
					//g.draw3DRect(x, y, width, height, raised);//raised是否突出，突出则true否则false 
					g.fill3DRect(x-15, y+5, 30, 5,false);
					//画出中间的矩形
					g.fill3DRect(x-10, y-5, 20, 10,false);
					//画圆形
					g.fillOval(x-6, y-6, 10, 10);
					//画炮管
					g.drawLine(x, y-1,x+15, y-1);
					break;
				case 2://向下
					g.fill3DRect(x-10, y-15, 5, 30,false);
					//g.draw3DRect(x, y, width, height, raised);//raised是否突出，突出则true否则false 
					g.fill3DRect(x+5, y-15, 5, 30,false);
					//画出中间的矩形
					g.fill3DRect(x-5, y-10, 10, 20,false);
					//画圆形
					g.fillOval(x-6, y-7, 10, 10);
					//画炮管
					g.drawLine(x-1, y,x-1, y+15);
					break;
				case 3://向左
					g.fill3DRect(x-15, y-10, 30, 5,false);
					//g.draw3DRect(x, y, width, height, raised);//raised是否突出，突出则true否则false 
					g.fill3DRect(x-15, y+5, 30, 5,false);
					//画出中间的矩形
					g.fill3DRect(x-10, y-5, 20, 10,false);
					//画圆形
					g.fillOval(x-6, y-6, 10, 10);
					//画炮管
					g.drawLine(x, y-1,x-15, y-1);
					break;
				}
	}





@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	if(e.getKeyCode()==KeyEvent.VK_W && !turnoff)
	{
		//设置我的坦克方向
		mt.setDirect(0);
		//mt.moveUp();
		mt.bU=true;
		//System.out.println("改变了方向");
	}
	else if(e.getKeyCode()==KeyEvent.VK_D && !turnoff)
	{
		//向右
		this.mt.setDirect(1);
		//mt.moveRight();
		mt.bR=true;
	}
	else if(e.getKeyCode()==KeyEvent.VK_S && !turnoff)
	{
		//向左
		this.mt.setDirect(2);
		//mt.moveDown();
		mt.bD=true;
	}
	else if(e.getKeyCode()==KeyEvent.VK_A && !turnoff)
	{
		//向左
		this.mt.setDirect(3);
	    //mt.moveLeft();
		mt.bL=true;
	}
	
	//玩家按下开火键
	if(e.getKeyCode()==KeyEvent.VK_J && !turnoff)
	{
		if(this.mt.shotcont.size()<6)
		{
			this.mt.shotEnemy();
		}
	}
	//暂停
	if(e.getKeyCode()==KeyEvent.VK_SPACE)
	{
		if(count%2==0)
		{
		turnoff=true;
		for(int i=0;i<ets.size();i++)
		{
			enemytank et=ets.get(i);
			et.turnoff=true;
			for(int j=0;j<et.etshot.size();j++)
			{
				et.etshot.get(j).turnoff=true;
			}
		}
		}
		else if(count%2==1)
			{
			turnoff=false;
			for(int i=0;i<ets.size();i++)
			{
				enemytank et=ets.get(i);
				et.turnoff=false;
				for(int j=0;j<et.etshot.size();j++)
				{
					et.etshot.get(j).turnoff=false;
				}
			}
		
	}
		count++;
	}
	//重新绘制Panel
	this.repaint();
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	if(e.getKeyCode()==KeyEvent.VK_W && !turnoff)
	{
		//设置我的坦克方向
		//mt.setDirect(0);
		//mt.moveUp();
		mt.bU=false;
		//System.out.println("改变了方向");
	}
	else if(e.getKeyCode()==KeyEvent.VK_D && !turnoff)
	{
		//向右
		//this.mt.setDirect(1);
		//mt.moveRight();
	   mt.bR=false;
	}
	else if(e.getKeyCode()==KeyEvent.VK_S && !turnoff)
	{
		//向左
		//this.mt.setDirect(2);
		//mt.moveDown();
		mt.bD=false;
	}
	else if(e.getKeyCode()==KeyEvent.VK_A && !turnoff)
	{
		//向左
		//this.mt.setDirect(3);
	    //mt.moveLeft();
		mt.bL=false;
	}
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void run() {//屏幕需要一个线程来重写
	// TODO Auto-generated method stub
	while(true) {
		try {
			Thread.sleep(35);
		}catch(Exception e) {
			e.printStackTrace();
		}
     if(!turnoff) {
		//每个100ms重绘一遍

		//这里判断子弹是否击中了坦克
		for(int i=0;i<mt.shotcont.size();i++)
		{
			//取出子弹
			shot myshot=mt.shotcont.get(i);
			//判断子弹是否还活着
			if(myshot.islive)
			{
				//取出每个坦克，与他判断
				for(int j=0;j<ets.size();j++)
				{
					enemytank et=ets.get(j);
					if(et.islive)
					{
					this.hitTank(myshot, et);
					}
				}
			}
		}

		//判断敌人的坦克是否击中了我
		this.hitMe();
		this.repaint();

	}
	}
	
}
}


