package org.dyndns.fzoli.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Olyan label, ami úgy néz ki, mint ha link lenne.
 * @author zoli
 */
public class LinkLabel extends JLabel {
    
    private boolean last = false;
    
    private boolean hover;
    private String text;
    private Color defaultColor = Color.BLUE, pressedColor = Color.RED;

    public LinkLabel() {
        this(null);
    }

    public LinkLabel(String text) {
        this(text, true);
    }
    
    public LinkLabel(String text, boolean hover) {
        if (text == null) text = " ";
        this.text = text;
        this.hover = hover;
        setText(getDefaultText());
        setForeground(defaultColor);
        MouseAdapter ma = new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (last) {
                    setForeground(pressedColor);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setForeground(defaultColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setText(getDefaultText());
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                last = false;
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean b = isOnText(e.getX());
                if (b != last) {
                    setText(b ? getHandText() : getDefaultText());
                    setCursor(new Cursor(b ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
                }
                last = b;
            }
            
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public boolean isOnText(int x) {
        FontMetrics fm = getFontMetrics(getFont());
        return x <= fm.stringWidth(LinkLabel.this.text);
    }
    
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }
    
    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public void setLinkText(String text) {
        this.text = text;
        setText(last ? getHandText() : getDefaultText());
    }
    
    private String getDefaultText() {
        if (!isHover()) return getHandText();
        return text;
    }
    
    private String getHandText() {
        return "<html><u>" + text + "</u></html>";
    }
    
}