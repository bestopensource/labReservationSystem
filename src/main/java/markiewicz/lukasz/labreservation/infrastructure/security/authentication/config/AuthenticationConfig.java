/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.infrastructure.security.authentication.config;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login/Login.xhtml"
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:app/reservationdb'}",
        callerQuery = "#{'select password from account where username = ?'}",
        groupsQuery = "#{'select rsys.name from account acc, rolesystem rsys where username = ? and acc.rolesystem_id = rsys.id'}",
        hashAlgorithm = PasswordHash.class,
        priority = 10
)
@ApplicationScoped
public class AuthenticationConfig {
}
