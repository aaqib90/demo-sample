package com.sample.camel.poc;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.main.Main;

import com.ibm.mq.jms.MQQueueConnectionFactory;

/**
 * App Class
 *
 */
public class App 
{
	

    private App() {
        // to comply with checkstyle
    }
    public static void main( String[] args ) throws Exception
    {
    	
    	// use Camel Main to setup and run Camel
        Main camelContext = new Main();

    	 // create the ActiveMQ Artemis component
        camelContext.bind("artemis", createArtemisComponent());
        
        camelContext.bind("wmq", createMqConnFactory());

        // add the widget/gadget route
        camelContext.addRouteBuilder(new SampleCamleRoute());


        // start and run Camel (block)
        camelContext.run();
    }

    /**
     * To Create JMS Component 
     * @return
     * @throws Exception
     */
    private static JmsComponent createArtemisComponent() throws Exception {
        // Sets up the Artemis core protocol connection factory

    	// without SSL
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://3.134.136.193:61616");

    	// With SSL
//    	String BROKER_URL = "ssl://10.0.10.102:61616";
//    	String CLIENT_TS = "D:\\amq\\poc\\src\\main\\client-side-truststore.jks";
//    	String CLIENT_KS = "D:\\amq\\poc\\src\\main\\resources\\client-side-keystore.jks";
//    	String CLIENT_TS_PW = "secureexample";
//    	String CLIENT_KS_PW = "secureexample";
//        
//        ActiveMQSslConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory();
//        try {
//            connectionFactory.setTrustStore(CLIENT_TS);
//            connectionFactory.setTrustStorePassword(CLIENT_TS_PW);
//            connectionFactory.setKeyStore(CLIENT_KS);
//            connectionFactory.setKeyStorePassword(CLIENT_KS_PW);
//            connectionFactory.setBrokerURL(BROKER_URL);
//        } catch (Exception e) {
//            throw new Exception("JMS Connection Failed (Trust store or key store weren't found) : ", e);
//        }

        JmsConfiguration configuration = new JmsConfiguration();
        configuration.setConnectionFactory(connectionFactory);

        return new JmsComponent(configuration);
    }

    private static JmsComponent createMqConnFactory() throws Exception {
        // Sets up the IBM MQ core protocol connection factory

    	String WMQ_CHANNEL = "CAMMEL.SVRCONN";
    	String WMQ_HOSTNAME = "10.0.10.160";
    	int WMQ_PORT = 7657;
    	String WMQ_QUEUE_MGR = "POCBC";
         
    	MQQueueConnectionFactory mQQueueConnectionFactory = new MQQueueConnectionFactory();
    	mQQueueConnectionFactory.setTransportType(1);
    	mQQueueConnectionFactory.setChannel(WMQ_CHANNEL);
    	mQQueueConnectionFactory.setHostName(WMQ_HOSTNAME);
    	mQQueueConnectionFactory.setPort(WMQ_PORT);
    	mQQueueConnectionFactory.setQueueManager(WMQ_QUEUE_MGR);
    	
    	JmsComponent jmsComponent = new JmsComponent();
    	jmsComponent.setConnectionFactory(mQQueueConnectionFactory);
    	
    	return jmsComponent;
    }
}
