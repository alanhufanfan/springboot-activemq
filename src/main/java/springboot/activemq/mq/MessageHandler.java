package springboot.activemq.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

	@JmsListener(destination = "activemq.queue")
	public void receiveQueue(@Payload String text) {
		System.out.println(text);
	}
}
