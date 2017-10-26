/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session;

import com.bdlions.dto.Credential;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 *
 * @author alamgir
 */
public class UserAuthentificationInfo extends Credential implements AuthenticationInfo {
    
    private final String username;
    private final String password;

    public UserAuthentificationInfo(String username, String password) {
        this.username = username;
        this.password = password;
        super.setUserName(username);
        super.setPassword(password);
    }

    @Override
    public PrincipalCollection getPrincipals() {
        PrincipalCollection collection = new SimplePrincipalCollection(username, username);
        return collection;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

}
