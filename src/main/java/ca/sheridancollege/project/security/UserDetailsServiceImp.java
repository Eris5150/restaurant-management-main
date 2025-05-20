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
 * A service implementation for loading user-specific details required for authentication
 * in a Spring Security context. This class interacts with the database to retrieve user
 * information and associated roles for Spring Security's authentication process.
 *
 * This service extends the {@code UserDetailsService} interface provided by Spring Security.
 * It uses a {@code DatabaseAccess} object to query the database for user and role information.
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ca.sheridancollege.project.beans.User user = da.findUserAccount(username);
        if (user == null) {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User " + username + "Was not found in the database");
        }
        List<String> roleNameList = da.getRolesById(user.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNameList != null) {
            for (String role: roleNameList){
                grantList.add(new SimpleGrantedAuthority(role));
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), grantList);
        return userDetails;
    }
}
