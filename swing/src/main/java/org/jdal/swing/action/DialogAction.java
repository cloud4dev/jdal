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
package org.jdal.swing.action;


import java.awt.Window;

import org.jdal.swing.IconAction;

/**
 * Base class for Dialog Actions
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public abstract class DialogAction extends IconAction {

	private static final long serialVersionUID = 1L;
	private Window dialog;

	
	public Window getDialog() {
		return dialog;
	}

	public void setDialog(Window dialog) {
		this.dialog = dialog;
	}
}
