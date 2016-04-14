package spring.jms.activemq.config;

import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import spring.jms.activemq.listener.NotifyMessageListener;

@Configuration
public class ActiveMQConfiguration {

	@Bean(name = "connectionFactory")
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("system", "manager",
				"tcp://192.168.137.129:61616");
		connectionFactory.setTrustedPackages(Arrays.asList("spring.jms.activemq.bean", "java.util", "java.lang"));
		return connectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
		cachingConnectionFactory.setSessionCacheSize(10);
		return cachingConnectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(connectionFactory());
	}

	@Bean(name = "queue")
	public ActiveMQQueue queue() {
		return new ActiveMQQueue("activemq.message.queue");
	}

	@Bean(name = "topic")
	public ActiveMQTopic topic() {
		return new ActiveMQTopic("activemq.message.topic");
	}

	@Bean(name = "queueContainer")
	public DefaultMessageListenerContainer queueContainer() {
		DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory());
		listenerContainer.setDestination(queue());
		listenerContainer.setConcurrentConsumers(10);
		listenerContainer.setSessionTransacted(true);
		listenerContainer.setMessageListener(notifyMessageListener());
		return listenerContainer;
	}

	@Bean(name = "topicContainer")
	public DefaultMessageListenerContainer topicContainer() {
		DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory());
		listenerContainer.setDestination(topic());
		listenerContainer.setSessionTransacted(true);
		listenerContainer.setMessageListener(notifyMessageListener());
		return listenerContainer;
	}

	@Bean
	public NotifyMessageListener notifyMessageListener() {
		return new NotifyMessageListener();
	}
}
