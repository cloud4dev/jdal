/*
 * Copyright 2002-2010 Jose Luis Martin.
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
package info.joseluismartin.gui;

import info.joseluismartin.gui.bind.BinderFactory;
import info.joseluismartin.gui.bind.CompositeBinder;
import info.joseluismartin.gui.bind.PropertyBinder;
import info.joseluismartin.gui.validation.ErrorProcessor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

/**
 * Default Abstract View with Composite support for refresh and update.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public abstract class AbstractView<T> implements View<T> {
	
	public final static String DEFAULT_BINDER_FACTORY_NAME = "binderFactory";
	private static final Log log = LogFactory.getLog(AbstractView.class);
	
	private String name;
	private BinderFactory binderFactory;
	private CompositeBinder<T> binder = new CompositeBinder<T>();
	private T model;
	private JComponent panel;
	private List<View<T>> subViews = new ArrayList<View<T>>();
	private Validator validator;
	private MessageSource messageSource;
	
	protected int width = 0;
	protected int height = 0;
	private Map<JComponent, Color> backgroundMap = new HashMap<JComponent, Color>();
	private List<ErrorProcessor> errorProcessors = new ArrayList<ErrorProcessor>();
	
	public AbstractView() {
		
	}
	
	public AbstractView(T model) {
		setModel(model);
	}
	
	/**
	 * @param state2
	 * @param string
	 * @param b
	 */
	public void bind(Object component, String propertyName, boolean readOnly) {
		binder.bind(component, propertyName, readOnly);
		
	}
	
	public void bind(Object component, String propertyName) {
		bind(component, propertyName, false);
	}
	
	public JComponent getPanel() {
		if (panel == null) {
			panel = buildPanel();
			if (width != 0 && height != 0)
				panel.setSize(width, height);
		}
		return panel;
	}
	
	protected abstract JComponent buildPanel();

	
	public T getModel() {
		return model;
	}
	
	public final void setModel(T model) {
		this.model = model;
		binder.setModel(model);
		
		// refresh subviews
		for (View<T> v : subViews)
			v.setModel(model);
		
		onSetModel(model);
	}
	
	protected void onSetModel(T model) {
		
	}
	
	public final void update() {
		// do custom update
		doUpdate();
		
		binder.update();
		
		// update subviews
		for (View<T>  v : subViews) {
			v.update();
		}
	}
	
	protected void doUpdate() {
		
	}
	
	public void addView(View<T> view) {
		subViews.add(view);
		view.setModel(model);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final void refresh() {
		doRefresh();
		
		binder.refresh();
		
		// refresh subviews
		for (View<T> v : subViews)
			v.refresh();
	}
	
	protected void doRefresh() {
		
	}

	public BinderFactory getBinderFactory() {
		return binderFactory;
	}

	public void setBinderFactory(BinderFactory binderFactory) {
		this.binderFactory = binderFactory;
		binder.setBinderFactory(binderFactory);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean validateView() {
		if (validator == null)
			return true;
		
		resetErrorProcessors();
		Errors errors = new BeanPropertyBindingResult(getModel(), "");
		validator.validate(getModel(), errors);
		
		if (errors.hasErrors()) {
			String errorMessage = getErrorMessage(errors);
			JOptionPane.showMessageDialog(getPanel(),errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
			for (FieldError error : errors.getFieldErrors()) {
				for (ErrorProcessor ep : errorProcessors ) 
					ep.processError(binder.getBinder(error.getField()), error);
			}
			return false;
		}
		
		return true;
	}
	
	private void resetErrorProcessors() {
		for (ErrorProcessor ep : errorProcessors) {
			ep.reset();
		}
		
	}

	@SuppressWarnings("unchecked")
	protected String getErrorMessage(Errors errors) {
		StringBuilder sb = new StringBuilder();
		if (errors.hasErrors()) {
			sb.append("\n");
			Iterator iter = errors.getAllErrors().iterator();
			while (iter.hasNext()) {
				ObjectError oe = (ObjectError) iter.next();
				sb.append("- ");
				sb.append(messageSource.getMessage(oe, null));
				sb.append("\n");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public void clear() {
		T model = getModel();
		if (model != null) {
			try {
				setModel((T) model.getClass().newInstance());
				refresh();
			} catch (InstantiationException e) {
				log.error(e);
			} catch (IllegalAccessException e) {
				log.error(e);
			}
		}
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the errorProcessors
	 */
	public List<ErrorProcessor> getErrorProcessors() {
		return errorProcessors;
	}

	/**
	 * @param errorProcessors the errorProcessors to set
	 */
	public void setErrorProcessors(List<ErrorProcessor> errorProcessors) {
		this.errorProcessors = errorProcessors;
	}
}