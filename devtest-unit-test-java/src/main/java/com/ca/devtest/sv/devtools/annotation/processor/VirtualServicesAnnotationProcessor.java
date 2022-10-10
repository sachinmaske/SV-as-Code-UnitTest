/**
 * 
 */
package com.ca.devtest.sv.devtools.annotation.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ca.devtest.sv.devtools.DevTestClient;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualService;
import com.ca.devtest.sv.devtools.annotation.DevTestVirtualServices;
import com.ca.devtest.sv.devtools.services.AbstractVirtualService;

/**
 * @author gaspa03
 *
 */
public class VirtualServicesAnnotationProcessor implements AnnotationProcessor {

	/* (non-Javadoc)
	 * @see com.ca.devtest.sv.devtools.annotation.processor.MethodProcessorAnnotation#process(com.ca.devtest.sv.devtools.DevTestClient, java.lang.annotation.Annotation)
	 */
	@Override
	public List<AbstractVirtualService> process(DevTestClient devTestClient, Annotation annotation)
			throws Exception {
		 List<AbstractVirtualService>  result=new ArrayList<AbstractVirtualService>(1);
		 result.addAll(buildVirtualService(devTestClient, (DevTestVirtualServices) annotation));
		
		return result;
	}

	private Collection<? extends AbstractVirtualService> buildVirtualService(DevTestClient devTestClient,
			DevTestVirtualServices annotation) throws Exception {
		DevTestVirtualService[] virtualServicesAnnotation=annotation.value();
		List<AbstractVirtualService> virtualServices = new ArrayList<AbstractVirtualService>();
		
		for (DevTestVirtualService vsAnnotation : virtualServicesAnnotation) {
			// get Annotation processor 
			AnnotationProcessor processor = AnnotationProcessorFactory.getInstance().getProcessor(vsAnnotation);
			List<AbstractVirtualService> services=processor.process(devTestClient,vsAnnotation);
			if( null!=services)
			virtualServices.addAll(services);
		}
		
		
		
		return virtualServices;
	}

}
