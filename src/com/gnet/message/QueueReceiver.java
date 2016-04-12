package com.gnet.message;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.json.JSONObject;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.gnet.threadpool.ThreadPoolUtils;


@Component(value = "queueReceiver")
public class QueueReceiver {
	static GetPosition gtpo = new GetPosition();
	private static Logger logger = Logger.getLogger(QueueReceiver.class);

	// @Resource (name="jdbcTemplate")
	// private JdbcTemplate jdbcTemplate;

	public QueueReceiver() {
		// insertMsg("我的设备", "12345698785128", "报警", "1", "北京市中关村",
		// "2015-12-3 14:36:26", "", "","0");
		// System.out.println("初始化值"+jdbcTemplate);
	}

	static String messageip = "";
	static String topic = "";
	static String messageId = "";
	static {
		try {
			messageip = PropertiesUtil.getKeyValue("messageip");
			topic = PropertiesUtil.getKeyValue("topic");
			System.out.println("messageurl==" + messageip);
			System.out.println("topic==" + topic);
			System.out.println("调用startQueueReceiver。。。。。。。");
			
			startQueueReceiver();
			
			logger.info("调用startQueueReceiver。。。。。。。");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MQUtils初始化异常" + e.getMessage());
		}

	}

	public static void startQueueReceiver() {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		final ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		final Session session;
		// Destination ：消息的目的地;消息发送给谁.
		final Destination destination;
		// 消费者，消息接收者
		MessageConsumer consumer = null;
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, messageip);
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			//connection.setClientID("GnetIOT_PC");
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			//destination = session.createQueue(topic + "_request");
			destination = session.createQueue(topic);
			//destination = session.createTopic(topic + "_request");
			// destination = session.createQueue("queue123");
			consumer = session.createConsumer(destination);
			//consumer = session.createDurableSubscriber((Topic) destination, "QueueReceiver_PC");
			// 监听启动
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					try {
						System.out.println("0000000000000000000000");
						if (message != null) {
							if (message instanceof TextMessage) {
								TextMessage textMessage = (TextMessage) message;
								System.out.println(textMessage.getText());
								String text = textMessage.getText();
								System.out.println("收到消息" + text);
								logger.info("收到消息" + text);
								try {
									// 进行协议解析推送
									JSONObject jsonObject0 = null;
									JSONObject jsonObject = null;
									try {
										jsonObject0 = new JSONObject(text);
										//messageId=jsonObject0.getString("messageId");
										//jsonObject = jsonObject0.getJSONObject("messageBody");
										logger.info("实时消息。。。。。。。。。。。。。。。");
										send(jsonObject0);
									} catch (Exception e2) {
										e2.printStackTrace();
										try {
											String time = text.split("!")[0];
											System.out.println("time==" + time);
											logger.info("time==" + time);
											String oldMsg = text.split("!")[1];
											jsonObject = new JSONObject(oldMsg);
											logger.info("非实时消息。。。。。。。。。。。。。。。");
											send(jsonObject);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}

								} catch (Exception e) {

									e.printStackTrace();
									logger.error("消费者解析异常  " + e.getMessage());
								}
								/*Connection connection = null;
								Destination destination;
								MessageProducer producer;
								connection = connectionFactory
										.createConnection();
								// 启动
								connection.start();
								// 获取操作连接
								Session session;
								session = connection.createSession(
										Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
								destination = session.createQueue(topic
										+ "_response");
								// 得到消息生成者【发送者】
								producer = session.createProducer(destination);
								// 设置不持久化，此处学习，实际根据项目决定
								producer.setDeliveryMode(DeliveryMode.PERSISTENT);
								// 构造消息，此处写死，项目就是参数，或者方法获取
								String correlationId = message
										.getJMSCorrelationID();
								System.out.println("messageId=="+messageId);
								sendMessage(session, producer, correlationId,
										messageId);
								session.commit();*/
								System.out.println("消息处理完毕！！！！！！！！！！！！！");
								System.out.println("*************************************************************************************");
								logger.info("消息处理完毕！！！！！！！！！！！！！");
								logger.info("*************************************************************************************");
							}
						} else {
							System.out.println("没有收到消息");
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("消费者执行异常  " + e.getMessage());
					}

				}

				public void sendMessage(Session session,
						MessageProducer producer, String correlationId,
						String result) throws Exception {
					// client side
					TextMessage message = session.createTextMessage(result);
					// 发送消息到服务端
					message.setJMSCorrelationID(correlationId);
					producer.send(message);
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("消费者执行异常  " + e.getMessage());
		}

	}


	
	public static void send(JSONObject jsonObject) throws Exception {
		try {
			String isOnline="1";
			System.out.println("jsonObject" + jsonObject);
			String type = jsonObject.getString("type");
			logger.info("type==  " + type);
			String devicesName = jsonObject.getString("nickName");
			String uniqueId = jsonObject.getString("uniqueId");
			String alarmType = "";
			String address = "暂时无法获取位置信息";
			String latitude = "";
			String longitude = "";
			try {
				address = jsonObject.getString("address");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				address = "暂时无法获取位置信息";
			}
			String sendTime = jsonObject.getString("sendTime");
			try {
				longitude = jsonObject.getString("longitude");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				longitude = "";
			}
			try {
				latitude = jsonObject.getString("latitude");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				latitude = "";
			}
			try {
				if (!"".equals(longitude) && !"".equals(latitude)
						&& "" != longitude && "" != latitude) {
					Double longitude3 = Double.parseDouble(longitude);// 经度
					Double latitude3 = Double.parseDouble(latitude);// 纬度
					String thisPostion1 = gtpo.transgpsbd(longitude3, latitude3);
					longitude = thisPostion1.split(",")[0];
					latitude = thisPostion1.split(",")[1];
				}
			} catch (Exception e1) {
				System.out.println("坐标转换异常");
				e1.printStackTrace();
			}
			String handle = "0";
			// 报警
			if ("1".equals(type) || "3".equals(type) || ("4").equals(type)
					|| ("8").equals(type) || ("9").equals(type)
					|| "10".equals(type)) {

				alarmType = "报警";
				String alarmTypeMsg = "";
				if ("1".equals(type)) {
					alarmTypeMsg = "手动报警";
				} else if ("3".equals(type)) {
					alarmTypeMsg = "超出安全区域报警";
				} else if ("4".equals(type)) {
					alarmTypeMsg = "电池电量低报警";
				} else if ("8".equals(type)) {
					alarmTypeMsg = "进入安全区域报警";
				} else if ("9".equals(type)) {
					alarmTypeMsg = "开关输入报警报警";
				} else if ("10".equals(type)) {
					alarmTypeMsg = "震动报警";
				}
				// 将数据写入数据库
				DBUtils.insertMsg(devicesName, uniqueId, alarmType, type,
						address, sendTime, longitude, latitude, handle,alarmTypeMsg);
			} else if ("5".equals(type) || "6".equals(type)
					|| ("18").equals(type)||"12".equals(type)||"15".equals(type)||"14".equals(type)||"19".equals(type)) {
				alarmType = "事件";
				String alarmTypeMsg = "";
				if ("5".equals(type)) {
					alarmTypeMsg = "开机事件";
				} else if ("6".equals(type)) {
					alarmTypeMsg = "关机事件";
				} else if ("18".equals(type)) {
					alarmTypeMsg = "测试报告";
				}else if ("12".equals(type)) {
					alarmTypeMsg = "开启追踪模式回执事件";
				}else if ("14".equals(type)) {
					alarmTypeMsg = "关闭追踪模式回执事件";
				}else if ("15".equals(type)) {
					alarmTypeMsg = "设备设置回执事件";
				}else if ("19".equals(type)) {
					alarmTypeMsg = "修改定位频率回执事件";
				}
				// 将数据写入数据库
				DBUtils.insertMsg(devicesName, uniqueId, alarmType, type,
						address, sendTime, longitude, latitude, handle,alarmTypeMsg);

			} else if ("0".equals(type)) {
				isOnline="0";
				alarmType = "故障";
				String alarmTypeMsg = "设备不在线";
				// 将数据写入数据库
				DBUtils.insertMsg(devicesName, uniqueId, alarmType, type,
						address, sendTime, longitude, latitude, handle,alarmTypeMsg);
			} else if ("17".equals(type) || "11".equals(type)
					|| "18".equals(type)) {
				String power = "";
				try {
					power = jsonObject.getString("BatPerc");
				} catch (Exception e) {
					power = "";
					e.printStackTrace();
				}

				// 存轨迹信息表
				DBUtils.insertTrace(uniqueId, devicesName, type, address,
						sendTime, longitude, latitude, power);
				if ("11".equals(type)) {
					//uniqueId
					//sendTime
					//devicesName
					DBUtils.insertDevTrack(uniqueId,devicesName,sendTime);
				}
			}
			// 是否在线信息插入数据库
			DBUtils.insertisOnline(uniqueId, isOnline);
			if ("1".equals(isOnline)) {
				ThreadPoolUtils.testPool(uniqueId);
			}
		} catch (Exception e) {
			logger.info("消息解析异常  " + e.getMessage());
			e.printStackTrace();
		}
	}
}
