package com.wq.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * �Ӵ���������.
 * 
 * @author wuqing
 * 
 */
public class FrameUtils {

	/**
	 * �ı��༭����.
	 */
	public static Map<String, String> textFieldMap = new HashMap<String, String>();
	/**
	 * �����ͱ༭����.
	 */
	public static Map<String, String> booleanFieldMap = new HashMap<String, String>();

	static {
		// �ı��ͳ�ʼ��.
		textFieldMap.put("javax.swing.JTextField", "javax.swing.JTextField");
		textFieldMap.put("javax.swing.JPasswordField",
				"javax.swing.JPasswordField");
		textFieldMap.put("javax.swing.JTextArea", "javax.swing.JTextArea");
		textFieldMap.put("com.wq.compent.DateTextField", "com.wq.compent.DateTextField");
		textFieldMap.put("com.wq.compent.NumberTextField", "com.wq.compent.NumberTextField");

		// �����ͳ�ʼ��.
		booleanFieldMap.put("javax.swing.JCheckBox", "javax.swing.JCheckBox");
		booleanFieldMap.put("javax.swing.JRadioButton",
				"javax.swing.JRadioButton");
	}

	/**
	 * ���Ӵ������Ķ���Ԫ��ע�뵽vo������ֶ���.
	 * 
	 * @param frame
	 * @param vo
	 * @throws Exception
	 */
	public static void frameToVO(Object frame, Object vo) throws Exception {
		Map<String, Field> frameFields = ObjectUtils.getMapFields(frame
				.getClass());
		Map<String, Field> voFields = ObjectUtils.getMapFields(vo.getClass());
		Set<String> s = frameFields.keySet();
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			String fieldName = it.next();
			Field frameField = frameFields.get(fieldName);
			Field voField = voFields.get(fieldName);
			if (frameField != null && voField != null
					&& !fieldName.equals("class")
					&& !fieldName.equals("serialVersionUID")) {// ������ͬ���ֵĳ�Ա�������.
				Object frameFieldObj = ObjectUtils.getFieldValue(frame,
						fieldName);
				String voFieldType = voField.getType().getName();
				if (textFieldMap.containsKey(frameField.getType().getName())) {// �ı����������
					String frameFieldValue = (String) ObjectUtils.invokeMethod(
							frameFieldObj, "getText", new Object[] {});
					if(!ObjectUtils.isEmpty(frameFieldValue)){
						ObjectUtils.setFieldValue(
								vo,
								fieldName,
								ObjectUtils.castObject(frameFieldValue,
										voField.getType()));
					}
				} else if (booleanFieldMap.containsKey(frameField.getType()
						.getName())) {// ���������������
					Boolean frameFieldValue = (Boolean) ObjectUtils
							.invokeMethod(frameFieldObj, "isSelected",
									new Object[] {});
					if(!ObjectUtils.isEmpty(frameFieldValue)){
						String booleanStr = "0SA";
						if (frameFieldValue.equals(true)) {
							booleanStr = "0SA";
						} else {
							booleanStr = "0SX";
						}
						if ("java.lang.Boolean".equals(voFieldType)
								|| "boolean".equals(voFieldType)) {// ������
							ObjectUtils.setFieldValue(vo, fieldName,
									frameFieldValue);
						} else {// �ַ�������
							ObjectUtils.setFieldValue(vo, fieldName, booleanStr);
						}
					}
				} else {
					if(!ObjectUtils.isEmpty(fieldName)){
						ObjectUtils.setFieldValue(vo, fieldName, frameFieldObj);
					}
				}
			}
		}
	}

	/**
	 * ��vo������ֶ�ע�뵽�Ӵ������Ķ���Ԫ��.
	 * 
	 * @param frame
	 * @param vo
	 * @throws Exception
	 */
	public static void voToFrame(Object vo, Object frame) throws Exception {
		Map<String, Field> frameFields = ObjectUtils.getMapFields(frame
				.getClass());
		Map<String, Field> voFields = ObjectUtils.getMapFields(vo.getClass());
		Set<String> s = frameFields.keySet();
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			String fieldName = it.next();
			Field frameField = frameFields.get(fieldName);
			Field voField = voFields.get(fieldName);
			if (frameField != null && voField != null
					&& !fieldName.equals("class")
					&& !fieldName.equals("serialVersionUID")) {// ������ͬ���ֵĳ�Ա�������.
				Object frameFieldObj = ObjectUtils.getFieldValue(frame,
						fieldName);
				Object voFieldObj = ObjectUtils.getFieldValue(vo, fieldName);
				String voFieldType = voField.getType().getName();
				if (textFieldMap.containsKey(frameField.getType().getName())) {// �ı����������
					ObjectUtils.invokeMethod(frameFieldObj, "setText",
							new Object[] { ValueUtils.getString(voFieldObj) });
				} else if (booleanFieldMap.containsKey(frameField.getType()
						.getName())) {// ���������������
					boolean selected = false;
					if ("java.lang.Boolean".equals(voFieldType)
							|| "boolean".equals(voFieldType)) {// ������
						selected = (Boolean) voFieldObj;
					} else {
						selected = (voFieldObj == null ? false : ValueUtils
								.getBoolean(voFieldObj));
					}
					boolean isSelected = (Boolean) ObjectUtils.invokeMethod(
							frameFieldObj, "isSelected", new Object[] {});
					if (selected != isSelected) {
						ObjectUtils.invokeMethod(frameFieldObj, "doClick",
								new Object[] {});
					}
				} else {
					ObjectUtils.setFieldValue(frame, fieldName, voFieldObj);
				}
			}
		}
	}

	/**
	 * ����Ļ��������ʾ�Ӵ�.
	 * 
	 * @param app
	 */
	public static void showView(JFrame app) {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		app.setLocation(scrSize.width / 2 - app.getWidth() / 2, scrSize.height
				/ 2 - app.getHeight() / 2);
	}

	/**
	 * ����Ļ��������ʾ�Ӵ�.
	 * 
	 * @param top
	 * @param sub
	 */
	public static void showView(JFrame top, JInternalFrame sub) {
		sub.setLocation((top.getWidth() - sub.getWidth()) / 2,
				(top.getHeight() - sub.getHeight()) / 3);
	}

	/**
	 * ������ʾ�Ի���.
	 * 
	 * @param title
	 * @param content
	 */
	public static void alter(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * ��������Ի���.
	 * 
	 * @param title
	 * @param content
	 */
	public static void error(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * ����ѡ��Ի��� .
	 * 
	 * @param title
	 * @param content
	 */
	public static boolean choose(String title, String content) {
		int i = JOptionPane.showConfirmDialog(null, content, title,
				JOptionPane.YES_NO_OPTION);
		if (i == 0) {
			return true;
		} else {
			return false;
		}
	}
}
