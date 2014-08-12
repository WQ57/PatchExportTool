package com.wq.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.wq.service.JavaFileService;
import com.wq.service.imp.JavaFileServiceImp;
import com.wq.sys.SysConstant;
import com.wq.utils.DateUtils;
import com.wq.utils.FrameUtils;
import com.wq.utils.LogUtils;
import com.wq.utils.ObjectUtils;
import com.wq.utils.SwingUtils;
import com.wq.vo.ProjInfoVO;
import com.wq.vo.Result;

public class ExportMainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private LogUtils log = new LogUtils(ExportMainView.class); // @jve:decl-index=0:
	private JavaFileService javaFileService = new JavaFileServiceImp(); // @jve:decl-index=0:
	private List<ProjInfoVO> commonProjList = null; // @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JComboBox commonProjNames = null;
	private JLabel jLabel1 = null;
	private JTextField projName = null;
	private JButton jButton_add = null;
	private JButton jButton_del = null;
	private JLabel jLabel2 = null;
	private JTextField projPath = null;
	private JButton jButton_projPath = null;
	private JLabel jLabel4 = null;
	private JTextField exportPath = null;
	private JButton jButton_exportPath = null;
	private JScrollPane jScrollPane = null;
	private JTextArea out_put = null;
	private JButton jButton_OK = null;
	private JButton jButton_cancel = null;
	private JLabel jLabel5 = null;
	private JTextField excelPath = null;
	private JButton jButton_excelPath = null;
	private JButton jButton_outputClear = null;

	/**
	 * This is the default constructor
	 */
	public ExportMainView() {
		super();
		initialize();
		resetCommonProjList();
	}

	/**
	 * 重置常用项目下拉框.
	 */
	private void resetCommonProjList() {
		commonProjList = javaFileService.getCommonProjInfo();
		this.getCommonProjNames().removeAllItems();
		for (int i = 0; i < commonProjList.size(); i++) {
			this.getCommonProjNames().addItem(
					commonProjList.get(i).getProjName());
		}
	}

	/**
	 * 添加常用项目.
	 */
	private boolean addCommonProjList() {
		ProjInfoVO vo = new ProjInfoVO();
		try {
			FrameUtils.frameToVO(ExportMainView.this, vo);
			if (ObjectUtils.isEmpty(vo.getProjName())) {
				FrameUtils.alter("提示", "'项目名称'不能为空!");
				return false;
			}
			if (ObjectUtils.isEmpty(vo.getProjPath())) {
				FrameUtils.alter("提示", "'项目根目录'不能为空!");
				return false;
			}
			if (ObjectUtils.isEmpty(vo.getExportPath())) {
				FrameUtils.alter("提示", "'导出路径'不能为空!");
				return false;
			}
			if (ObjectUtils.isEmpty(vo.getExcelPath())) {
				FrameUtils.alter("提示", "'excel路径'不能为空!");
				return false;
			}
			commonProjList.add(vo);
			boolean result = javaFileService
					.wirteCommonProjInfo(commonProjList);
			if (result) {
				resetCommonProjList();
				FrameUtils.alter("提示", "操作成功！");
			} else {
				FrameUtils.error("错误", "操作失败！请联系WQ~~~");
			}
		} catch (Exception e) {
			log.err(e.getMessage());
			FrameUtils.error("错误", "操作失败！请联系WQ~~~");
			return false;
		}
		return true;
	}

	/**
	 * 删除常用项目.
	 */
	private void delCommonProjList() {
		int index = this.getCommonProjNames().getSelectedIndex();
		if (index != -1) {
			commonProjList.remove(index);
			boolean result = javaFileService
					.wirteCommonProjInfo(commonProjList);
			if (result) {
				resetCommonProjList();
				FrameUtils.alter("提示", "操作成功！");
			} else {
				FrameUtils.error("错误", "操作失败！请联系WQ~~~");
			}
		} else {
			FrameUtils.alter("提示", "请选择常用项目!");
		}
	}

	/**
	 * 复制文件.
	 */
	private boolean exportFiles() {
		ProjInfoVO vo = new ProjInfoVO();
		try {
			FrameUtils.frameToVO(ExportMainView.this, vo);
			if (ObjectUtils.isEmpty(vo.getProjPath())) {
				FrameUtils.alter("提示", "'项目根目录'不能为空!");
				return false;
			}
			if (ObjectUtils.isEmpty(vo.getExportPath())) {
				FrameUtils.alter("提示", "'导出路径'不能为空!");
				return false;
			}
			if (ObjectUtils.isEmpty(vo.getExcelPath())) {
				FrameUtils.alter("提示", "'excel路径'不能为空!");
				return false;
			}
			Result result = javaFileService.exportFiles(vo);
			if (result.isSuccess()) {
				String text = this.getOut_put().getText();
				text += "=========================\n[" + vo.getProjName()
						+ " - " + DateUtils.dateToString(new Date(), null)
						+ "]开始复制文件...\n" + result.getMsg() + "\n\n["
						+ vo.getProjName() + " - "
						+ DateUtils.dateToString(new Date(), null)
						+ "]复制结束...\n" + "=========================\n";
				this.getOut_put().setText(text);
				FrameUtils.alter("提示", "操作成功！");
			} else {
				String text = this.getOut_put().getText();
				text += "=========================\n[" + vo.getProjName()
						+ " - " + DateUtils.dateToString(new Date(), null)
						+ "]开始复制文件...\n" + "错误提示：\n" + result.getMsg()
						+ "\n\n[" + vo.getProjName() + " - "
						+ DateUtils.dateToString(new Date(), null)
						+ "]复制结束...\n" + "=========================\n";
				this.getOut_put().setText(text);
				FrameUtils
						.error("错误",
								"操作失败！请查看路径和excel文件格式是否正确！\n实在没办法，请联系WQ~~~ _/[>  <]\\_");
			}
		} catch (Exception e) {
			log.err(e.getMessage());
			FrameUtils.error("错误",
					"操作失败！请查看路径和excel文件格式是否正确！\n实在没办法，请请联系WQ~~~ _/[>  <]\\_");
			return false;
		}
		return true;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(595, 502);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource(SysConstant.FRAME_ICO)));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("文件导出");
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(10, 142, 70, 18));
			jLabel5.setText("excel路径：");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(10, 97, 65, 18));
			jLabel4.setText("导出路径：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(10, 57, 78, 18));
			jLabel2.setText("项目根目录：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(250, 12, 65, 18));
			jLabel1.setText("项目名称：");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(10, 12, 65, 18));
			jLabel.setText("常用项目：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getCommonProjNames(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getProjName(), null);
			jContentPane.add(getJButton_add(), null);
			jContentPane.add(getJButton_del(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getProjPath(), null);
			jContentPane.add(getJButton_projPath(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getExportPath(), null);
			jContentPane.add(getJButton_exportPath(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton_OK(), null);
			jContentPane.add(getJButton_cancel(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getExcelPath(), null);
			jContentPane.add(getJButton_excelPath(), null);
			jContentPane.add(getJButton_outputClear(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes commonProjNames
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCommonProjNames() {
		if (commonProjNames == null) {
			commonProjNames = new JComboBox();
			commonProjNames.setPreferredSize(new Dimension(30, 25));
			commonProjNames.setSize(new Dimension(155, 30));
			commonProjNames.setBackground(Color.white);
			commonProjNames.setLocation(new Point(85, 6));
			commonProjNames
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							int index = commonProjNames.getSelectedIndex();
							if (index != -1) {
								ProjInfoVO vo = commonProjList.get(index);
								try {
									FrameUtils.voToFrame(vo,
											ExportMainView.this);
								} catch (Exception e1) {
									log.err(e1.getMessage());
									FrameUtils.error("错误", "操作失败！请联系WQ~~~");
								}
							}
						}
					});
		}
		return commonProjNames;
	}

	/**
	 * This method initializes projName
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getProjName() {
		if (projName == null) {
			projName = new JTextField();
			projName.setLocation(new Point(325, 6));
			projName.setSize(new Dimension(155, 30));
		}
		return projName;
	}

	/**
	 * This method initializes jButton_add
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_add() {
		if (jButton_add == null) {
			jButton_add = new JButton();
			jButton_add.setBounds(new Rectangle(490, 7, 41, 28));
			jButton_add.setText("+");
			jButton_add.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addCommonProjList();
				}
			});
		}
		return jButton_add;
	}

	/**
	 * This method initializes jButton_del
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_del() {
		if (jButton_del == null) {
			jButton_del = new JButton();
			jButton_del.setBounds(new Rectangle(541, 7, 38, 28));
			jButton_del.setText("-");
			jButton_del.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					delCommonProjList();
				}
			});
		}
		return jButton_del;
	}

	/**
	 * This method initializes projPath
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getProjPath() {
		if (projPath == null) {
			projPath = new JTextField();
			projPath.setLocation(new Point(85, 51));
			projPath.setSize(new Dimension(394, 30));
		}
		return projPath;
	}

	/**
	 * This method initializes jButton_projPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_projPath() {
		if (jButton_projPath == null) {
			jButton_projPath = new JButton();
			jButton_projPath.setBounds(new Rectangle(491, 52, 87, 28));
			jButton_projPath.setText("浏      览");
			jButton_projPath
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SwingUtils.directoriesChooser(ExportMainView.this,
									projPath);
						}
					});
		}
		return jButton_projPath;
	}

	/**
	 * This method initializes exportPath
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getExportPath() {
		if (exportPath == null) {
			exportPath = new JTextField();
			exportPath.setLocation(new Point(85, 91));
			exportPath.setSize(new Dimension(394, 30));
		}
		return exportPath;
	}

	/**
	 * This method initializes jButton_exportPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_exportPath() {
		if (jButton_exportPath == null) {
			jButton_exportPath = new JButton();
			jButton_exportPath.setBounds(new Rectangle(491, 92, 85, 29));
			jButton_exportPath.setText("浏      览");
			jButton_exportPath
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SwingUtils.directoriesChooser(ExportMainView.this,
									exportPath);
						}
					});
		}
		return jButton_exportPath;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(11, 181, 468, 281));
			jScrollPane.setViewportView(getOut_put());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes out_put
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getOut_put() {
		if (out_put == null) {
			out_put = new JTextArea();
			out_put.setSize(2000, 4000);
			out_put.setLineWrap(true);
			out_put.setEditable(false);
			out_put.setWrapStyleWord(true);
		}
		return out_put;
	}

	/**
	 * This method initializes jButton_OK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_OK() {
		if (jButton_OK == null) {
			jButton_OK = new JButton();
			jButton_OK.setText("确      定");
			jButton_OK.setSize(new Dimension(85, 29));
			jButton_OK.setLocation(new Point(489, 373));
			jButton_OK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					exportFiles();
				}
			});
		}
		return jButton_OK;
	}

	/**
	 * This method initializes jButton_cancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_cancel() {
		if (jButton_cancel == null) {
			jButton_cancel = new JButton();
			jButton_cancel.setText("关      闭");
			jButton_cancel.setSize(new Dimension(85, 29));
			jButton_cancel.setLocation(new Point(489, 424));
			jButton_cancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							dispose();
						}
					});
		}
		return jButton_cancel;
	}

	/**
	 * This method initializes excelPath
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getExcelPath() {
		if (excelPath == null) {
			excelPath = new JTextField();
			excelPath.setLocation(new Point(85, 136));
			excelPath.setSize(new Dimension(394, 30));
		}
		return excelPath;
	}

	/**
	 * This method initializes jButton_excelPath
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_excelPath() {
		if (jButton_excelPath == null) {
			jButton_excelPath = new JButton();
			jButton_excelPath.setText("浏      览");
			jButton_excelPath.setSize(new Dimension(85, 29));
			jButton_excelPath.setLocation(new Point(491, 137));
			jButton_excelPath
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							SwingUtils.fileChooser(ExportMainView.this,
									excelPath, new String[] { ".xls" });
						}
					});
		}
		return jButton_excelPath;
	}

	/**
	 * This method initializes jButton_outputClear
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_outputClear() {
		if (jButton_outputClear == null) {
			jButton_outputClear = new JButton();
			jButton_outputClear.setText("清      空");
			jButton_outputClear.setSize(new Dimension(85, 29));
			jButton_outputClear.setLocation(new Point(489, 324));
			jButton_outputClear
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getOut_put().setText("");
						}
					});
		}
		return jButton_outputClear;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
