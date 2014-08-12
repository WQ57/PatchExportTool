package com.wq.view;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import com.wq.compent.DateTextField;

import java.awt.Font;
import java.awt.Color;

public class TestView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private DateTextField jTextField = null;
	private JPasswordField jPasswordField = null;
	private JTextArea jTextArea = null;
	private JCheckBox jCheckBox = null;
	private JRadioButton jRadioButton = null;
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public TestView() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(701, 305);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(55, 162, 39, 41));
			ImageIcon image = new ImageIcon(getClass().getResource(
					"/img/user1.png"));
			image.setImage(image.getImage().getScaledInstance(
					jLabel.getWidth(), jLabel.getHeight(), Image.SCALE_DEFAULT));
			jLabel.setIcon(image);
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJTextArea(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJRadioButton(), null);
			jContentPane.add(jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private DateTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new DateTextField();
			jTextField.setBounds(new Rectangle(18, 14, 145, 26));
			jTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextField.setForeground(new Color(45, 51, 51));
		}
		jTextField.setText("");
		jTextField.getText();
		return jTextField;
	}

	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(187, 11, 155, 25));
			jPasswordField.setEditable(true);
		}
		jPasswordField.setText("");
		jPasswordField.getText();
		return jPasswordField;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(18, 49, 324, 79));
		}
		jTextArea.setText("");
		jTextArea.getText();
		return jTextArea;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(482, 22, 48, 33));
		}
		jCheckBox.isSelected();
		System.out.println(jCheckBox.isSelected());
		jCheckBox.doClick();
		System.out.println(jCheckBox.isSelected());
		return jCheckBox;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(new Rectangle(482, 63, 94, 33));
		}
		jRadioButton.isSelected();
		jRadioButton.doClick();
		return jRadioButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
