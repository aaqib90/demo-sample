package com.sample.camel.poc;

import org.apache.camel.Endpoint;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

public class SampleCamleRoute extends RouteBuilder {
	@Override
    public void configure() throws Exception {
        // you can define the endpoints and predicates here
        // it is more common to inline the endpoints and predicates in the route
        // as shown in the CreateOrderRoute

        Endpoint amq1 = endpoint("artemis:queue:AMQ1");
        Endpoint wmqQ1 = endpoint("wmq:queue:DEV1");
        Endpoint backupWmqQ1 = endpoint("wmq:queue:BACKUP.DEV1");
        

        
        
        // errorHandler(deadLetterChannel(backupWmqQ1));

        from(wmqQ1)
        //.to(backupWmqQ1)
        .to("log:IBM_MQ to RH_AMQ")
        .to(amq1);
	}
}
