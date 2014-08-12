package com.wq.utils.datePicker;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import sun.util.calendar.ZoneInfo;

/**
 * 日期输入框.
 * 
 * @author wuqing
 * 
 */
public class DateTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private DatePicker dp;
	private JDialog dlg;
	private Point origin = new Point();
	private String dateFormate = "yyyy-MM-dd";

	public DateTextField() {
		initDialog();
	}

	public void setDateFormate(String dateFormate) {
		this.dateFormate = dateFormate;
	}

	public void initDialog() {

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				setText("");
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}

		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				onTextFieldClick();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * 日期转字符串.
	 * 
	 * @param dt
	 * @return
	 */
	private String dateToString(final Date dt) {
		if (null != dt) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
			TimeZone zone = new ZoneInfo("GMT", 0);
			sdf.setTimeZone(zone);
			return sdf.format(dt);
		}
		return null;
	}

	/**
	 * 字符串转日期.
	 * 
	 * @param s
	 * @return
	 */
	private Date stringToDate(final String s) {
		try {
			return DateFormat.getDateInstance(DateFormat.LONG).parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期窗口监听器.
	 * 
	 * @author wuqing
	 * 
	 */
	final class Listener extends ComponentAdapter {

		public void componentHidden(final ComponentEvent evt) {
			Date dt = ((DatePicker) evt.getSource()).getDate();
			dt = new Date(dt.getTime() + Long.parseLong("86400000"));
			if (null != dt)
				setText(dateToString(dt));
			dlg.dispose();
		}

	}

	/**
	 * 编辑框点击事件.
	 * 
	 * @param evt
	 */
	private void onTextFieldClick() {
		if ("".equals(getText()))
			dp = new DatePicker();
		else
			dp = new DatePicker(stringToDate(getText()));
		dp.addComponentListener(new Listener());

		final Point p = getLocationOnScreen();
		p.setLocation(p.getX(), p.getY() - 1 + getSize().getHeight());

		dlg = new JDialog(new JFrame(), true);

		dlg.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		dlg.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = dlg.getLocation();
				dlg.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
						- origin.y);
			}
		});

		dlg.setLocation(p);
		dlg.setResizable(false);
		dlg.setUndecorated(true);
		dlg.getContentPane().add(dp);
		dlg.pack();
		dlg.setVisible(true);
	}

}
