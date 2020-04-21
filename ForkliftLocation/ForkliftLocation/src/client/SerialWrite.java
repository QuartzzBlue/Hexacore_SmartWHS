package client;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

public class SerialWrite implements Runnable {

	String data;
	static int forkliftLocX = 11;
	static int forkliftLocY = 13;
	String forkliftLocXStr;
	String forkliftLocYStr;
	static boolean back = false;
	static boolean flag = false;
	static boolean done = false;


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

			System.out.println("SerialWrite [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: " + threadName);

			String tmp = SerialClient.id + SerialClient.data;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			// ������ stock �� x ��ǥ�� y��ǥ�� ���� forklift �� ��ġ�� ����Ǵ� ���� ����
			if (SerialClient.stockLocX % 4 == 2 && !done) { // ���� ���ʿ� ��ġ

				if (!back) {
					if (forkliftLocX != SerialClient.stockLocX - 2) {
						forkliftLocX--;
						
					} else if (forkliftLocY != SerialClient.stockLocY) {
						forkliftLocY--;
					
					} else if (forkliftLocY == SerialClient.stockLocY && forkliftLocX == SerialClient.stockLocX - 2) {
						forkliftLocX++;
						
						back = true;
					}
				}

				else {

					if (forkliftLocY != 12) {
						forkliftLocY++;
					} else if (forkliftLocX != 11) {
						forkliftLocX++;
					} else if (forkliftLocY == 12 && forkliftLocX == 11) {
						forkliftLocY = 13;
						SerialClient.id = "14000005";
						done = true;
					}

				}

			} else if (SerialClient.stockLocX %4==3 && !done) { // ���� �����ʿ� ��ġ
				if (!back) {
					if (forkliftLocX < SerialClient.stockLocX + 1) {
						if(!flag) {
							forkliftLocY--;
							flag = true;
						}
						else forkliftLocX++;
						
					}else if (forkliftLocX > SerialClient.stockLocX+1) {
						forkliftLocX--;
					}
					
					else if (forkliftLocY != SerialClient.stockLocY) {
						forkliftLocY--;
					
					} else if (forkliftLocY == SerialClient.stockLocY && forkliftLocX == SerialClient.stockLocX +1) {
						back = true;
					}
				}

				else {

					if (forkliftLocY != 13) {
						forkliftLocY++;
					} else if (forkliftLocX < 11) {
						forkliftLocX++;
					} else if (forkliftLocY>11) {
						forkliftLocX--;
					}
					
					if (forkliftLocY == 13 && forkliftLocX == 11) {
						done = true;
					}

				}
			}
		

			System.out.println(forkliftLocX);
			System.out.println(forkliftLocY);

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
				SerialClient.out.write(outData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
