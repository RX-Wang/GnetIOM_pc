package com.gnet.utils;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;

import com.gnet.message.PropertiesUtil;

public class QueueSend {
	private static Logger logger = Logger.getLogger(QueueSend.class);
	private static PooledConnectionFactory poolFactory;
	static String messageurl = "";
	static String topic = "";
	static {
		try {
			messageurl = PropertiesUtil.getKeyValue("messageip");
			topic = PropertiesUtil.getKeyValue("topic1");
			System.out.println("messageurl==" + messageurl);
			System.out.println("topic==" + topic);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MQUtils初始化异常" + e.getMessage());
		}

	}

	public QueueSend() {
	}

	/**
	 * 获取单例的PooledConnectionFactory
	 * 
	 * @return
	 * 
	 */
	private synchronized static PooledConnectionFactory getPooledConnectionFactory() {
		logger.info("getPooledConnectionFactory");
		if (poolFactory != null)
			return poolFactory;
		logger.info("getPooledConnectionFactory create new");
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, messageurl);
		// poolFactory = new PooledConnectionFactory(factory);
		poolFactory.setMaxConnections(100);
		poolFactory.setMaximumActiveSessionPerConnection(50);
		poolFactory.setTimeBetweenExpirationCheckMillis(3000);
		logger.info("getPooledConnectionFactory create success");
		return poolFactory;
	}

	/**
	 * 1.对象池管理connection和session,包括创建和关闭等
	 * 
	 * @return * @throws JMSException
	 */
	public static Session createSession() throws JMSException {
		PooledConnectionFactory poolFactory = getPooledConnectionFactory();
		PooledConnection pooledConnection = (PooledConnection) poolFactory
				.createConnection();
		return pooledConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static boolean produce(String msg) {
		Connection con = null;
		Session session = null;
		try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD, messageurl);
			con = factory.createConnection();
			session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			TextMessage textMessage = session.createTextMessage(msg);
			Destination destination = session.createTopic(topic);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.send(textMessage);
			System.out.println("MQUtils发送的消息的topic是 " + topic + "内容是 " + msg);
			logger.info("MQUtils发送的消息的topic是 " + topic + "内容是 " + msg);
			return true;
		} catch (Exception e) {
			logger.info("消息系統发送异常=" + e);
			return false;
		} finally {
			try {
				if (session != null) {
					session.close();
				}

			} catch (Exception e2) {
				logger.info("关闭异常=" + e2);
			}
			try {
				if (con != null) {
					con.close();
				}

			} catch (Exception e2) {
				logger.info("关闭异常=" + e2);
			}
		}
	}

	public static void main(String[] args) {
		try {
			//boolean produce = produce("123", "dfds");
//			/System.out.println("11=" + produce);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
