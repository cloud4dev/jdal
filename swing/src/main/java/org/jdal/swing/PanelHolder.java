/*
 * Copyright 2008-2011 the original author or authors.
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
package org.jdal.swing;


import javax.swing.Icon;
import javax.swing.JComponent;

import org.jdal.beans.MessageSourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * A Holder for gui component classes
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public abstract class PanelHolder {
	
	protected MessageSourceWrapper messageWrapper = new MessageSourceWrapper();
	
	public PanelHolder() {
		
	}
	
	public PanelHolder(Icon icon, String name) {
		super();
		this.icon = icon;
		this.name = name;
	}

	private Icon icon;
	private String name;

	public abstract JComponent getPanel();

	/**
	 * @return the icon
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name != null ?  messageWrapper.getMessage(name) : null;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public MessageSource getMessageSource() {
		return this.messageWrapper.getMessageSource();
	}
	
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageWrapper.setMessageSource(messageSource);
	}
}
