/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session;

import java.net.InetAddress;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author alamgir
 */
public interface ISession {
    public String getUserName();
    public long getUserId();
    public String getSessionId();
    public int getDevice();
    public int getRemotePort();
    public InetAddress getRemoteIP();
    public void setRemotePort(int port);
    public void setRemoteIP(InetAddress remoteAddress);
    public int getAppType();
    public int getVersion();
    public int getLiveStatus();
    public int getMood();
    public String getFullName();
    public void setLiveStatus(int liveStatus);
    public Subject getUser();
}
