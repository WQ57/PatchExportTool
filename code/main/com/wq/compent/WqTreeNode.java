package com.wq.compent;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 * wqÊ÷ÐÎ½Úµã.
 * 
 * @author wuqing
 * 
 */
public class WqTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -3922778230563797644L;

	private String nodeId;
	private String nodeType;
	private String nodeText;

	private double sum = 0.0;
	private double earn = 0.0;
	private double cost = 0.0;

	public WqTreeNode() {

	}

	public WqTreeNode(String nodeText) {
		super(nodeText);
	}

	public WqTreeNode(String nodeId, String nodeType, String nodeText) {
		super(nodeText);
		this.nodeId = nodeId;
		this.nodeType = nodeType;
		this.nodeText = nodeText;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeText() {
		return nodeText;
	}

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
		setUserObject(nodeText);
	}

	public void flashNodeText(){

	}
	
	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getEarn() {
		return earn;
	}

	public void setEarn(double earn) {
		this.earn = earn;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
