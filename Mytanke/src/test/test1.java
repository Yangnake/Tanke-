package test;
import javax.swing.*;
import java.awt.*;

public class test1 extends JFrame {

	 JSplitPane jsp;
	 JList jList;
	 JLabel jll;
	 public  test1()//���캯��
	 {
		 //�����齨
		 String []words= {"boy","girl","bird"};
		 jList=new JList(words);//�б�
		 jll=new JLabel(new ImageIcon("images/jinshan.jpg"));//jLabel���Լ�ͼƬ
		 //��ִ���
		 jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jList,jll);
		 //���Ա仯,���������仯
		 jsp.setOneTouchExpandable(true);//�����������������أ�
		 //���ò��ֹ�����
		 //������
		 this.add(jsp);
		// this.add(jList);
		 this.setSize(400, 300);
		 this.setLocation(200, 300);
		 this.setDefaultCloseOperation(EXIT_ON_CLOSE);//ָ��Ϊˮƽ���
		 this.setVisible(true);
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1 myt=new test1();
		 
		

	}

}
