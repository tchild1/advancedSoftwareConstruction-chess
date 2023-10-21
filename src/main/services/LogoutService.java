package services;

import requests.LogoutRequest;
import resposnses.LogoutResponse;

/**
 * service for a request to logout
 */
public class LogoutService {

    /**
     * Logs out the user represented by the AuthToken
     *
     * @param request object with User's AuthToken
     * @return object with message if failure
     */
    public LogoutResponse logout(LogoutRequest request) {
        return null;
    }
}
