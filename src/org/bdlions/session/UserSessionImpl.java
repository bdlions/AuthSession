/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.shiro.session.ProxiedSession;
import org.apache.shiro.subject.Subject;
import org.bdlions.transport.sender.IClientPacketSender;

/**
 *
 * @author alamgir
 */
public class UserSessionImpl extends ProxiedSession implements ISession {

    private final String USER_NAME = "USER_NAME";
    private final String USER_ID = "USER_ID";
    private final String DEVICE_TYPE = "DEVICE_TYPE";
    private final String REMOTE_PORT = "REMOTE_PORT";
    private final String REMOTE_IP = "REMOTE_IP";
    private final String APP_TYPE = "APP_TYPE";
    private final String VERSION = "VERSION";
    private final String LIVE_STATUS = "LIVE_STATUS";
    private final String MOOD = "MOOD";
    private final String FULL_NAME = "FULL_NAME";
    private final Subject user;

    public UserSessionImpl(Subject subject) {
        super(subject.getSession());
        this.user = subject;
    }

    @Override
    public Subject getUser(){
        return user;
    }
    
    public void setUserName(String userName) {
        setAttribute(USER_NAME, userName);
    }

    @Override
    public String getUserName() {
        return (String) getAttribute(USER_NAME);
    }

    @Override
    public long getUserId() {
        return (Long) getAttribute(USER_ID);
    }

    public void setUserId(long userId) {
        setAttribute(USER_ID, userId);
    }

    @Override
    public String getSessionId() {
        return (String) getId();
    }

    @Override
    public int getDevice() {
        return (Integer) getAttribute(DEVICE_TYPE);
    }

    public void setDevice(int device) {
        setAttribute(DEVICE_TYPE, device);
    }

    @Override
    public int getRemotePort() {
        return (Integer) getAttribute(REMOTE_PORT);
    }

    @Override
    public void setRemotePort(int port) {
        setAttribute(REMOTE_PORT, port);
    }

    @Override
    public InetAddress getRemoteIP() {
        try {
            return InetAddress.getByName((String) getAttribute(REMOTE_IP));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void setRemoteIP(InetAddress ip) {
        setAttribute(REMOTE_IP, ip);
    }

    @Override
    public int getAppType() {
        return (Integer) getAttribute(APP_TYPE);
    }

    public void setAppType(int appType) {
        setAttribute(APP_TYPE, appType);
    }

    @Override
    public int getVersion() {
        return (Integer) getAttribute(VERSION);
    }

    public void setVersion(int version) {
        setAttribute(VERSION, version);
    }

    @Override
    public int getLiveStatus() {
        return (Integer) getAttribute(LIVE_STATUS);
    }

    @Override
    public int getMood() {
        return (Integer) getAttribute(MOOD);
    }

    public void setMood(int mood) {
        setAttribute(REMOTE_PORT, mood);
    }

    @Override
    public String getFullName() {
        return (String) getAttribute(FULL_NAME);
    }

    @Override
    public void setLiveStatus(int liveStatus) {
        setAttribute(LIVE_STATUS, liveStatus);
    }

    @Override
    public IClientPacketSender getClientChannel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
