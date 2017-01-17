package sapp.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketHandler {
	
	void afterConnectionEstablished(WebSocketSession session) throws Exception;

	void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception;

	void handleTransportError(WebSocketSession session, Throwable exception) throws Exception;

	void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception;

	boolean supportsPartialMessages();
	
}