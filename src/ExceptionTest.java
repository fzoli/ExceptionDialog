import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import org.dyndns.fzoli.component.LinkLabel;
import org.dyndns.fzoli.exceptiondialog.UncaughtExceptionDialog;
import org.dyndns.fzoli.exceptiondialog.UncaughtExceptionParameters;
import org.dyndns.fzoli.exceptiondialog.event.UncaughtExceptionEvent;
import org.dyndns.fzoli.exceptiondialog.event.UncaughtExceptionListener;

/**
 *
 * @author zoli
 */
public class ExceptionTest {
    
    private static void recursiveCall(String[] st, int i) {
        String s = st[i];
        recursiveCall(st, ++i);
    }
    
    private static void exceptionTest() {
        final JLabel lb = new JLabel("Visszaszámlálás: ");
        final LinkLabel llb = new LinkLabel();
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.add(lb);
        p.add(llb);
        UncaughtExceptionDialog.applyHandler(new UncaughtExceptionParameters("Nem várt hiba történt", "<html><strong style=\"color:red\">Nem várt hiba történt!</strong><br>Kérem, jelezze a fejlesztőnek.</html>", "Részletek", null, "Másolás", "Minden kijelölése", p), new UncaughtExceptionListener() {
            
            private int count = 0;
            private int countdown = 10;
            UncaughtExceptionEvent exev;
            
            private ActionListener al = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (countdown <= ++count) {
                        setComponent(new JLabel("Ideje bezárni az ablakot."));
                        timer.stop();
                    }
                    else {
                        setText();
                    }
                }
                
            };
            
            private void setComponent(Component c) {
                exev.getSource().setComponent(c);
            }
            
            private void setText() {
                llb.setLinkText(Integer.toString(countdown - count));
            }
            
            private Timer timer = new Timer(1000, al);
            
            @Override
            public void exceptionThrown(UncaughtExceptionEvent e) {
                this.exev = e;
                timer.start();
                setText();
                e.getSource().addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {
                        timer.stop();
                    }
                    
                });
                llb.addMouseListener(new MouseAdapter() {
                    
                    private int clickCount = 0;
                    
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (llb.isOnText(e.getX()) && ++clickCount >= 5) {
                            timer.stop();
                            setComponent(new JLabel("\"Csodák nincsenek.\""));
                        }
                    }
                    
                });
            }
            
        });
        String[] st = new String[20];
        recursiveCall(st, 0);
    }
    
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //JOptionPane.showMessageDialog(null, "msg", "title", JOptionPane.ERROR_MESSAGE);
        //UIManager.setLookAndFeel(new NimbusLookAndFeel());
        exceptionTest();
    }
    
}