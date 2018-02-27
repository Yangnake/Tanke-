package test;
import javax.swing.*;
import java.awt.*;

public class test1 extends JFrame {

	 JSplitPane jsp;
	 JList jList;
	 JLabel jll;
	 public  test1()//构造函数
	 {
		 //创件组建
		 String []words= {"boy","girl","bird"};
		 jList=new JList(words);//列表
		 jll=new JLabel(new ImageIcon("images/jinshan.jpg"));//jLabel可以加图片
		 //拆分窗格
		 jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jList,jll);
		 //可以变化,可以伸缩变化
		 jsp.setOneTouchExpandable(true);//恻栏可以伸缩（开关）
		 //设置布局管理器
		 //添加组件
		 this.add(jsp);
		// this.add(jList);
		 this.setSize(400, 300);
		 this.setLocation(200, 300);
		 this.setDefaultCloseOperation(EXIT_ON_CLOSE);//指定为水平拆分
		 this.setVisible(true);
	 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1 myt=new test1();
		 
		

	}

}
