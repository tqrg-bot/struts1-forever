/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
 
 
package com.wintecinc.struts.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.FastHashMap; 


/**
 * <p>Used in the Validation framework.</p>
 *
 * <ul><li>See /WEB-INF/validation.xml for validation rules.</li></ul>
 *
 * @author David Winterfeldt
*/
public class Form implements Serializable {
    /**
     * The name of the form should match the form name in the action configuration 
     * in the struts-config.xml file.
    */
    protected String name = null;

    /**
     * List of <code>Field</code>s.  Used to maintain the order they were added in.
    */
    protected List lFields = new ArrayList();
    
    /**
     * Map of <code>Field</code>s keyed on their property value.
    */
    protected FastHashMap hFields = new FastHashMap();

    /**
     * Gets the name of the form.  It should match the form name in 
     * the action configuration in the struts-config.xml file.
    */
    public String getName() {
       return name;	
    }

    /**
     * Sets the name of the form.  It should match the form name in 
     * the action configuration in the struts-config.xml file.
    */
    public void setName(String name) {
       this.name = name;	
    }

    /**
     * Add a <code>Field</code> to the <code>Form</code>.
    */
    public void addField(Field f) {
       if (f != null && f.getProperty() != null && f.getProperty().length() > 0) {
       	  lFields.add(f);
       	  
          hFields.put(f.getKey(), f);
       }
    }

    /**
     * A <code>List</code> of <code>Field</code>s is returned as an 
     * unmodifiable <code>List</code>.
    */
    public List getFields() {
    	return Collections.unmodifiableList(lFields);
    }
    
    /**
     * A <code>Map</code> of <code>Field</code>s is returned as an 
     * unmodifiable <code>List</code>.
    */
    public Map getFieldMap() {
    	return Collections.unmodifiableMap(hFields);
    }

    /**
     * Processes all of the <code>Form</code>'s <code>Field</code>s.
    */
    public void process(Map globalConstants, Map constants) {
       hFields.setFast(true);
       
       for (Iterator i = lFields.iterator(); i.hasNext(); ) {
       	  Field f = (Field)i.next();
       	  f.process(globalConstants, constants);
       }
    }
    
    public String toString() {
    	String sReturn = "Form: " + name + "\n";
    			 //"  validationclass=" + validationclass + "\n";
    	for (Iterator i = lFields.iterator(); i.hasNext(); ) {
    	   sReturn += "\tField: \n" + i.next() + "\n";;	
    	}
    	return sReturn;
    }
	
}