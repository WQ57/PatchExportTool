package com.wq.compent;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.wq.utils.ObjectUtils;
import com.wq.utils.ValueUtils;

/**
 * Êý×ÖÊäÈë¿ò.
 * 
 * @author wuqing
 * 
 */
public class NumberTextField extends JTextField {

	public static final int TYPE_INTEGER = 0;
	public static final int TYPE_DOUBLE = 1;
	private int type = TYPE_INTEGER;

	private static final long serialVersionUID = 1L;

	public NumberTextField() {
		this.addFocusListener(focusListener);
	}

	public NumberTextField(int type) {
		this.type = type;
		this.addFocusListener(focusListener);
	}

	private FocusListener focusListener = new FocusListener() {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusLost(FocusEvent e) {
			String str = getText();
			if (!ObjectUtils.isEmpty(str)) {
				try {
					if (type == TYPE_INTEGER) {
						Integer.parseInt(str);
					} else if (type == TYPE_DOUBLE) {
						setText(ValueUtils.getString(Double.parseDouble(str)));
					}
				} catch (NumberFormatException e1) {
					setText("");
				}
			}
		}

	};

}
