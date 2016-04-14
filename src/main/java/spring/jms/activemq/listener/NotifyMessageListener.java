package spring.jms.activemq.listener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotifyMessageListener implements MessageListener {

	private static final Logger logger = LogManager.getLogger(NotifyMessageListener.class);

	@Override
	public void onMessage(Message message) {
		logger.info("receive message...");
		try {
			if (message instanceof TextMessage) {
				onTextMessage(message);
			} else if (message instanceof MapMessage) {
				onMapMessage(message);
			} else if (message instanceof ObjectMessage) {
				onObjectMessage(message);
			} else if (message instanceof BytesMessage) {
				onBytesMessage(message);
			} else if (message instanceof StreamMessage) {
				onStreamMessage(message);
			} else {
				throw new IllegalArgumentException("Unknown Message");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void onTextMessage(Message message) throws JMSException {
		TextMessage textMessage = (TextMessage) message;
		logger.info("TextMessage: " + textMessage.getText());
	}

	private void onMapMessage(Message message) throws JMSException {
		MapMessage mapMessage = (MapMessage) message;
		List<String> list = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Enumeration<String> names = mapMessage.getMapNames();
		while (names.hasMoreElements()) {
			list.add(names.nextElement());
		}
		logger.info("MapMessage: [ " + StringUtils.join(list, " , ") + " ]");
	}

	private void onBytesMessage(Message message) throws JMSException {
		BytesMessage bytesMessage = (BytesMessage) message;
		logger.info("BytesMessage: " + bytesMessage.readUTF());
	}

	private void onObjectMessage(Message message) throws JMSException {
		ObjectMessage objectMessage = (ObjectMessage) message;
		logger.info("ObjectMessage: " + objectMessage.getObject());
	}

	private void onStreamMessage(Message message) throws JMSException {
		StreamMessage streamMessage = (StreamMessage) message;
		logger.info("StreamMessage: " + streamMessage.readObject());
	}

}
