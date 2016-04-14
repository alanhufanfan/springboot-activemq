package spring.jms.activemq.test;

import java.util.Date;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.junit.Test;
import org.springframework.jms.core.MessageCreator;

import spring.jms.activemq.bean.User;

public class QueueSendTest extends BaseTest {

	@Test
	public void sendTextMessage() {
		this.jmsTemplate.send(this.queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("我爱北京天啊我那么");
			}
		});
	}

	@Test
	public void sendMapMessage() {
		this.jmsTemplate.send(this.queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("name", "andy");
				mapMessage.setInt("age", 11);
				return mapMessage;
			}
		});
	}

	@Test
	public void sendObjectMessage() {
		this.jmsTemplate.send(this.queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				User user = new User(1, "aaa", 11, "sh", new Date());
				return session.createObjectMessage(user);
			}
		});
	}

	@Test
	public void sendBytesMessage() {
		this.jmsTemplate.send(this.queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage();
				bytesMessage.writeUTF("this is a bytesMessage");
				return bytesMessage;
			}
		});
	}

	@Test
	public void sendStreamMessage() {
		this.jmsTemplate.send(this.queue, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				StreamMessage streamMessage = session.createStreamMessage();
				streamMessage.writeString("this is a streamMessage");
				return streamMessage;
			}
		});
	}
}
