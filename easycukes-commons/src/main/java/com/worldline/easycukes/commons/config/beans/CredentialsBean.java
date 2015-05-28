package com.worldline.easycukes.commons.config.beans;

import com.worldline.easycukes.commons.config.InjectableConfiguration;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * * Simple POJO allowing to store the main credentials used in the application to be tested.
 *
 * @author aneveux
 * @version 1.0
 */
@NoArgsConstructor
@Data
public class CredentialsBean extends InjectableConfiguration {

    /**
     * Main login for the application
     */
    public String login;

    /**
     * Main password for the application
     */
    public String password;

    @Override
    public String inject(@NonNull String token) {
        switch (token) {
            case "login":
                return login;
            case "password":
                return password;
        }
        return null;
    }
}
