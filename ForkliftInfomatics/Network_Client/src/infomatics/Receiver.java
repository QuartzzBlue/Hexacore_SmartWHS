package infomatics;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

import msg.Msg;
import msg.Task;

class Receiver implements Runnable {

	InputStream is;
	ObjectInputStream ois;

	OutputStream os;
	ObjectOutputStream oos;

	Socket socket;
	static int status = 2; // waiting
	static Task task;

	public Receiver() {
	}

	public Receiver(Socket socket) throws IOException {
		this.socket = socket;
		is = socket.getInputStream();
		ois = new ObjectInputStream(is);
	}

	@Override
	public void run() {

		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Main.executorService;
		int poolSize = threadPoolExecutor.getPoolSize();// ������ Ǯ ������ ���
		String threadName = Thread.currentThread().getName();// ������ Ǯ�� �ִ� �ش� ������ �̸� ���

		while (ois != null) {
			Msg msg = null;
			try {

				System.out.println("Receiver [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: " + threadName);
				//msg = (Msg) ois.readObject();
				
				
				
				//Test
				Thread.sleep(5000);
//				msg = new Msg("tabletServer","ForkliftInfomatics");
//				msg.setTask(1, "PHONE", 1, 3, 20);
//				task = msg.getTask();
//				
				//
				
//				if (msg.getTask() != null) {
//					System.out.println("Received Task - " + "srcid: " + msg.getSrcID() + " dstnid : " + msg.getDstnID()
//							+ " IO : " + msg.getTask().getIo() + " LocX : " + msg.getTask().getLocX() + " LocY : "
//							+ msg.getTask().getLocY() + " itemName : " + msg.getTask().getName() + " Qty : "
//							+ msg.getTask().getQty());
//
//					SerialWrite.sendId = "10000000";
////					SerialWrite.data = msg.getTask().getLocX()+""+msg.getTask().getLocY();
//					Runnable r = new SerialWrite(msg.getTask().getLocX()+""+msg.getTask().getLocY());
//					Main.executorService.submit(r);
//					
//
//				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Server Die");
				ActiveConnection.ipToOos.remove(socket.getInetAddress().toString());
				// value ������ key �� ã��
				for (String id : ActiveConnection.idToIp.keySet()) {
					if (socket.getInetAddress().toString().equals(ActiveConnection.idToIp.get(id))) {
						ActiveConnection.idToIp.remove(id);
					}
				}
				System.out.println("Disconnected : " + socket.getInetAddress().toString());
				System.out.println("���� �� : " + ActiveConnection.ipToOos.size());
				break;
			}
		}

		try {
			if (ois != null) {
				ois.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
