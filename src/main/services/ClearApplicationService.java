package services;

import requests.ClearApplicationRequest;
import resposnses.ClearApplicationResponse;

/**
 * service for requests to clear all application data
 */
public class ClearApplicationService {

    /**
     * Constructor Creating class to clear application data
     */
    public ClearApplicationService() {

    }

    /**
     * This method checks the user's AuthToken,
     * then if authorized connects to the DAOs and clears all users, games, and authTokens.
     *
     * @param request object with AuthToken and method of the request
     * @return returns a response object
     */
    public ClearApplicationResponse clearApplication(ClearApplicationRequest request) {
        return null;
    }
}
