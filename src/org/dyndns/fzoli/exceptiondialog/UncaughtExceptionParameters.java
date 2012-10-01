package org.dyndns.fzoli.exceptiondialog;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;

/**
 *
 * @author zoli
 */
public final class UncaughtExceptionParameters {
    
    private String title, details, close, copy, selectAll, info;
    private Image dialogIconImage, messageIconImage;
    private Component component;
    private MouseAdapter messageListener;

    public UncaughtExceptionParameters() {
    }

    public UncaughtExceptionParameters(String title, String info, String details, String close, String copy, String selectAll) {
        this(title, info, details, close, copy, selectAll, null);
    }
    
    public UncaughtExceptionParameters(String title, String info, String details, String close, String copy, String selectAll, Component component) {
        this(title, info, details, close, copy, selectAll, null, null, null, component);
    }    
    
    public UncaughtExceptionParameters(String title, String info, String details, String close, String copy, String selectAll, Image dialogIconImage, Image messageIconImage, MouseAdapter messageActionListener, Component component) {
        this.title = title;
        this.info = info;
        this.details = details;
        this.close = close;
        this.copy = copy;
        this.selectAll = selectAll;
        this.component = component;
        this.dialogIconImage = dialogIconImage;
        this.messageIconImage = messageIconImage;
        this.messageListener = messageActionListener;
    }

    public Component getComponent() {
        return component;
    }

    public MouseAdapter getMessageListener() {
        return messageListener;
    }

    public Image getMessageIconImage() {
        return messageIconImage;
    }

    public Image getDialogIconImage() {
        return dialogIconImage;
    }

    public String getInfo() {
        if (info == null) return "<html><strong style=\"color:red\">" + getTitle() + ".</strong></html>";
        return info;
    }

    public String getDetails() {
        if (details == null) return "Details";
        return details;
    }

    public String getOk() {
        if (close == null) return "OK";
        return close;
    }

    public String getSelectAll() {
        return selectAll;
    }

    public String getCopy() {
        if (copy == null) return "Copy";
        return copy;
    }

    public String getTitle() {
        if (title == null) return "Uncaught Exception";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public void setSelectAll(String selectAll) {
        this.selectAll = selectAll;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDialogIconImage(Image dialogIconImage) {
        this.dialogIconImage = dialogIconImage;
    }

    public void setMessageIconImage(Image messageIconImage) {
        this.messageIconImage = messageIconImage;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setMessageListener(MouseAdapter messageListener) {
        this.messageListener = messageListener;
    }

}