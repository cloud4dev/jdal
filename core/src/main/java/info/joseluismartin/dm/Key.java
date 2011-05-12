/*
 * Copyright 2005-2011 the original author or authors.
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
package info.joseluismartin.dm;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A Database Key 
 *
 * @author Jose Luis Martin - (chelu.es@gmail.com)
 */
public class Key implements Serializable {
	
	
	private static final long serialVersionUID = 133802428827763162L;
	/** key fields */
	private Object fields[];
	
	/**
	 * Create  a new <code>Key</code> from an array of Objects
	 */
	public Key(Object[] fields) {
		checkKeyNotNull(fields);
		this.fields = fields;
	}
	
	// Convenient Constructors for simple cases 
	 
	public Key(long id) {
		this.fields = new Object[1];
		this.fields[0] = new Long(id);
	}
	
	public Key(Object arg1) {
		this.fields = new Object[1];
		this.fields[0] = arg1;
		checkKeyNotNull(fields);
	}
	
	public Key(Object arg1, Object arg2) {
		this.fields = new Object[2];
		this.fields[0] =  arg1;
		this.fields[1] = arg2;
		checkKeyNotNull(fields);
	}
	
	/** 
	 * Throw an IllegalArgumentException field or some field[i] is Null
	 * 
	 * @param fields
	 */
	private void checkKeyNotNull(Object[] fields) {
		if (fields == null) 
			throw new IllegalArgumentException("Cannot have a Null Key");
		for (int i = 0; i < fields.length; i++) 
			if (fields[i] == null) 
				throw new IllegalArgumentException("Cannot have a Null element of Key");
	}
	
	/** 
	 * Throw an IllegalStateException if key is not Single 
	 */
	private void checkSingleKey() {
		if (fields.length >  1)
			throw new IllegalStateException("Cannot take value of composite Key");
	}
	
	// Accesors to use key as Single key
	public Object value() {
		checkSingleKey();
		return fields[0];
	}
	
	public long longValue() {
		checkSingleKey();
		return longValue(0);
	}
	
	public String stringValue() {
		checkSingleKey();
		return (String) fields[0];
	}
	
	// Accesor for composite long keys 
	public long longValue(int i) {
		if (!(fields[i] instanceof Long))
			throw new IllegalStateException("Cannot take long value on not long key");
		return ((Long) fields[i]).longValue();
	}
	
	/**
	 * Test if two keys are equals
	 * 
	 * @param obj  the key to compare to
	 * @return true if keys are equal
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Key)) return false;
		Key key = (Key) obj;
		if (fields.length != key.fields.length) return false;
		for (int i = 0; i < fields.length; i++) 
			if (!fields[i].equals(key.fields[i])) return false;

		// all fields are equals, is the same key
		return true;
	}

	public Object[] getFields() {
		return fields;
	}
	
	public void setFields(Object[] fields) {
		this.fields = fields;
	}
	
	public int hashCode() {
		return Arrays.hashCode(fields);
	}
	
	public String toString() {
		StringBuffer sbuff = new StringBuffer("Key [");
		for(Object obj : fields)
			sbuff.append(obj.toString() + ",");
		
		sbuff.deleteCharAt(sbuff.length() - 1);
		sbuff.append("]");
		
		return sbuff.toString();
	}	
}