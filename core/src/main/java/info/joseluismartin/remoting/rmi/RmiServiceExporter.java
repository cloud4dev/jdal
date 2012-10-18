/*
 * Copyright 2009-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.joseluismartin.remoting.rmi;

import java.rmi.Remote;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.rmi.RmiInvocationHandler;

/**
 * A RmiServiceExporter that exposes the remote service to allow passing it
 * as parameter to a remote object.
 *  
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public class RmiServiceExporter extends org.springframework.remoting.rmi.RmiServiceExporter {

	private Object remoteService;

	@Override
	protected Remote getObjectToExport() {
		Remote exportedObject = super.getObjectToExport();

		if (getService() instanceof Remote && (
				getServiceInterface() == null || exportedObject.getClass().isAssignableFrom(getServiceInterface()))) {
			this.remoteService = exportedObject;
		}
		else {
			// RMI Invokers. 
			RmiInvocationHandler wrapper = (RmiInvocationHandler) exportedObject;
			ProxyFactory factory = new ProxyFactory(getServiceInterface(), 
					new RmiExporterServiceInterceptor(wrapper));

			this.remoteService = factory.getProxy();
		}

		return exportedObject;
	}

	public Object getRemoteService()  {
		return remoteService;
	}


}
