package client;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class SerialWrite implements Runnable {

	String data;
	int temperature = 50;
	String temperatureStr;
	static boolean flag = false;
	int flag2 = -1;

	// ��� �����ʹ� String
	public SerialWrite() {
		this.data = ":G11A9\r";
	}

	@Override
	public void run() {

		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
		int poolSize = threadPoolExecutor.getPoolSize();// ������ Ǯ ������ ���
		String threadName = Thread.currentThread().getName();// ������ Ǯ�� �ִ� �ش� ������ �̸� ���	

		while (SerialClient.out != null) {

			if(!data.equals(":G11A9\r")) {
				
				String tmp = SerialClient.id + SerialClient.data;

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (temperature < 50) {
					flag2 = 1;
				} else if (temperature > 100)
					flag2 = -1;

				temperature += (int) (Math.random() * 2) * flag2;

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
			}
			
			byte[] outData = data.getBytes();
			try {
				SerialClient.out.write(outData);// �̷��� data�� CAN Network Area�� ���.
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			data=SerialClient.id + SerialClient.data;
			
		} // While
			

	}

}
