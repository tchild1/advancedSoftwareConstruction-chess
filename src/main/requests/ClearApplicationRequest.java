package requests;

import models.AuthToken;

/**
 * Class representing a request to clear all application data
 */
public class ClearApplicationRequest extends Request {

    /**
     * AuthToken authorizing this request
     */
    AuthToken authToken;

    /**
     * Constructor creating a ClearApplicationRequest object with
     * user's AuthToken and request method set to DELETE
     *
     * @param token -AuthToken of the user making the request
     */
    public ClearApplicationRequest(AuthToken token) {
        super(RequestMethods.DELETE);
        this.authToken = token;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
