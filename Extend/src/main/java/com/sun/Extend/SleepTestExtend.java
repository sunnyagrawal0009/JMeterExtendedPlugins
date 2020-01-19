package com.sun.Extend;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;

public class SleepTestExtend extends AbstractJavaSamplerClient implements Serializable,Interruptible {

	private String name;
	private long sleepMask;
	private static final long DEFAULT_SLEEP_TIME=1000;
	private static final String DEFAULT_SLEEP_MASK="";
	private long sleepTime=5000;
	private boolean success;
	private transient volatile Thread myThread;
	
	public Arguments getDefaultArguments() {
		Arguments params=new Arguments();
		params.addArgument("SleepTime",String.valueOf(DEFAULT_SLEEP_TIME));
		params.addArgument("SleepMask",DEFAULT_SLEEP_MASK);
		return params;
	}
	
	 public void setupTest(JavaSamplerContext context) {
		 sleepTime=context.getLongParameter("SleepTime", DEFAULT_SLEEP_TIME);
		 name=context.getParameter(TestElement.NAME);
	 }
	
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult sR=new SampleResult();
		sR.setSampleLabel(name);
		sR.setResponseCode("200");
		sR.setResponseMessage("Hi");
		long sleep=sleepTime;
		sR.sampleStart();
		 try {
	            // Execute the sample. In this case sleep for the
	            // specified time, if any
	            if (sleep > 0) {
	                myThread = Thread.currentThread();
	                TimeUnit.MILLISECONDS.sleep(sleep);
	                myThread = null;
	            }
	            sR.setSuccessful(success
	            		);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            
	            sR.setSuccessful(false);
	        } catch (Exception e) {
	           
	            sR.setSuccessful(false);
	        } finally {
	            // Record end time and populate the results.
	            sR.sampleEnd();
	        }
		return sR;
	}

	public boolean interrupt() {
		// TODO Auto-generated method stub
		return false;
	}
}
