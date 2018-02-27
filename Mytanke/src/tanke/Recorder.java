package tanke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

//��¼�࣬������Ϸ�е���Ϣ

public class Recorder {
	//��¼���˵�����
	private static int enNum = 5;
	// �����ҿ����õ���
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
	
	//��ȫ������Ϣ��¼����
	public static void keepRecording()
	{
		try {
			//����
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(enNum+"\r\n");//����
			
			//���浱���ŵ�̹�˵�����
			for(int i=0;i<ets.size();i++)
			{
				//ȡ��̹��
				enemytank et=ets.get(i);
				if(et.islive)
				{
					String record=et.m_x+" "+et.m_y+" "+et.m_direct;
					//д��
					bw.write(record+"\r\n");//�س�����
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			//�ر���
			try {
				//���ȹ�
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
			enNum=Integer.parseInt(n);//ת����int��
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
	//���ٵ�������
	public static void reduceEnNum()
	//��̬������ͬһ�����п��ö����ó�Ϊ����ĳ�Ա����
	{
		enNum--;
	}
	//�����ҵ�̹������
	public static void reduceMtNum()
	{
		myLife--;
	}
	public static void addGrade()
	{
		grade+=100;
	}
}
