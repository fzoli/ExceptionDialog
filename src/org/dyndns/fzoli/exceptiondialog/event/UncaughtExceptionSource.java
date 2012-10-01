package org.dyndns.fzoli.exceptiondialog.event;

import java.awt.Component;
import java.awt.event.WindowListener;

/**
 *
 * @author zoli
 */
public interface UncaughtExceptionSource {
    
    void setComponent(Component c);
    
    void addWindowListener(WindowListener listener);
    
}