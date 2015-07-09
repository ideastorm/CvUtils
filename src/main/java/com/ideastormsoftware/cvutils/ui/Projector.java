package com.ideastormsoftware.cvutils.ui;

import com.ideastormsoftware.cvutils.sources.ScaledSource;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Projector extends JFrame {

    RenderPane renderPane;

    public Projector(ScaledSource source) throws HeadlessException {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        renderPane = new RenderPane(source);
        add(renderPane);
        setTitle("Presmedia Projector");
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            moveToSecondaryScreen();
        }
        super.setVisible(visible);
    }

    private void moveToSecondaryScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        boolean multiMonitor = gd.length > 1;
        if (multiMonitor) {
            DisplayMode currentMode = gd[1].getDisplayMode();
            Dimension primaryMonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(primaryMonitorSize.width, 0);
            setSize(currentMode.getWidth(), currentMode.getHeight());
        } else {
            setLocation(0, 0);
            setSize(640, 480);
        }
        setUndecorated(multiMonitor);
        setAlwaysOnTop(multiMonitor);
    }
    
    public Dimension getRenderSize()
    {
        return renderPane.getSize();
    }           

}