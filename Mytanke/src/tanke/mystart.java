package tanke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class mystart  extends JPanel implements Runnable{
	int times=0;
	public void paint(Graphics g)//重写paint函数
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//提示信息
		if(times%2==0) {
		g.setColor(Color.yellow);
		Font myfont=new Font("华文新魏",Font.BOLD,30);//粗体，字号30
		g.setFont(myfont);
		g.drawString("第一关", 140, 30);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//休眠0.5秒
			try {
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			times++;
			repaint();
		}
	}
}
