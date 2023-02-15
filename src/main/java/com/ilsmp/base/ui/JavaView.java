package com.ilsmp.base.ui;

import javax.swing.*;

public class JavaView {
    private JPanel myMainPanel;
    private JTextField basePackageTextField;

    public JTextField getBasePackageTextField() {
        return basePackageTextField;
    }

    public JComponent getComponent() {
        return myMainPanel;
    }
}
