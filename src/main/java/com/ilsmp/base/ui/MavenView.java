package com.ilsmp.base.ui;

import javax.swing.*;

public class MavenView {
    private JPanel myMainPanel;
    private JTextField groupIdTextField;
    private JTextField artifactIdTextField;
    private JTextField versionTextField;
    private JTextField basePackageTextField;
    private JSeparator mySeparator;

    public JTextField getArtifactIdTextField() {
        return artifactIdTextField;
    }

    public JTextField getVersionTextField() {
        return versionTextField;
    }

    public JTextField getGroupIdTextField() {
        return groupIdTextField;
    }

    public JTextField getBasePackageTextField() {
        return basePackageTextField;
    }

    public JComponent getComponent() {
        return myMainPanel;
    }

}
