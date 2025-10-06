package ca.sheridancollege.project.security;

import ca.sheridancollege.project.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom UserDetailsService implementation for Spring Security.
 * Loads user credentials and roles from the database for authentication.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    @Lazy
    private DatabaseAccess da; // Injected data access for user lookup

    /** Retrieves user details and granted authorities for authentication. */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ca.sheridancollege.project.beans.User user = da.findUserAccount(username);
        if (user == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        List<String> roleNameList = da.getRolesById(user.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();

        if (roleNameList != null) {
            for (String role : roleNameList) {
                grantList.add(new SimpleGrantedAuthority(role));
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getEncryptedPassword(),
                grantList
        );
    }
}
