package hello;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;

import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;


public class Application {
	
	public static final String swf_domain = "helloWorldWalkthrough-NoSpring";
	public static final String	swf_endpoint = "https://swf.us-east-1.amazonaws.com";
	public static final String	swf_tasklist = "HelloWorldAsyncList-NoSpring";

    public static void main(String[] args) {

		System.out.println("Initializing AWS...");
		ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70*1000);
		
		AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(config);
		service.setEndpoint(swf_endpoint);
		
		String domain = swf_domain;

		try {
		
			String taskListToPoll = "HelloWorldAsyncList";
			
			ActivityWorker aw = new ActivityWorker(service, domain, taskListToPoll);
			aw.addActivitiesImplementation(new GreeterActivitiesImpl());
			System.out.println("Starting activity worker...");
			aw.start();
			
			WorkflowWorker wfw = new WorkflowWorker(service, domain, taskListToPoll);
			wfw.addWorkflowImplementationType(GreeterWorkflowImpl.class);
			System.out.println("Starting workflow worker...");
			wfw.start();
			
			// Also in GreeterMain
			
			// worker starter code -- this should really be done elsewhere, but for testing...
			GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(service, domain);
			GreeterWorkflowClientExternal greeter = factory.getClient("someID");
			greeter.greet();
		}
		catch (Exception e)
		{
		   System.out.println(e);
		}

    }

}

