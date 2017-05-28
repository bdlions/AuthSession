/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bdlions.session.db;

import com.auction.dto.Credential;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author alamgir
 */
public interface IDBUserProvider {
    Credential getLoggedInUserByUserName(String userName);
    Credential getUser(Credential credential);
    Set<String> getUserRoles(String userName);
    Collection<String> getPermissionsByRole(Set<String> roles);
    Collection<String> getUserPermissions(String userName);
}
