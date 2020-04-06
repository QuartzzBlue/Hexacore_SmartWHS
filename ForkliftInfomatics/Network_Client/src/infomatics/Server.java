//�ø���������� ������ ���� ��

package infomatics;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Runnable {

	ServerSocket serverSocket;
	boolean SERVER_RUNNING = true;
	
	public Server() {
		
	}
	
	public Server(int port) {
		
		
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("TCP/IP Server Start");
		
	}

	@Override
	public void run() {
		while (SERVER_RUNNING) {
            
			Socket socket = null;
			try {
				
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
				int poolSize = threadPoolExecutor.getPoolSize();//������ Ǯ ������ ���
				String threadName = Thread.currentThread().getName();//������ Ǯ�� �ִ� �ش� ������ �̸� ���
	            
	            System.out.println("Server [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: "+threadName);
				
				socket = serverSocket.accept();
				System.out.println("new ECU Accepted : "+socket.getInetAddress());
				
				Runnable r = new Receiver(socket);
				Main.executorService.submit(r);
				
				//Runnable r2 = new Sender(Client.socket);
				//Main.executorService.submit(r2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


