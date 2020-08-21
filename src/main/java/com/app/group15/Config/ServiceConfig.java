package com.app.group15.Config;

import com.app.group15.UserManagement.SessionManagement.AuthorizationService;
import com.app.group15.UserManagement.SessionManagement.IAuthorizationService;

public class ServiceConfig {
    private static ServiceConfig singletonServiceConfig = null;

    private IAuthorizationService authorizationService;

    private ServiceConfig() {
        authorizationService = new AuthorizationService();
    }

    public static ServiceConfig getInstance() {
        if (null == ServiceConfig.getUniqueInstance()) {
            singletonServiceConfig = new ServiceConfig();
        }
        return ServiceConfig.singletonServiceConfig;
    }

    private static ServiceConfig getUniqueInstance() {
        return singletonServiceConfig;
    }

    public IAuthorizationService getAuthorizationService() {
        return authorizationService;
    }

}
