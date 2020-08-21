package com.app.group15.services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;

public class HttpSessionMock implements HttpSession {
    private HashMap<String, Object> sessionAttributes;

    {
        sessionAttributes = new HashMap<>();
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {

    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    public Object getAttributeMock(String name) {
        if (name != null && name.equals("BANNER_ID_SESSION")) {
            return this.sessionAttributes.get(name);
        } else {
            return null;
        }
    }

    @Override
    public Object getValue(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String name, Object value) {
    }

    public void setAttributeMock(String name, Object value) {
        this.sessionAttributes.put(name, value);
    }

    @Override
    public void putValue(String name, Object value) {

    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public void removeValue(String name) {

    }

    @Override
    public void invalidate() {
    }

    public void invalidateMock() {
        this.sessionAttributes.clear();
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
