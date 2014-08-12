package com.wq.utils;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Swing������.
 * 
 * @author wuqing
 * 
 */
public class SwingUtils {

	/**
	 * �ļ�ѡ���.
	 * 
	 * @param frame
	 * @param textField
	 */
	public static void fileChooser(JFrame frame, JTextField textField) {
		JFileChooser fDialog = new JFileChooser();
		if (!ObjectUtils.isEmpty(textField.getText())) {
			fDialog = new JFileChooser(new File(textField.getText()));
		}
		fDialog.setFileSelectionMode(JFileChooser.FILES_ONLY); // ����ֻѡ���ļ�
		int result = fDialog.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			String fname = fDialog.getCurrentDirectory().getAbsolutePath()
					+ "\\" + fDialog.getName(fDialog.getSelectedFile());
			textField.setText(fname);
		}
	}

	/**
	 * �����˵��ļ�ѡ���.
	 * 
	 * @param frame
	 * @param textField
	 * @param filters
	 */
	public static void fileChooser(JFrame frame, JTextField textField,
			String[] filters) {
		JFileChooser fDialog = new JFileChooser();
		if (!ObjectUtils.isEmpty(textField.getText())) {
			fDialog = new JFileChooser(new File(textField.getText()));
		}
		fDialog.setFileSelectionMode(JFileChooser.FILES_ONLY); // ����ֻѡ���ļ�
		MyFileFilter filter = new MyFileFilter(filters);
		fDialog.setFileFilter(filter);
		int result = fDialog.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			String fname = fDialog.getCurrentDirectory().getAbsolutePath()
					+ "\\" + fDialog.getName(fDialog.getSelectedFile());
			textField.setText(fname);
		}
	}

	/**
	 * Ŀ¼ѡ���.
	 * 
	 * @param frame
	 * @param textField
	 */
	public static void directoriesChooser(JFrame frame, JTextField textField) {
		JFileChooser fDialog = new JFileChooser();
		if (!ObjectUtils.isEmpty(textField.getText())) {
			fDialog = new JFileChooser(new File(textField.getText()));
		}
		fDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ����ֻѡ��Ŀ¼
		int result = fDialog.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			String fname = fDialog.getCurrentDirectory().getAbsolutePath()
					+ "\\" + fDialog.getName(fDialog.getSelectedFile());
			textField.setText(fname);
		}
	}

	/**
	 * ��ֵ�������ֵ.
	 * 
	 * @param comboBox
	 * @param items
	 */
	@SuppressWarnings("rawtypes")
	public static void resetComboBoxItems(JComboBox comboBox, List items) {
		comboBox.removeAllItems();
		addComboBoxItems(comboBox, items);
	}

	/**
	 * ���������Ԫ��.
	 * 
	 * @param comboBox
	 * @param items
	 */
	@SuppressWarnings("rawtypes")
	public static void addComboBoxItems(JComboBox comboBox, List items) {
		for (int i = 0; i < items.size(); i++) {
			comboBox.addItem(items.get(i));
		}
	}

	/**
	 * ����ͼ��.
	 * 
	 * @param obj
	 * @param img
	 */
	public static void setImg(final Object obj, String img) {
		try {
			int width = (Integer) ObjectUtils.invokeMethod(obj, "getWidth",
					new Object[] {});
			int height = (Integer) ObjectUtils.invokeMethod(obj, "getHeight",
					new Object[] {});
			ImageIcon image = new ImageIcon(new SwingUtils().getClass()
					.getResource(img));
			image.setImage(image.getImage().getScaledInstance(width, height,
					Image.SCALE_DEFAULT));
			if (obj instanceof JLabel) {
				((JLabel) obj).setIcon(image);
			} else if (obj instanceof JButton) {
				((JButton) obj).setIcon(image);
				((JButton) obj).setBorderPainted(false); // �趨button�ı߿����
				((JButton) obj).setContentAreaFilled(false);// ����ɫ͸��
				((JButton) obj).addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent arg0) {

					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						((JButton) obj).setBorderPainted(true); // �趨button�ı߿����
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						((JButton) obj).setBorderPainted(false); // �趨button�ı߿����
						((JButton) obj).setContentAreaFilled(false);// ����ɫ͸��
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						((JButton) obj).setContentAreaFilled(true);// ����ɫ͸��
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						((JButton) obj).setContentAreaFilled(false);// ����ɫ͸��
					}

				});

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
