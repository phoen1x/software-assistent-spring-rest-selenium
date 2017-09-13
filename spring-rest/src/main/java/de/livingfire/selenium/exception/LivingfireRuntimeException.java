package de.livingfire.selenium.exception;

public class LivingfireRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LivingfireRuntimeException() {
        super();
    }

    public LivingfireRuntimeException(String message,
                                      Throwable cause) {
        super(message, cause);
    }

    public LivingfireRuntimeException(String message) {
        super(message);
    }

    public LivingfireRuntimeException(Throwable cause) {
        super(cause);
    }
}
