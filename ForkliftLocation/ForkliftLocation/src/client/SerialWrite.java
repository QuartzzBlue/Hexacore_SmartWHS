package client;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class SerialWrite implements Runnable {

	String data;
	static int forkliftLocX = 12;
	static int forkliftLocY = 0;
	String forkliftLocXStr;
	String forkliftLocYStr;
	static boolean flag = false;

	// ��� �����ʹ� String
	public SerialWrite() {
		this.data = ":G11A9\r";
		flag = true;

	}

//	public SerialWrite(String msg) {
//		this.data = convertData(msg);
//	}

//	public String convertData(String msg) {
//		int num = (int) (Math.random() * 100);
//		if (num < 10) {
//			msg += ("0" + num);
//		} else {
//			msg += num;
//		}
//		msg = msg.toUpperCase();
//		msg = "W28" + msg;
//		// W28 00000000 0000000000000000
//		char[] c = msg.toCharArray();
//		int checkSum = 0;
//		for (char ch : c) {
//			checkSum += ch;
//		}
//		checkSum = (checkSum & 0xFF);
//		String result = ":";
//		result += msg + Integer.toHexString(checkSum).toUpperCase() + "\r";
//		System.out.println("result : " + result);
//		return result;
//	}

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

				// ������ stock �� x ��ǥ�� y��ǥ�� ���� forklift �� ��ġ�� ����Ǵ� ���� ����
//			if(SerialClient.stockLocX!=0) {
//				
//			}
//			
//			if(SerialClient.stockLocY!=0) {
//				if(forkliftLocY!=SerialClient.stockLocY){
//					forkliftLocY++;
//				}
//			}

				// Web ���� �����ֱ� ���� Test ��. x,y �� �����ư��� 1�� ����
				
//				if (flag) {
//
//					forkliftLocX++;
//
//					flag = false;
//				} else {
//					forkliftLocY++;
//					flag = true;
//				}

				// x,y ��ġ ���̰� 4���ڰ� �ǰ�
				if (forkliftLocX < 10) {
					forkliftLocXStr = "0" + forkliftLocX;
				} else {
					forkliftLocXStr = forkliftLocX + "";
				}

				if (forkliftLocY < 10) {
					forkliftLocYStr = "0" + forkliftLocY;
				} else {
					forkliftLocYStr = forkliftLocY + "";
				}

				String locationStr = forkliftLocXStr + forkliftLocYStr;
				int locationStrlen = locationStr.length();

				tmp = tmp.substring(0, tmp.length() - locationStrlen) + locationStr;

				System.out.println("tmp : " + tmp);

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
