package com.sun.Extend;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;


/**
 * Hello world!
 *
 */
public class JavaTestExtend extends AbstractJavaSamplerClient implements Serializable,Interruptible
{
	private long sleepTime;
	private String label;
	private String responseCode;
	private String responseMessage;
	private long DEFAULT_SLEEP_TIME=1000;
	private boolean success;
	private static final String SLEEP_NAME="Sleep_Time";
	private static final String LABEL_NAME="Label";
	private static final String RESPONSECODE_NAME="ResponseCode";
	private static final String RESPONSEMESSAGE_NAME="ResponseMessage";
	private static final String DEFAULT_LABEL_NAME="";
	private static final String DEFAULT_RESPONSECODE_NAME="";
	private static final String DEFAULT_RESPONSEMESSAGE_NAME="";
	private transient volatile Thread myThread;
	
	private void setUpValues(JavaSamplerContext context) {
		sleepTime=context.getLongParameter(SLEEP_NAME, DEFAULT_SLEEP_TIME);
		label=context.getParameter(LABEL_NAME,DEFAULT_LABEL_NAME);
		responseCode=context.getParameter(RESPONSECODE_NAME,DEFAULT_RESPONSECODE_NAME);
		responseMessage=context.getParameter(RESPONSEMESSAGE_NAME,DEFAULT_RESPONSEMESSAGE_NAME);
	}
	
	@Override
	public Arguments getDefaultParameters() {
		
		Arguments params=new Arguments();
		params.addArgument(SLEEP_NAME,String.valueOf(DEFAULT_SLEEP_TIME));
		return params;
		
	}
	
	public SampleResult runTest(JavaSamplerContext context) {
		setUpValues(context);
		SampleResult results=new SampleResult();
		results.setResponseCode(responseCode);
		results.setResponseMessage(responseMessage);
		results.setSampleLabel(label);
		results.sampleStart();
		long sleep=sleepTime;
		 try {
	            // Execute the sample. In this case sleep for the
	            // specified time, if any
	            if (sleep > 0) {
	                myThread = Thread.currentThread();
	                TimeUnit.MILLISECONDS.sleep(sleep);
	                myThread = null;
	            }
	            results.setSuccessful(success
	            		);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            
	            results.setSuccessful(false);
	        } catch (Exception e) {
	           
	            results.setSuccessful(false);
	        } finally {
	            // Record end time and populate the results.
	            results.sampleEnd();
	        }
		
		return results;
	}

	public boolean interrupt() {
		
		
		return false;
	}
   
}
