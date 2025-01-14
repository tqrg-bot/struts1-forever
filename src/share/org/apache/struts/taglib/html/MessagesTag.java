/*
 * $Header: /home/cvs/jakarta-struts/src/share/org/apache/struts/taglib/html/MessagesTag.java,v 1.3 2001/10/16 15:43:58 dwinterfeldt Exp $
 * $Revision: 1.3 $
 * $Date: 2001/10/16 15:43:58 $
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
 */


package org.apache.struts.taglib.html;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.ErrorMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;


/**
 * Custom tag that iterates the elements of a message collection.
 * It defaults to retrieving the messages from <code>Action.ERROR_KEY</code>,
 * but if the message attribute is set to true then the messages will be
 * retrieved from <code>Action.MESSAGE_KEY</code>. This is an alternative
 * to the default <code>ErrorsTag</code>.
 *
 * @since 1.1
 * @author David Winterfeldt
 * @version $Revision: 1.3 $ $Date: 2001/10/16 15:43:58 $
*/
public class MessagesTag extends BodyTagSupport {

    /**
     * The message resources for this package.
     */
    protected static MessageResources messageResources =
       MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * Iterator of the elements of this error collection, while we are actually
     * running.
    */
    protected Iterator iterator = null;

    /**
     * Whether or not any error messages have been processed.
    */
    protected boolean processed = false;

    /**
     * The name of the scripting variable to be exposed.
    */
    protected String id = null;

    /**
     * The servlet context attribute key for our resources.
    */
    protected String bundle = Action.MESSAGES_KEY;

    /**
     * The session attribute key for our locale.
    */
    protected String locale = Action.LOCALE_KEY;

    /**
     * The request attribute key for our error messages (if any).
     */
    protected String name = Action.ERROR_KEY;

    /**
     * The name of the property for which error messages should be returned,
     * or <code>null</code> to return all errors.
     */
    protected String property = null;

    /**
     * The message resource key for errors header.
    */
    protected String header = null;

    /**
     * The message resource key for errors footer.
    */
    protected String footer = null;

    /**
     * If this is set to 'true', then the <code>Action.MESSAGE_KEY</code> will
     * be used to retrieve the messages from scope.
    */
    protected String message = null;


    public String getId() {
    return (this.id);
    }

    public void setId(String id) {
    this.id = id;
    }

    public String getBundle() {
        return (this.bundle);
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }


    public String getLocale() {
        return (this.locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
    return (this.name);
    }

    public void setName(String name) {
    this.name = name;
    }


    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getHeader() {
        return (this.header);
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return (this.footer);
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getMessage() {
        return (this.message);
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Construct an iterator for the specified collection, and begin
     * looping through the body once per element.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        // Were any messages specified?
        ActionMessages messages = null;
        
        if (message != null && "true".equalsIgnoreCase(message))
           name = Action.MESSAGE_KEY;

        try {
            messages = RequestUtils.getActionMessages(pageContext, name);
        } catch (JspException e) {
            RequestUtils.saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection we are going to iterate over
        if (property == null)
            iterator = messages.get();
        else
            iterator = messages.get(property);

    // Store the first value and evaluate, or skip the body if none
    if (iterator.hasNext()) {
           ActionMessage report = (ActionMessage)iterator.next();
           String msg = RequestUtils.message(pageContext, bundle,
                                             locale, report.getKey(),
                                             report.getValues());

       pageContext.setAttribute(id, msg);

           if (header != null && header.length() > 0) {
              String headerMessage = RequestUtils.message(pageContext, bundle,
                                                             locale, header);
              if (headerMessage != null) {
                 // Print the results to our output writer
                 ResponseUtils.write(pageContext, headerMessage);
              }
           }

           // Set the processed variable to true so the
           // doEndTag() knows processing took place
           processed = true;

       return (EVAL_BODY_TAG);
        } else {
           return (SKIP_BODY);
        }

    }


    /**
     * Make the next collection element available and loop, or
     * finish the iterations if there are no more elements.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {
        // Render the output from this iteration to the output stream
        if (bodyContent != null) {
            ResponseUtils.writePrevious(pageContext, bodyContent.getString());
            bodyContent.clearBody();
        }

        // Decide whether to iterate or quit
    if (iterator.hasNext()) {
           ActionMessage report = (ActionMessage)iterator.next();
           String msg = RequestUtils.message(pageContext, bundle,
                                             locale, report.getKey(),
                                             report.getValues());

       pageContext.setAttribute(id, msg);

       return (EVAL_BODY_TAG);
    } else {
       return (SKIP_BODY);
    }

    }


    /**
     * Clean up after processing this enumeration.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndTag() throws JspException {
       if (processed && footer != null && footer.length() > 0) {
          String footerMessage = RequestUtils.message(pageContext, bundle,
                                                         locale, footer);
          if (footerMessage != null) {
             // Print the results to our output writer
             ResponseUtils.write(pageContext, footerMessage);
          }
       }
       // Continue processing this page
       return (EVAL_PAGE);
    }


    /**
     * Release all allocated resources.
     */
    public void release() {
       super.release();
       iterator = null;
       processed = false;
       id = null;
       bundle = Action.MESSAGES_KEY;
       locale = Action.LOCALE_KEY;
       name = Action.ERROR_KEY;
       property = null;
       header = null;
       footer = null;
       message = null;
    }

}
