package com.worldline.easycukes.commons.config;

/**
 * InjectableConfiguration allows to define Configuration properties that can be used by the EasyCukes DataInjector.
 * Any configuration bundle can simply extend this class and implement the #inject method in order to be allow being injected.
 *
 * @author aneveux
 * @version 1.0
 */
public abstract class InjectableConfiguration {

    /**
     * To be implemented, will be called from the DataInjector.
     * You'll receive the token the DataInjector tries to replace, and will be able to inject whatever you want as a value if needed.
     * Simply return the value you want to inject, or null otherwise.
     *
     * @param token The token read by the DataInjector that needs to be replaced by a value
     * @return the value you want to inject, or null
     */
    public abstract String inject(String token);

}
