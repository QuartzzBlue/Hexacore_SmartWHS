package client;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class SerialWrite implements Runnable {

	String data;
	int temperature = 0;
	String temperatureStr;
	static boolean flag = false;

	// ��� �����ʹ� String
	public SerialWrite() {
		this.data = ":G11A9\r";
		flag = true;

	}
	
	@Override
	public void run() {

		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
		int poolSize = threadPoolExecutor.getPoolSize();// ������ Ǯ ������ ���
		String threadName = Thread.currentThread().getName();// ������ Ǯ�� �ִ� �ش� ������ �̸� ���

		if (flag) {
			System.out.println("Begin : " + data);
			byte[] outData = data.getBytes();
			try {
				SerialClient.out.write(outData);// �̷��� data�� CAN Network Area�� ���.
			} catch (IOException e) {
				e.printStackTrace();
			}

			flag = false;

		} 
			while (SerialClient.out != null && !flag) {

				System.out.println("SerialWrite [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: " + threadName);

				String tmp = SerialClient.id + SerialClient.data;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				// x,y ��ġ ���̰� 4���ڰ� �ǰ�
				if (temperature < 10) {
					temperatureStr = "0" + temperature;
				} else {
					temperatureStr = temperature + "";
				}
				
				
				int temperatureStrlen = temperatureStr.length();

				tmp = tmp.substring(0, tmp.length() - temperatureStrlen) + temperatureStr;

				tmp = tmp.toUpperCase();
				SerialClient.msg = "W28" + tmp;
				// W28 00000000 0000000000000000

				// checkSum ���
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
					SerialClient.out.write(outData);// �̷��� data�� CAN Network Area�� ���.
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // run method
		
	}

}
