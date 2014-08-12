package com.wq.compent;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 统计树单元格渲染.
 * 
 * @author wuqing
 * 
 */
public class CountTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 7310065334403971387L;

	public static final int NORMAL_IMG_WIDTH = 18;
	public static final int NORMAL_IMG_HEIGHT = 18;

	public static final int BIG_IMG_WIDTH = 30;
	public static final int BIG_IMG_HEIGHT = 30;

	public CountTreeCellRenderer() {

	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);
		WqTreeNode node = (WqTreeNode) value;
		String type = node.getNodeType();
		if (type != null) {
			if (type.equals("root")) {
				setIconByImageIcon("/img/treeNodeRoot01.png", BIG_IMG_WIDTH,
						BIG_IMG_HEIGHT);
			} else if (type.equals("year")) {
				setIconByImageIcon("/img/treeNodeYear.png", NORMAL_IMG_WIDTH,
						NORMAL_IMG_HEIGHT);
			} else if (type.equals("month")) {
				setIconByImageIcon("/img/treeNodeMonth.png", NORMAL_IMG_WIDTH,
						NORMAL_IMG_HEIGHT);
			} else if (type.equals("day")) {
				setIconByImageIcon("/img/treeNodeDay.png", NORMAL_IMG_WIDTH,
						NORMAL_IMG_HEIGHT);
			} else {
				setIconByImageIcon("/img/treeNodeItem.png", NORMAL_IMG_WIDTH,
						NORMAL_IMG_HEIGHT);
			}
		}
		return this;

	}

	/**
	 * 设置图标.
	 * 
	 * @param img
	 */
	private void setIconByImageIcon(String img, int width, int height) {
		ImageIcon image = new ImageIcon(new CountTreeCellRenderer().getClass()
				.getResource(img));
		image.setImage(image.getImage().getScaledInstance(width, height,
				Image.SCALE_DEFAULT));
		this.setIcon(image);
	}

}
