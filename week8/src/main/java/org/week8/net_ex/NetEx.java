package org.week8.net_ex;

import org.week8.net_ex.component.NetApp;

import static javax.swing.SwingUtilities.invokeLater;

public class NetEx {
    public static void main(String[] args) {
        invokeLater(NetApp::new);
    }
}