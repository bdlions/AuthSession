/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session;

import com.auction.dto.Credential;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.bdlions.session.db.IDBUserProvider;

/**
 *
 * @author alamgir
 */
public class UserSessionManagerImpl implements ISessionManager {

    private final SecurityManager securityManager;
    private final HashMap<String, HashMap<Integer, String>> deviceUserSessions;
    private final int SESSION_IDLE_TIMEOUT = 1000 * 300;
    private final IDBUserProvider userProvider;

    public UserSessionManagerImpl(IDBUserProvider userProvider) {
        this.userProvider = userProvider;
        securityManager = new DefaultSecurityManager(new UserRealm(userProvider));
        SecurityUtils.setSecurityManager(securityManager);
        deviceUserSessions = new HashMap<>();
    }

    @Override
    public ISession getSessionBySessionId(String sessionId) {
        UserSessionImpl session = null;
        
        try{
            Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
            if (subject.getSession(false) == null) {
                return null;
            }
            session = new UserSessionImpl(subject);
        }catch(UnknownSessionException ex){
            
        }
        return session;
    }

    @Override
    public ISession getOnlineSessionByUserId(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ISession createSession(Credential credential) throws UnknownAccountException{
        ISession prevSession = getSession(credential.getUserName(), credential.getDevice());
        if (prevSession != null) {
            return prevSession;
        }

        Subject currentUser = new Subject.Builder().buildSubject();
        
        if (currentUser.isAuthenticated()) {
            return getSessionBySessionId((String)currentUser.getSession().getId());
        }
        else{
            UsernamePasswordToken token = new UsernamePasswordToken(credential.getUserName(), credential.getPassword());
            token.setRememberMe(true);
            currentUser.login(token);
            
        }
        
        UserSessionImpl session = new UserSessionImpl(currentUser);
        
        credential = userProvider.getLoggedInUserByUserName(credential.getUserName());
        
        session.setAppType(credential.getAppType());
        session.setDevice(credential.getDevice());
        session.setLiveStatus(credential.getLiveStatus());
        session.setMood(credential.getMood());
        session.setRemoteIP(credential.getRemoteIP());
        session.setRemotePort(credential.getRemotePort());
        session.setUserId(credential.getUserId());
        session.setUserName(credential.getUserName());
        session.setVersion(credential.getVersion());
        session.setTimeout(SESSION_IDLE_TIMEOUT);

        putDeviceSession(credential.getUserName(), credential.getDevice(), session.getSessionId());
        return session;
    }

    @Override
    public void destroySession(String sessionId) {
        Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        subject.getSession().stop();
    }

    @Override
    public void updateSession(String sessionId, InetAddress address, int port) {
        Subject subject = new Subject.Builder().sessionId(sessionId).buildSubject();
        UserSessionImpl session = new UserSessionImpl(subject);
        session.setRemoteIP(address);
        session.setRemotePort(port);
        session.touch();
    }

    @Override
    public ISession getSession(String userName, int device) {
        if (deviceUserSessions.containsKey(userName)) {
            HashMap<Integer, String> sessions = deviceUserSessions.get(userName);
            if (sessions.containsKey(device)) {
                String sessionId = sessions.get(device);
                ISession session = getSessionBySessionId(sessionId);
                if (session != null) {
                    return session;
                } else {
                    sessions.remove(device);
                }

            }
        }
        return null;
    }

    public void putDeviceSession(String userName, int device, String sessionId) {
        if (!deviceUserSessions.containsKey(userName)) {
            deviceUserSessions.put(userName, new HashMap<>());
        }
        HashMap<Integer, String> sessions = deviceUserSessions.get(userName);
        sessions.put(device, sessionId);
    }

    @Override
    public ISession getLatestSession(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ISession> getSessions(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
