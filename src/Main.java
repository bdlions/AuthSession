/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.auction.dto.Credential;
import com.auction.util.ACTION;
import org.apache.shiro.subject.Subject;
import org.bdlions.roles.USER_ROLE;
import org.bdlions.session.ISession;
import org.bdlions.session.ISessionManager;
import org.bdlions.session.UserSessionManagerImpl;

/**
 *
 * @author alamgir
 */
public class Main {

    public static void main(String[] args) {
        try {

            Credential user = new Credential();
            user.setUserId(123);
            user.setUserName("alamgir");
            user.setPassword("pass");
            ISessionManager sessionManager = new UserSessionManagerImpl(new MockDBUserProvider());
            ISession session = sessionManager.createSession(user);
            
            session = sessionManager.getSessionBySessionId(session.getSessionId());
            Subject currentUser = session.getUser();
            
            
            if (currentUser.hasRole(USER_ROLE.ADMIN.name())) {
                System.out.println("User has role " + USER_ROLE.ADMIN.name());
            }
            if (!currentUser.hasRole(USER_ROLE.MEMBER.name())) {
                System.out.println("User has no role " + USER_ROLE.MEMBER.name());
            }

            currentUser.checkPermission(ACTION.FETCH_PRODUCT_INFO.name());
            
            String sessionId = (String) currentUser.getSession().getId();
            
            currentUser.getSession().setTimeout(5000);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
