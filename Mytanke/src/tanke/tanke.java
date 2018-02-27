package tanke;

/*
 * ���ܣ�̹����Ϸ
 * 1.��̹��
 * Ҫ��һ:
 * Ϊ��ʹmytank�ڱ任�����ʱ���ܹ�������ƶ�
 * ��Ҫ�����̵İ�ѹ���ͷ��������ܵ��źŶ�ʵ��
 * 2.�ܹ�ʵ����ͣ
 * 3.��ֹ����̹�˳����ص������жϺ���д��enemytank��
 * 4.���Էֹ�
 * 5.������ͣ�ͼ���
 * ���û������ͣʱ���ӵ����ٶȺ�̹�˵��ٶ�����Ϊ0��
 * ����̹�˵ķ���Ҫ�仯
 * 6.���Լ�¼�Լ��ĳɼ�
 * ���ļ�����С��Ϸ���ļ���������Ϸ�����ݿ⣩
 */
import java.awt.*;
import java.awt.event.*;//�����¼���
import java.util.Vector;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

class Tank
{
	 int m_x=10;
	 int m_y=15;
	 int m_color=0;//��ɫ
	 int m_direct=1;//��ʼ����
	 int m_speed=4;//�ٶ�
	 int m_type=0;//̹�˵����ͣ�0����Ϸ����ң�1�ǵ��˵�̹��
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
	
//Ҫʵ�ָ���Ĺ��캯��
	public Mytank(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	shot s=null;
	Vector<shot>shotcont=new Vector<shot>();
	
	//����
	public void shotEnemy()
	{
		//0123��������
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
		//�����ӵ����߳�
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

	//����һ�����������Է��ʵ�Panel�ϵ������˵�̹��
	Vector<enemytank>ets=new Vector<enemytank>(); 
	
	public enemytank(int x, int y) {
		super(x, y);

    this.setType(1);
	}
	//�ĵ���Ϸ�е���̹��
	public void setEts(Vector<enemytank>et)
	{
		this.ets=et;
	}
	//�ж��Ƿ������˱��˵�̹��
	public boolean isTouchOtherEnemy()
	{
		switch(this.m_direct)
		{
		case 0:
		case 2:
			//�õ��˵�̹�����ϻ�����
			//ȡ�����еĵ���̹��
			//0123��������
			for(int i=0;i<ets.size();i++)
			{
				//ȡ��һ������̹��
				enemytank et=ets.get(i);
				//�жϲ����Լ�
				if(et!=this)
				{
					//������˵ķ��������»�������
					if(et.m_direct==0||et.m_direct==2)
					{
						if(et.m_x-10>=this.m_x-30 && et.m_x-10<=this.m_x+10 && Math.abs(this.m_y-et.m_y)<=30)
							return true;
					}
					//���˵�̹����������
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
			//��̹�����һ�����
			for(int i=0;i<ets.size();i++)
	       {
				enemytank et=ets.get(i);  
				//ȡ��һ��̹��
				if(et!=this)
				{
				  //�����������̹�����ϻ�������
					if(et.m_direct==0||et.m_direct==2)
					{
						if(et.m_x+10>=this.m_x-15&&et.m_x-10<=this.m_x+15&&Math.abs(et.m_y-this.m_y)<=25)
							return true;
					}
					//����̹�������������
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
	public void run() {//�߳�
		// TODO Auto-generated method stub
		
		while(true)
		{
			if(!turnoff) {
			for(int i=0;i<this.etshot.size();i++)
				if(etshot.get(i).islive==false)etshot.removeElementAt(i);
			
			//���̹�������������˳��߳�
			if(this.islive==false)break;
			
			//System.out.println(this.getDirect());
			switch(this.getDirect())
			{
			//0123��������
			case 0: //up
			{
				for(int i=0;i<20;i++ ) {
					if(m_y>0&& !this.isTouchOtherEnemy())this.m_y-=m_speed;
					try {
						Thread.sleep(50);//˯��50����
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

			
				//�ж��Ƿ���Ҫ����һ���ӵ�
						if(this.etshot.size()<3)
						{
							shot s=new shot(this.m_x,this.m_y,6,this.m_direct);
							this.etshot.add(s);
							//�������ӵ�
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

//�ӵ�
//ÿ���ӵ���Ҫ��һ���߳�
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
		//0123��������
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
				//��
				m_y-=m_speed;
				break;
			case 1:
				//��
				m_x+=m_speed;
				break;
			case 2:
				//��
				m_y+=m_speed;
				break;
			case 3:
				//��
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
	//����ը������
	int x,y;
	//ը��������
	int life=6;
	boolean isLive=true;
	
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	//��������ֵ
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
	Mypanel mp=null; //����һ��ָ��Mypanel�����ָ�룬��ʼΪ��
	
	//����һ����ʼ���
	mystart ms=null;
	
	//������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//�˳�ϵͳ
	JMenuItem jmi2=null;
	
	JMenuItem jmi3=null;
	
 public tanke()
 { 
	 //�����˵��Ͳ˵�ѡ��
	 jmb=new JMenuBar();
	 jm1=new JMenu("��Ϸ(G)");
	 //���ÿ�ݷ�ʽ
	 jm1.setMnemonic('G');
	 
	 jmi1=new JMenuItem("��ʼ��Ϸ(N)");
	 jmi2=new JMenuItem("�˳���Ϸ(E)");
	 jmi3=new JMenuItem("�˳�������(S)");

	 //��jmi1���ע��
	 jmi1.addActionListener(this);
	 jmi1.setActionCommand("newgame");//������������
	 
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
			 //�����߳�
			 Thread t=new Thread(mp);
			 t.start();
			 //��ɾ���ɵ����
			 this.remove(ms);
			 
			 this.add(mp);//̹�������������
			 this.addKeyListener(mp);//ע�����
			 //��ʾ
			 this.setVisible(true);
		}else if(arg0.getActionCommand().equals("exit"))
		{
			//�û�������˳�ϵͳ�Ĳ˵�
			//������ٵ�������
			Recorder.setEts(mp.ets);
			Recorder.keepRecording();
			System.exit(0);
		}
		else if(arg0.getActionCommand().equals("save"))
		{
			//����Ϣ�������ļ���
			
		}
	}
}

//����һ���Լ������
//���ڻ�ͼ����ʾ������
class Mypanel extends JPanel implements KeyListener,Runnable{
	Mytank mt=null;
	//������˵�̹��
	Vector<enemytank> ets=new Vector<enemytank>();
	int ensize=5;
	
	//����ը������
	Vector<Bomb> bombs=new Vector<Bomb>();

	//��ͣ��ʾ
	boolean turnoff=false;
	int count=0;
	
	//����6��ͼƬ���ը��
	Image image1=null;
	Image image2=null;
	Image image3=null;
	Image image4=null;
	Image image5=null;
	Image image6=null;
	
	
    //for(int i=0;i<ensize;i++) {};
	//����JPanel�е�paint����
	//Graphics�ǻ�ͼ����Ҫ�࣬�������ڻ�ͼ��ʵ�ֻ�ͼ������
	public void paint(Graphics g) {//��д��paint��,g�൱��һ֧���ʣ�����Ҫ��ͬһ֧����
		super.paint(g);//���ø�����ɳ�ʼ��
		//���ƽ��棬������϶�����ͽ�������Ŷ�������ػ�
		g.fillRect(0, 0, 400, 300);//����һ��400*300�ľ��Σ���Ԥ������ɫ������������
		
		//������ʾ��Ϣ
		this.showInfo(g);
		
		//�����ҵ�̹��
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
				 //ɾ���ӵ�
				 mt.shotcont.remove(myshot);
			 }
		 }
		 
		 /*
		  * �������˵�̹��
		  */
		 
		 for(int i=0;i<ets.size();i++)
		 {
			 enemytank et = ets.get(i);
			 if(et.islive)
			 {
				 this.drawTank(et.getM_x(), et.getM_y(), g, et.getDirect(), et.getType());
				 //��������̹�˵��ӵ� 
				 for(int j=0;j<et.etshot.size();j++)
				 {
					 //ȡ���ӵ�
					 shot eshot=et.etshot.get(j);
					 if(eshot.islive)
					 {
						 g.draw3DRect(eshot.m_x, eshot.m_y, 1, 1, false);
					 }
				 }
			 }
		 }
		 
		 
		//System.out.println(ets.size());
		 
		 //����ը��
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
			 //������ֵ��С
			 b.lifeDown();
			 if(b.life==0)
			 {
				 bombs.remove(b);
			 }
		 }
		 }
	}
	//Graphics r=null;
	public Mypanel(){//�����Ĺ��캯���м����ҵ�̹��
		
		//��ʼ��ͼƬ
		//"images/jinshan.jpg"

		//��ȡ��¼
		Recorder.getRecording();
		 ensize=Recorder.getEnNum();
		//MediaTracker����
		MediaTracker mediaTracker = new MediaTracker(this);
		
		image1=Toolkit.getDefaultToolkit().getImage("./images/one.gif");;
		image2=Toolkit.getDefaultToolkit().getImage("./images/two.gif");
		image3=Toolkit.getDefaultToolkit().getImage("./images/three.gif");
		image4=Toolkit.getDefaultToolkit().getImage("./images/four.gif");
		image5=Toolkit.getDefaultToolkit().getImage("./images/five.gif");
		image6=Toolkit.getDefaultToolkit().getImage("./images/six.gif");
		//��ͼƬ��ӵ�ý��׷����
		//�ڶ��������ǿ���ȡͼ���ȴ����һ������id������ͼ��ʱ��Сid���нϸ�����
		//���idҲ��������ѯ��mediaTracker����ע��ͼ���״̬
		mediaTracker.addImage(image1, 1);
		mediaTracker.addImage(image2, 2);
		mediaTracker.addImage(image3, 3);
		mediaTracker.addImage(image4, 4);
		mediaTracker.addImage(image5, 5);
		mediaTracker.addImage(image6, 6);
		
		//���ͼƬ�Ƿ�����꣬���û���������
		mediaTracker.checkAll(true);

		//�����ҵ�̹��
		mt=new  Mytank(10,15);
		//�������˵�̹��
		for(int i=0;i<ensize;i++)
		{
			//��������̹�˵Ķ���
			enemytank et=new enemytank((i+1)*50+15,100);
			//���÷���
			et.setDirect(0);
			ets.add(et);
			//��panel�ĵ���̹�����������õ���̹��
			et.setEts(ets);
			
			//��������̹�˵��߳�
			Thread t=new Thread(et);
			t.start();
		}
	}
	
	 //��ʾ̹�˵���Ϣ
public void showInfo(Graphics g)
{
		 //������ʾ��Ϣ
		 this.drawTank(20, 320, g,0 , 0);
		 g.setColor(Color.red);
		 g.drawString(Recorder.getMyLife()+"", 40, 325);
		 this.drawTank(80, 320, g,0 , 1);
		 g.setColor(Color.red);
		 g.drawString(Recorder.getEnNum()+"", 100, 325);
		 
		 //�������
		 g.setColor(Color.black);
		 Font f=new Font("����",Font.BOLD,20);
		 g.setFont(f);
		 g.drawString("��ǰ�÷֣�", 130, 325);
		 g.drawString(Recorder.getGrade()+"", 230, 325);

}

	//�жϵ��˵�̹���Ƿ������
public void hitMe()
{
	//ȡ��ÿһ�����˵�̹��
	for(int i=0;i<this.ets.size();i++)
	{
		//ȡ��̹��
		enemytank et=ets.get(i);
		//ȡ��ÿһ���ӵ�
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

//�жϵ���̹���Ƿ��ӵ�����
public void hitTank(shot s,Tank et)
{
	//�жϸ�̹�˵ķ���
	switch(et.m_direct)
	{
	//�������̹�˵ķ��������ϻ�������
	case 0:
	case 2:
		if(s.m_x>(et.m_x-10) && s.m_x<(et.m_x+10)&&s.m_y>(et.m_y-15)&&s.m_y<(et.m_y+15))
		{
			//�����ӵ�����
			s.islive=false;
			//����̹������
			et.islive=false;
			//����һ��ը��������vector�й���
		    Bomb b=new Bomb(et.m_x-10,et.m_y-15);
			bombs.add(b);
			//�ų�����./images/one.gif
			AePlayWave aeplaywave=new AePlayWave("./images/bomb.wav");
			Thread t=new Thread(aeplaywave);
			t.start();
			//��̬��������һ�����˵�̹��
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
			//�����ӵ�����
			s.islive=false;
			//̹������
			et.islive=false;
			Bomb b=new Bomb(et.m_x-15,et.m_y-10);
			bombs.add(b);
			//�ų�����
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
 * ̹�˵�����
 * ̹�˵�����x,y 
 * ͼƬ�������ĸ����滭̹��
 * ̹�˵ķ���
 * ̹�˵�����
 */
	public void drawTank(int x,int y,Graphics g,int direct,int type)//��̹��
	{
		switch(type)//Ϳɫ
		{
		//������ɫ�ĺ��������¸��ǵģ�������ɫ��ֻ�Ḳ������������Ƶ�ͼ�ε���ɫ
		case 0: g.setColor(Color.cyan);break;
		case 1:g.setColor(Color.yellow);break;
		}
		//�жϷ���
				switch(direct)
				{
				case 0://�����ҵ�̹��,����
					//��������ľ���
					
					g.fill3DRect(x-10, y-15, 5, 30,false);
					//g.draw3DRect(x, y, width, height, raised);//raised�Ƿ�ͻ����ͻ����true����false 
					g.fill3DRect(x+5, y-15, 5, 30,false);
					//�����м�ľ���
					g.fill3DRect(x-5, y-10, 10, 20,false);
					//��Բ��
					g.fillOval(x-6, y-7, 10, 10);
					//���ڹ�
					g.drawLine(x-1, y,x-1, y-15);
					break;
				case 1:
					//����
					g.fill3DRect(x-15, y-10, 30, 5,false);
					//g.draw3DRect(x, y, width, height, raised);//raised�Ƿ�ͻ����ͻ����true����false 
					g.fill3DRect(x-15, y+5, 30, 5,false);
					//�����м�ľ���
					g.fill3DRect(x-10, y-5, 20, 10,false);
					//��Բ��
					g.fillOval(x-6, y-6, 10, 10);
					//���ڹ�
					g.drawLine(x, y-1,x+15, y-1);
					break;
				case 2://����
					g.fill3DRect(x-10, y-15, 5, 30,false);
					//g.draw3DRect(x, y, width, height, raised);//raised�Ƿ�ͻ����ͻ����true����false 
					g.fill3DRect(x+5, y-15, 5, 30,false);
					//�����м�ľ���
					g.fill3DRect(x-5, y-10, 10, 20,false);
					//��Բ��
					g.fillOval(x-6, y-7, 10, 10);
					//���ڹ�
					g.drawLine(x-1, y,x-1, y+15);
					break;
				case 3://����
					g.fill3DRect(x-15, y-10, 30, 5,false);
					//g.draw3DRect(x, y, width, height, raised);//raised�Ƿ�ͻ����ͻ����true����false 
					g.fill3DRect(x-15, y+5, 30, 5,false);
					//�����м�ľ���
					g.fill3DRect(x-10, y-5, 20, 10,false);
					//��Բ��
					g.fillOval(x-6, y-6, 10, 10);
					//���ڹ�
					g.drawLine(x, y-1,x-15, y-1);
					break;
				}
	}





@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	if(e.getKeyCode()==KeyEvent.VK_W && !turnoff)
	{
		//�����ҵ�̹�˷���
		mt.setDirect(0);
		//mt.moveUp();
		mt.bU=true;
		//System.out.println("�ı��˷���");
	}
	else if(e.getKeyCode()==KeyEvent.VK_D && !turnoff)
	{
		//����
		this.mt.setDirect(1);
		//mt.moveRight();
		mt.bR=true;
	}
	else if(e.getKeyCode()==KeyEvent.VK_S && !turnoff)
	{
		//����
		this.mt.setDirect(2);
		//mt.moveDown();
		mt.bD=true;
	}
	else if(e.getKeyCode()==KeyEvent.VK_A && !turnoff)
	{
		//����
		this.mt.setDirect(3);
	    //mt.moveLeft();
		mt.bL=true;
	}
	
	//��Ұ��¿����
	if(e.getKeyCode()==KeyEvent.VK_J && !turnoff)
	{
		if(this.mt.shotcont.size()<6)
		{
			this.mt.shotEnemy();
		}
	}
	//��ͣ
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
	//���»���Panel
	this.repaint();
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	if(e.getKeyCode()==KeyEvent.VK_W && !turnoff)
	{
		//�����ҵ�̹�˷���
		//mt.setDirect(0);
		//mt.moveUp();
		mt.bU=false;
		//System.out.println("�ı��˷���");
	}
	else if(e.getKeyCode()==KeyEvent.VK_D && !turnoff)
	{
		//����
		//this.mt.setDirect(1);
		//mt.moveRight();
	   mt.bR=false;
	}
	else if(e.getKeyCode()==KeyEvent.VK_S && !turnoff)
	{
		//����
		//this.mt.setDirect(2);
		//mt.moveDown();
		mt.bD=false;
	}
	else if(e.getKeyCode()==KeyEvent.VK_A && !turnoff)
	{
		//����
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
public void run() {//��Ļ��Ҫһ���߳�����д
	// TODO Auto-generated method stub
	while(true) {
		try {
			Thread.sleep(35);
		}catch(Exception e) {
			e.printStackTrace();
		}
     if(!turnoff) {
		//ÿ��100ms�ػ�һ��

		//�����ж��ӵ��Ƿ������̹��
		for(int i=0;i<mt.shotcont.size();i++)
		{
			//ȡ���ӵ�
			shot myshot=mt.shotcont.get(i);
			//�ж��ӵ��Ƿ񻹻���
			if(myshot.islive)
			{
				//ȡ��ÿ��̹�ˣ������ж�
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

		//�жϵ��˵�̹���Ƿ��������
		this.hitMe();
		this.repaint();

	}
	}
	
}
}


