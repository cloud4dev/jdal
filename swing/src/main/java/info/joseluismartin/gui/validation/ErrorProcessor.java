/*
 * Copyright 2009-2011 the original author or authors.
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
package info.joseluismartin.gui.validation;

import org.springframework.validation.FieldError;

import info.joseluismartin.gui.Binder;

/**
 * Process binding erros. <code>AbstractView</code> execute configured ErrorProcessors 
 * when there are binding errors. 
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public interface ErrorProcessor {
	
	/**
	 * Process binding error. Usefull for to do control interaction on binding erros, 
	 * like set a tooltip with error, change background and so.
	 * @param binder the binder that gerenate the error
	 * @param error the spring validation error Object
	 */
	void processError(Binder<?> binder, FieldError error);
	
	/**
	 * clear state of ErrorProcessor. Usefull for undo control changes made in processError.
	 */
	void reset();

}