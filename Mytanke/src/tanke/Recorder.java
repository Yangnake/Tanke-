package tanke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

//记录类，保存游戏中的信息

public class Recorder {
	//记录敌人的数量
	private static int enNum = 5;
	// 设置我可以用的人
	private static int myLife = 1;
	
	private static int grade=0;
	
	private static Vector<enemytank> ets=new Vector<enemytank>();
	
	
	public static Vector<enemytank> getEts() {
		return ets;
	}

	public static void setEts(Vector<enemytank> ets) {
		Recorder.ets = ets;
	}
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	//把全部的信息记录下来
	public static void keepRecording()
	{
		try {
			//创建
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(enNum+"\r\n");//换行
			
			//保存当活着的坦克的坐标
			for(int i=0;i<ets.size();i++)
			{
				//取出坦克
				enemytank et=ets.get(i);
				if(et.islive)
				{
					String record=et.m_x+" "+et.m_y+" "+et.m_direct;
					//写入
					bw.write(record+"\r\n");//回车换行
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			//关闭流
			try {
				//后开先关
				bw.close();
				fw.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void getRecording()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			if(n!="")
			enNum=Integer.parseInt(n);//转换成int型
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {
				br.close();
				fr.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static int getGrade() {
		return grade;
	}
	public static void setGrade(int grade) {
		Recorder.grade = grade;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	//减少敌人人数
	public static void reduceEnNum()
	//静态方法在同一个包中可用而不用成为该类的成员函数
	{
		enNum--;
	}
	//减少我的坦克数量
	public static void reduceMtNum()
	{
		myLife--;
	}
	public static void addGrade()
	{
		grade+=100;
	}
}
