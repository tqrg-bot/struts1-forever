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


package com.wintecinc.struts.example.validator;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.wintecinc.struts.action.ValidatorForm;


/**
 * Form bean for the user registration page.
 *
 * @author David Wintefeldt
*/
public final class RegistrationForm extends ValidatorForm implements Serializable {
    private String action = null;
       
    private String sFirstName = null;
    private String sLastName = null;
    private String sAddr = null;
    private CityStateZip csz = new CityStateZip();
    private String sPhone = null;
    private String sEmail = null;


    public String getAction() {
	return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFirstName() {
       return sFirstName;	
    }
    
    public void setFirstName(String sFirstName) {
       	this.sFirstName = sFirstName;
    }
    
    public String getLastName() {
       return sLastName;	
    }
    
    public void setLastName(String sLastName) {
       	this.sLastName = sLastName;
    }

    public String getAddr() {
       return sAddr;	
    }
    
    public void setAddr(String sAddr) {
       	this.sAddr = sAddr;
    }

    public CityStateZip getCityStateZip() {
       return csz;	
    }
    
    public void setCityStateZip(CityStateZip csz) {
       	this.csz = csz;
    }

    public String getPhone() {
       return sPhone;	
    }
    
    public void setPhone(String sPhone) {
       	this.sPhone = sPhone;
    }

    public String getEmail() {
       return sEmail;	
    }
    
    public void setEmail(String sEmail) {
       	this.sEmail = sEmail;
    }
        
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
       action = null;
       sFirstName = null;
       sLastName = null;
       sAddr = null;
       csz = new CityStateZip();
       sPhone = null;
       sEmail = null;
    }

}
