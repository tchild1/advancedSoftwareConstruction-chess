package requests;

import models.AuthToken;

/**
 * Class representing a request to clear all application data
 */
public class ClearApplicationRequest {

    /**
     * AuthToken authorizing this request
     */
    AuthToken authToken;

    /**
     * Constructor creating a ClearApplicationRequest object
     *
     * @param token AuthToken of the user making the request
     */
    public ClearApplicationRequest(AuthToken token) {
        this.authToken = token;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
