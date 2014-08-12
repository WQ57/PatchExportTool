package com.wq.jfreechart;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.wq.jfreechart.vo.TimeSeriesChartDataVO;

/**
 * 时间线性统计图.
 * 
 * @author wuqing
 * 
 */
public class TimeSeriesChart {

	ChartPanel panel;

	/**
	 * 初始化构造参数.
	 * 
	 * @param chartTitle
	 *            图形标题
	 * @param xTitle
	 *            x轴标题
	 * @param yTitle
	 *            y轴标题
	 * @param data
	 *            数据集
	 */
	public TimeSeriesChart(String chartTitle, String xTitle, String yTitle,
			List<TimeSeriesChartDataVO> data) {
		XYDataset xydataset = createDataset(data);
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(chartTitle,
				xTitle, yTitle, xydataset, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		String dateFormat = "yyyy-MM";
		if (data != null && data.size() > 0) {
			switch (data.get(0).getType()) {
			case TimeSeriesChartDataVO.DAY:
				dateFormat = "yyyy-MM-dd";
				break;
			case TimeSeriesChartDataVO.MONTH:
				dateFormat = "yyyy-MM";
				break;
			case TimeSeriesChartDataVO.YEAR:
				dateFormat = "yyyy";
				break;
			}
		}
		dateaxis.setDateFormatOverride(new SimpleDateFormat(dateFormat));
		panel = new ChartPanel(jfreechart, true);
		dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 12)); // 水平底部标题
		dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 10)); // 垂直标题
		ValueAxis rangeAxis = xyplot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 13));
		jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 13));
		jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体
	}

	/**
	 * 构建数据集.
	 * 
	 * @param data
	 * @return
	 */
	private XYDataset createDataset(List<TimeSeriesChartDataVO> data) {
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		for (int i = 0; i < data.size(); i++) {
			timeseriescollection.addSeries(data.get(i).getTimeseries());
		}
		return timeseriescollection;
	}

	/**
	 * 获得图形面板.
	 * 
	 * @return
	 */
	public ChartPanel getChartPanel() {
		return panel;
	}
}
