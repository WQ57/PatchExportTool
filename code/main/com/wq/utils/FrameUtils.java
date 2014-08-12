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
 * 视窗对象常用类.
 * 
 * @author wuqing
 * 
 */
public class FrameUtils {

	/**
	 * 文本编辑类型.
	 */
	public static Map<String, String> textFieldMap = new HashMap<String, String>();
	/**
	 * 布尔型编辑类型.
	 */
	public static Map<String, String> booleanFieldMap = new HashMap<String, String>();

	static {
		// 文本型初始化.
		textFieldMap.put("javax.swing.JTextField", "javax.swing.JTextField");
		textFieldMap.put("javax.swing.JPasswordField",
				"javax.swing.JPasswordField");
		textFieldMap.put("javax.swing.JTextArea", "javax.swing.JTextArea");
		textFieldMap.put("com.wq.compent.DateTextField", "com.wq.compent.DateTextField");
		textFieldMap.put("com.wq.compent.NumberTextField", "com.wq.compent.NumberTextField");

		// 布尔型初始化.
		booleanFieldMap.put("javax.swing.JCheckBox", "javax.swing.JCheckBox");
		booleanFieldMap.put("javax.swing.JRadioButton",
				"javax.swing.JRadioButton");
	}

	/**
	 * 将视窗包含的对象元素注入到vo对象的字段中.
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
					&& !fieldName.equals("serialVersionUID")) {// 存在相同名字的成员的情况下.
				Object frameFieldObj = ObjectUtils.getFieldValue(frame,
						fieldName);
				String voFieldType = voField.getType().getName();
				if (textFieldMap.containsKey(frameField.getType().getName())) {// 文本类型情况下
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
						.getName())) {// 布尔型类型情况下
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
								|| "boolean".equals(voFieldType)) {// 布尔型
							ObjectUtils.setFieldValue(vo, fieldName,
									frameFieldValue);
						} else {// 字符串类型
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
	 * 将vo对象的字段注入到视窗包含的对象元素.
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
					&& !fieldName.equals("serialVersionUID")) {// 存在相同名字的成员的情况下.
				Object frameFieldObj = ObjectUtils.getFieldValue(frame,
						fieldName);
				Object voFieldObj = ObjectUtils.getFieldValue(vo, fieldName);
				String voFieldType = voField.getType().getName();
				if (textFieldMap.containsKey(frameField.getType().getName())) {// 文本类型情况下
					ObjectUtils.invokeMethod(frameFieldObj, "setText",
							new Object[] { ValueUtils.getString(voFieldObj) });
				} else if (booleanFieldMap.containsKey(frameField.getType()
						.getName())) {// 布尔型类型情况下
					boolean selected = false;
					if ("java.lang.Boolean".equals(voFieldType)
							|| "boolean".equals(voFieldType)) {// 布尔型
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
	 * 在屏幕正中央显示视窗.
	 * 
	 * @param app
	 */
	public static void showView(JFrame app) {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		app.setLocation(scrSize.width / 2 - app.getWidth() / 2, scrSize.height
				/ 2 - app.getHeight() / 2);
	}

	/**
	 * 在屏幕正中央显示视窗.
	 * 
	 * @param top
	 * @param sub
	 */
	public static void showView(JFrame top, JInternalFrame sub) {
		sub.setLocation((top.getWidth() - sub.getWidth()) / 2,
				(top.getHeight() - sub.getHeight()) / 3);
	}

	/**
	 * 弹出提示对话框.
	 * 
	 * @param title
	 * @param content
	 */
	public static void alter(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 弹出错误对话框.
	 * 
	 * @param title
	 * @param content
	 */
	public static void error(String title, String content) {
		JOptionPane.showMessageDialog(null, content, title,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * 弹出选择对话框 .
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
