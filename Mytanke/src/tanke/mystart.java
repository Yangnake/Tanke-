package tanke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class mystart  extends JPanel implements Runnable{
	int times=0;
	public void paint(Graphics g)//��дpaint����
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//��ʾ��Ϣ
		if(times%2==0) {
		g.setColor(Color.yellow);
		Font myfont=new Font("������κ",Font.BOLD,30);//���壬�ֺ�30
		g.setFont(myfont);
		g.drawString("��һ��", 140, 30);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//����0.5��
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
