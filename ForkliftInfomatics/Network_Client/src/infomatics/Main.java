package infomatics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	static ExecutorService executorService = Executors.newFixedThreadPool(5);

	public static void main(String[] args) throws Exception {
		
		SerialServer sc = new SerialServer("COM11");
		
//		Runnable r = new Server (7777);
//		executorService.submit(r);
//		//ECU �� TCP/IP Server
//		//�ø���������� �޾ƿ��� �ʿ����
	
		String address = "70.12.226.134";
		Client client = new Client(address,8888);
		//Tablet Server �� Client
		

	}
}
