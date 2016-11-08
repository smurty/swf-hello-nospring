package hello;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;

public class GreeterMain {

	public GreeterMain() {

	}
	
	public static void mian(String[] args) {
		System.out.println("Initializing GreeterMain...");
        ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70*1000);

        AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(config);
        service.setEndpoint(Application.swf_endpoint);
        
		// Start a workflow execution
		GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(
				service, Application.swf_domain);
		GreeterWorkflowClientExternal greeter = factory.getClient("someID");
		greeter.greet();
		
	}
	
}
