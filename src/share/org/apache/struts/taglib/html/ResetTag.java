/*
 * $Header: /home/cvs/jakarta-struts/src/share/org/apache/struts/taglib/html/ResetTag.java,v 1.3 2001/04/18 01:31:15 craigmcc Exp $
 * $Revision: 1.3 $
 * $Date: 2001/04/18 01:31:15 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
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
 *
 */


package org.apache.struts.taglib.html;


import java.lang.reflect.Method;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;


/**
 * Tag for input fields of type "reset".
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2001/04/18 01:31:15 $
 */

public class ResetTag extends BaseHandlerTag {


    // ----------------------------------------------------- Instance Variables


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
     MessageResources.getMessageResources(Constants.Package + ".LocalStrings");


    /**
     * The name of the generated input field.
     */
    protected String name = "reset";


    /**
     * The body content of this tag (if any).
     */
    protected String text = null;


    /**
     * The value of the button label.
     */
    protected String value = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the field name.
     */
    public String getName() {

	return (this.name);

    }


    /**
     * Set the field name.
     *
     * @param name The field name
     */
    public void setName(String name) {

	this.name = name;

    }


    /**
     * Return the label value.
     */
    public String getValue() {

	return (this.value);

    }


    /**
     * Set the label value.
     *
     * @param value The label value
     */
    public void setValue(String value) {

	this.value = value;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

	// Do nothing until doEndTag() is called
        this.text = null;
	return (EVAL_BODY_TAG);

    }



    /**
     * Save the associated label from the body content.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        if (bodyContent != null) {
            String value = bodyContent.getString().trim();
            if (value.length() > 0)
                text = value;
        }
        return (SKIP_BODY);

    }


    /**
     * Process the end of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {

	// Acquire the label value we will be generating
	String label = value;
	if ((label == null) && (text != null))
	    label = text;
	if ((label == null) || (label.length() < 1))
	    label = "Reset";

	// Generate an HTML element
	StringBuffer results = new StringBuffer();
	results.append("<input type=\"reset\" name=\"");
	results.append(name);
	results.append("\"");
	if (accesskey != null) {
	    results.append(" accesskey=\"");
	    results.append(accesskey);
	    results.append("\"");
	}
	if (tabindex != null) {
	    results.append(" tabindex=\"");
	    results.append(tabindex);
	    results.append("\"");
	}
	results.append(" value=\"");
	results.append(label);
	results.append("\"");
	results.append(prepareEventHandlers());
	results.append(prepareStyles());
	results.append(">");

	// Render this element to our writer
        ResponseUtils.write(pageContext, results.toString());

        // Evaluate the remainder of this page
	return (EVAL_PAGE);

    }


    /**
     * Release any acquired resources.
     */
    public void release() {

	super.release();
	name = "reset";
        text = null;
	value = null;

    }


}
