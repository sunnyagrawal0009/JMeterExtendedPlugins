package com.sun.Extend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jmeter.gui.GUIMenuSortOrder;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.util.JMeterUtils;

@GUIMenuSortOrder(2)
public class DebugSamplerExtend extends AbstractSampler implements TestBean{

	public SampleResult sample(Entry e) {
		
		SampleResult res=new SampleResult();
		res.setSampleLabel("DebugSampler");
		res.sampleStart();
		StringBuilder sb=new StringBuilder(100);
		formatSet(sb,JMeterContextService.getContext().getVariables().entrySet());
		sb.append("\n");
		formatSet(sb,JMeterUtils.getJMeterProperties().entrySet());
		sb.append("\n");
		formatSet(sb,System.getProperties().entrySet());
		sb.append("\n");
		res.setResponseData(sb.toString(),null);
		res.setResponseOK();
		res.sampleEnd();
		return res;
	}
	
	private void formatSet(StringBuilder sb,@SuppressWarnings("rawtypes") Set s) {
		@SuppressWarnings("unchecked")
		List<Map.Entry<Object, Object>> al = new ArrayList(s);
        al.sort((Map.Entry<Object, Object> o1, Map.Entry<Object, Object> o2) -> {
                String m1 = (String)o1.getKey();
                String m2 = (String)o2.getKey();
                return m1.compareTo(m2);
        });
        al.forEach(me -> sb.append(me.getKey()).append("=").append(me.getValue()).append("\n"));
	}

}
