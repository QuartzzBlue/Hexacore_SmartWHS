package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ThreadPoolExecutor;

import msg.Msg;

public class Sender implements Runnable {
	
	 OutputStream os;
     ObjectOutputStream oos;
     Msg msg;
     
	
	public Sender () {
		
	}
	
	public Sender(Msg msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		
		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
		int poolSize = threadPoolExecutor.getPoolSize();//������ Ǯ ������ ���
		String threadName = Thread.currentThread().getName();//������ Ǯ�� �ִ� �ش� ������ �̸� ���
	
		System.out.println("Sender [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: "+threadName);
          
		
		System.out.println("srcip : "+msg.getSrcIP()+", srcid : "+msg.getSrcID()+", dstnip : "+msg.getDstnIP()
		+", dstnid : "+msg.getDstnID()+", content : " + msg.getForkLift().getBattery());
		
		if(ActiveConnection.idToIp.containsKey(msg.getDstnID())) {
			String ip = ActiveConnection.idToIp.get(msg.getDstnID());	
			try {
				ActiveConnection.ipToOos.get(ip).writeObject(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	

}
