//package com.forklift;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import com.google.gson.Gson;
//
//import msg.ForkLift;
//
//public class ForkliftHandler extends TextWebSocketHandler {
//	WebSocketSession forkliftSession;
//	Map<String, WebSocketSession> map = new HashMap<>();
//	Gson gson = new Gson();
//	
//	public void checkWebSocket(ArrayList<String> removeList) throws Exception {
//		Iterator<String> keys = map.keySet().iterator();
//		
//		while (keys.hasNext()) {
//			String key = keys.next();
//			WebSocketSession ws = map.get(key);
//			
//			// ����Ǿ� ���� ���� ������ ����
//			if(!ws.isOpen())
//				removeList.add(ws.getId());
//		}
//	}
//
//	// ���� �ڿ� ����
//	public void closeWebSocket(ArrayList<String> removeList) throws Exception {
//		for( String removeWebSocketId : removeList) {
//			System.out.println("������ �ݽ��ϴ�  > " + removeWebSocketId);
//			map.remove(removeWebSocketId);
//		}
//	}
//	
//	// �޽��� ������
//	public void sendToWebSocket(ArrayList<String> removeList, String sendMsg) throws Exception {
//		System.out.println("�������� ���� �޽��� ���� > " + sendMsg);
//		
//		Iterator<String> keys = map.keySet().iterator();
//		
//		while(keys.hasNext()) {
//			String key = keys.next();
//			WebSocketSession ws = map.get(key);
//			
//			if(ws.isOpen())
//				ws.sendMessage(new TextMessage(sendMsg));
//			else
//				removeList.add(ws.getId());
//		}
//	}	
//
//	
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		super.afterConnectionEstablished(forkliftSession);
//		
//		System.out.println("������ ����� > " + session);
//	}
//	
//	@Override
//	protected void handleTextMessage(WebSocketSession session, TextMessage msg) {
//		String rcvMsg = msg.getPayload();
//		ForkLift handleMsg = gson.fromJson(rcvMsg, ForkLift.class);
//		
//		System.out.println("���̷ε� > " + rcvMsg);
//	}
//	
//	
//}
