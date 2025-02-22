/*
 * $Header: /home/cvs/jakarta-struts/src/share/org/apache/struts/taglib/bean/CookieTag.java,v 1.9 2001/04/23 22:52:20 craigmcc Exp $
 * $Revision: 1.9 $
 * $Date: 2001/04/23 22:52:20 $
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


package org.apache.struts.taglib.bean;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;


/**
 * Define a scripting variable based on the value(s) of the specified
 * cookie received with this request.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.9 $ $Date: 2001/04/23 22:52:20 $
 */

public class CookieTag extends TagSupport {


    // ------------------------------------------------------------- Properties


    /**
     * The name of the scripting variable that will be exposed as a page
     * scope attribute.
     */
    protected String id = null;

    public String getId() {
        return (this.id);
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources
        ("org.apache.struts.taglib.bean.LocalStrings");


    /**
     * Return an array of Cookies if <code>multiple</code> is non-null.
     */
    protected String multiple = null;

    public String getMultiple() {
        return (this.multiple);
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }


    /**
     * The name of the cookie whose value is to be exposed.
     */
    protected String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * The default value to return if no cookie of the specified name is found.
     */
    protected String value = null;

    public String getValue() {
        return (this.value);
    }

    public void setValue(String value) {
        this.value = value;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Retrieve the required property and expose it as a scripting variable.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        // Retrieve the required cookie value(s)
        ArrayList values = new ArrayList();
        Cookie cookies[] =
            ((HttpServletRequest) pageContext.getRequest()).getCookies();
        if (cookies == null)
            cookies = new Cookie[0];

        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName()))
                values.add(cookies[i]);
        }
        if ((values.size() < 1) && (value != null))
            values.add(new Cookie(name, value));
        if (values.size() < 1) {
            JspException e = new JspException
                (messages.getMessage("cookie.get", name));
            RequestUtils.saveException(pageContext, e);
            throw e;
        }

        // Expose an appropriate variable containing these results
        if (multiple == null) {
            Cookie cookie = (Cookie) values.get(0);
            pageContext.setAttribute(id, cookie);
        } else {
            cookies = new Cookie[values.size()];
            pageContext.setAttribute(id, (Cookie[]) values.toArray(cookies));
        }
        return (SKIP_BODY);

    }


    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        id = null;
        multiple = null;
        name = null;
        value = null;

    }


}
