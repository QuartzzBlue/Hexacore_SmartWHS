package infomatics;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

import msg.Msg;

class Receiver implements Runnable {

	InputStream is;
	ObjectInputStream ois;

	OutputStream os;
	ObjectOutputStream oos;

	Socket socket;
	static int status = 2; // waiting

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

		// Test �ڵ�
		Msg msg = new Msg("tabletServer", "ForkliftInfomatics");
		msg.setTask(1, "product", 3, 10, 20);

		if (msg.getTask() != null) {
			status = 0; // working
			String str = "";
			if (msg.getTask().getLocX() < 10) {
				str += "0" + msg.getTask().getLocX();
			} else {
				str += msg.getTask().getLocX() + "";
			}
			if (msg.getTask().getLocY() < 10) {
				str += "0" + msg.getTask().getLocY();
			} else {
				str += msg.getTask().getLocY() + "";
			}
			Runnable r = new SerialWrite(str);
			Main.executorService.submit(r);
		}

//		while (ois != null) {
//			Msg msg = null;
//			try {
//				System.out.println("Receiver [�� ������ ����:" + poolSize + "] �۾� ������ �̸�: "+threadName);
//				msg = (Msg) ois.readObject();
//				
//				System.out.println("Received Task : "+"srcid: "+msg.getSrcID()+"dntnid : " + msg.getDstnID()+"IO : " +
//				msg.getTask().getIo() + "LocX : "+ msg.getTask().getLocX()+"LocY : " + msg.getTask().getLocY()+"itemName : "+ 
//						msg.getTask().getName()+"Qty : "+ msg.getTask().getQty());
//				
//				if(msg.getTask()!=null) {
//					status = 0; //working
//					String str="";
//					if(msg.getTask().getLocX()<10) {
//						str+="0"+msg.getTask().getLocX();
//					}else {
//						str+=msg.getTask().getLocX()+"";
//					}
//					if(msg.getTask().getLocY()<10) {
//						str+="0"+msg.getTask().getLocY();
//					}else {
//						str+=msg.getTask().getLocY()+"";
//					}
//					Runnable r = new SerialWrite(str);
//					Main.executorService.submit(r);
//				}
//				
//				
//
//			} catch (Exception e) {
//				System.out.println("Server Die");
//				ActiveConnection.ipToOos.remove(socket.getInetAddress().toString());
//				//value ������ key �� ã��
//				for(String id : ActiveConnection.idToIp.keySet()) {
//					if(socket.getInetAddress().toString().equals(ActiveConnection.idToIp.get(id))) {
//						ActiveConnection.idToIp.remove(id);
//					}		
//				}
//				System.out.println("Disconnected : " + socket.getInetAddress().toString());
//				System.out.println("���� �� : " + ActiveConnection.ipToOos.size());
//				break;
//			}
//		}

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
