package org.dyndns.fzoli.exceptiondialog.event;

/**
 *
 * @author zoli
 */
public class UncaughtExceptionEvent {
    
    private Thread thread;
    private Throwable throwable;
    private String details;
    private UncaughtExceptionSource source;
    
    public UncaughtExceptionEvent(UncaughtExceptionSource source, String details, Thread thread, Throwable throwable) {
        this.thread = thread;
        this.throwable = throwable;
        this.details = details;
        this.source = source;
    }

    public UncaughtExceptionSource getSource() {
        return source;
    }

    public String getDetails() {
        return details;
    }

    public Thread getThread() {
        return thread;
    }

    public Throwable getThrowable() {
        return throwable;
    }
    
}