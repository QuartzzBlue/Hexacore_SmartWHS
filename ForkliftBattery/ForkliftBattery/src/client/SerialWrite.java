package client;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class SerialWrite implements Runnable {

	String data;
	int battery=999;
	static int d=-2;

	// ��� �����ʹ� String
	public SerialWrite() {
		this.data = ":G11A9\r";
	}
	
	
	@Override
	public void run() {
		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
		int poolSize = threadPoolExecutor.getPoolSize();// ������ Ǯ ������ ���
		String threadName = Thread.currentThread().getName();// ������ Ǯ�� �ִ� �ش� ������ �̸� ���
		

		while (SerialClient.out != null ) {
			
			System.out.println("SerialWrite [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: " + threadName);
			
			String tmp = SerialClient.id+SerialClient.data;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			String batteryStr = battery+"";
			int batteryLen = batteryStr.length();
	
			tmp = tmp.substring(0, tmp.length()-batteryLen)+batteryStr;
				
			battery+=d;
			
			if(battery >= 999) {
				battery = 999;
			}
			
			tmp = tmp.toUpperCase();
			SerialClient.msg = "W28" + tmp;
			// W28 00000000 0000000000000000
			
			//checkSum ���
			char[] c = SerialClient.msg.toCharArray();
			int checkSum = 0;
			for (char ch : c) {
				checkSum += ch;
			}
			checkSum = (checkSum & 0xFF);
			
			String result = ":";
			result += SerialClient.msg + Integer.toHexString(checkSum).toUpperCase() + "\r";
			System.out.println("result : " + result);
			this.data = result;

			byte[] outData = data.getBytes();
			try {
				SerialClient.out.write(outData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // run method
	}

}
