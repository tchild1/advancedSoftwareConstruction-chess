package requests;

/**
 * base class of all request objects
 */
public class Request {

    /**
     * enum stating all possible request methods
     */
    public enum RequestMethods {
        DELETE,
        POST,
        PUT,
        GET
    }

    /**
     * type of request being made
     */
    RequestMethods method;

    /**
     * Constructor of Base class for all requests
     *
     * @param method of request being made
     */
    public Request(RequestMethods method) {
        this.method = method;
    }

    public RequestMethods getMethod() {
        return this.method;
    }

    public void setMethod(RequestMethods method) {
        this.method = method;
    }
}
