package org.week8.font_ex;

import org.week8.font_ex.component.FontApp;

import static javax.swing.SwingUtilities.invokeLater;


public class FontEx {
    public static void main(String[] args) {
        invokeLater(FontApp::new);
    }
}