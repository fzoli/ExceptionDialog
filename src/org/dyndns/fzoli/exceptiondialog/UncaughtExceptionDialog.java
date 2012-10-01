package org.dyndns.fzoli.exceptiondialog;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import org.dyndns.fzoli.exceptiondialog.event.UncaughtExceptionEvent;
import org.dyndns.fzoli.exceptiondialog.event.UncaughtExceptionListener;
import org.dyndns.fzoli.exceptiondialog.event.UncaughtExceptionSource;

public final class UncaughtExceptionDialog extends JDialog {
    
    private JPanel pc = new JPanel(new GridLayout());
    
    private UncaughtExceptionDialog(Dialog.ModalityType modalityType, String s, UncaughtExceptionParameters params) {
        setResizable(false);
        setTitle(params.getTitle());
        setIconImage(params.getDialogIconImage());
        setModalityType(modalityType);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        final JTextArea ta = new JTextArea(s);
        ta.setOpaque(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        final JScrollPane sp = new JScrollPane(ta);
        ta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(sp, gbc);
        final JToggleButton btDetails = new JToggleButton("« " + params.getDetails());
        btDetails.setOpaque(false);
        btDetails.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setDetailsVisible(sp, btDetails.isSelected());
            }
            
        });
        JButton btOk = new JButton(params.getOk());
        btOk.setOpaque(false);
        btOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
            
        });
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 5, 10);
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setOpaque(false);
        p.add(btDetails);
        p.add(btOk);
        add(p, gbc);
        JPanel pi = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel li = createMessageIconLabel(params.getMessageIconImage());
        if (li != null) {
            li.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            pi.add(li);
        }
        final JLabel lb = new JLabel(params.getInfo());
        lb.setOpaque(false);
        MouseAdapter lbma = params.getMessageListener();
        if (lbma != null) {
            lb.addMouseListener(lbma);
            lb.addMouseMotionListener(lbma);
            lb.addMouseWheelListener(lbma);
        }
        JPanel pci = new JPanel(new GridBagLayout());
        pci.setOpaque(false);
        GridBagConstraints pcc = new GridBagConstraints();
        pcc.anchor = GridBagConstraints.LINE_START;
        pci.add(lb, pcc);
        pi.add(pci);
        setComponent(params.getComponent());
        pcc.gridy = 1;
        pcc.fill = GridBagConstraints.BOTH;
        pcc.insets = new Insets(5, 0, 0, 0);
        pci.add(pc, pcc);
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        add(pi, gbc);
        Dimension d = new Dimension(400, 150);
        sp.setMinimumSize(d);
        sp.setPreferredSize(d);
        pack();
        btOk.setPreferredSize(btDetails.getSize());
        int w = sp.getSize().width;
        p.setPreferredSize(new Dimension(w, p.getSize().height));
        pi.setPreferredSize(new Dimension(w, pi.getSize().height));
        setLocationRelativeTo(this);
        ta.setPreferredSize(ta.getSize());
        ta.setEditable(false);
        setDetailsVisible(sp, false);
        final JPopupMenu pm = new JPopupMenu();
        final JMenuItem mic = new JMenuItem(params.getCopy());
        final JMenuItem misa = new JMenuItem(params.getSelectAll());
        ActionListener al = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mic) {
                    ta.copy();
                }
                else if (e.getSource() == misa) {
                    ta.requestFocus();
                    ta.selectAll();
                }
            }
            
        };
        mic.addActionListener(al);
        pm.add(mic);
        misa.addActionListener(al);
        pm.add(misa);
        ta.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JTextComponent tc = (JTextComponent) e.getSource();
                if (tc.isEnabled()) {
                    if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
                        tc.selectAll();
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3) {
                        mic.setEnabled(tc.getSelectedText() != null);
                        pm.show(tc, e.getX(), e.getY());
                    }
                }
            }
            
        });
        btOk.requestFocus();
    }
    
    private void setComponent(Component c) {
        pc.setVisible(false);
        pc.removeAll();
        if (c != null) {
            pc.add(c);
            pc.setVisible(true);
        }
    }
    
    private void setDetailsVisible(JScrollPane sp, boolean visible) {
        sp.setVisible(visible);
        pack();
    }
    
    private JLabel createMessageIconLabel(Image img) {
        try {
            if (img == null) {
                Icon icon = UIManager.getIcon("OptionPane.errorIcon");
                img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                icon.paintIcon(this, img.getGraphics(), 0, 0);
            }
            return new JLabel(new ImageIcon(img));
        }
        catch (Exception ex) {
            try {
                LookAndFeel tmp = UIManager.getLookAndFeel();
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                }
                catch (Exception e) {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                }
                if (tmp.equals(UIManager.getLookAndFeel())) return null;
                JLabel lb = createMessageIconLabel(null);
                UIManager.setLookAndFeel(tmp);
                return lb;
            }
            catch(Exception e) {
                ;
            }
            return null;
        }
    }
    
    public static void applyHandler() {
        applyHandler(null);
    }
    
    public static void applyHandler(UncaughtExceptionParameters params) {
        applyHandler(params, null);
    }
    
    public static void applyHandler(UncaughtExceptionParameters params, UncaughtExceptionListener listener) {
        applyHandler(Dialog.ModalityType.APPLICATION_MODAL, params, listener);
    }
    
    public static void applyHandler(final Dialog.ModalityType modalityType, UncaughtExceptionParameters params, UncaughtExceptionListener listener) {
        if (!GraphicsEnvironment.isHeadless()) {
            final UncaughtExceptionParameters uefp = params == null ? new UncaughtExceptionParameters() : params;
            final UncaughtExceptionListener uel = listener;
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    String s = createDetails(e);
                    final UncaughtExceptionDialog d = new UncaughtExceptionDialog(modalityType, s, uefp);
                    UncaughtExceptionSource src = new UncaughtExceptionSource() {

                        @Override
                        public void setComponent(Component c) {
                            d.setComponent(c);
                        }

                        @Override
                        public void addWindowListener(WindowListener listener) {
                            d.addWindowListener(listener);
                        }

                    };
                    if (uel != null) uel.exceptionThrown(new UncaughtExceptionEvent(src, s, t, e));
                    d.setVisible(true);
                }

            });
        }
    }
    
    private static String createDetails(Throwable e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        e.printStackTrace(ps);
        String s = new String(baos.toByteArray());
        s = s.substring(0, s.length() - 1);
        return s;
    }
    
}