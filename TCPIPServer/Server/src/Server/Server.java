package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements Runnable {
	  // ExecutorService �������̽� ������ü Executors �����޼��带 ���� �ִ� ������ ������ 5�� ������ Ǯ ����
	
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
		System.out.println("Server Start (Server ��ü ����)");
		
	}

	@Override
	public void run() {
		while (SERVER_RUNNING) {
            
			Socket socket = null;
			try {
				
				socket = serverSocket.accept();
				System.out.println("new Client Accepted : "+socket.getInetAddress());
				
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
				int poolSize = threadPoolExecutor.getPoolSize();//������ Ǯ ������ ���
				String threadName = Thread.currentThread().getName();//������ Ǯ�� �ִ� �ش� ������ �̸� ���
	            
	            System.out.println(" Server [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: "+threadName);
				
				
				Runnable r = new Receiver(socket);
				//Thread ReceiverThread = new Thread(r);
				Main.executorService.submit(r);
				//ReceiverThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}


