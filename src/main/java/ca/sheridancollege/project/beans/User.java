package ca.sheridancollege.project.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a system user with login credentials and account status.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    private Long userId; // Database identifier

    @NonNull
    private String email; // User's login email

    @NonNull
    private String encryptedPassword; // Hashed password for authentication

    @NonNull
    private Boolean enabled; // Indicates if the account is active
}
