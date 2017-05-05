/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session;

import com.auction.dto.Credential;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bdlions.session.db.IDBUserProvider;

/**
 *
 * @author alamgir
 */
public class UserRealm extends AuthorizingRealm {

    private final IDBUserProvider userProvider;
    
    public UserRealm(IDBUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
        AuthenticationInfo info = new UserAuthentificationInfo(userPassToken.getUsername(), new String(userPassToken.getPassword()));
        return (AuthenticationInfo)userProvider.getUser((Credential)info);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = (String)principals.getPrimaryPrincipal();
        
        Set<String> roles = userProvider.getUserRoles(userName);
        Collection<String> permissions = userProvider.getUserPermissions(userName);
        
        authorizationInfo.addStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;

    }

}
