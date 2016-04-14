package spring.jms.activemq.test;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import spring.jms.activemq.config.ActiveMQConfiguration;

@ContextConfiguration(classes = ActiveMQConfiguration.class)
public class BaseTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected JmsTemplate jmsTemplate;

	@Resource(name = "queue")
	protected Destination queue;

	@Resource(name = "topic")
	protected Destination topic;
}
