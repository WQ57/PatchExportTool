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
 * ʱ������ͳ��ͼ.
 * 
 * @author wuqing
 * 
 */
public class TimeSeriesChart {

	ChartPanel panel;

	/**
	 * ��ʼ���������.
	 * 
	 * @param chartTitle
	 *            ͼ�α���
	 * @param xTitle
	 *            x�����
	 * @param yTitle
	 *            y�����
	 * @param data
	 *            ���ݼ�
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
		dateaxis.setLabelFont(new Font("����", Font.BOLD, 12)); // ˮƽ�ײ�����
		dateaxis.setTickLabelFont(new Font("����", Font.BOLD, 10)); // ��ֱ����
		ValueAxis rangeAxis = xyplot.getRangeAxis();// ��ȡ��״
		rangeAxis.setLabelFont(new Font("����", Font.BOLD, 13));
		jfreechart.getLegend().setItemFont(new Font("����", Font.BOLD, 13));
		jfreechart.getTitle().setFont(new Font("����", Font.BOLD, 20));// ���ñ�������
	}

	/**
	 * �������ݼ�.
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
	 * ���ͼ�����.
	 * 
	 * @return
	 */
	public ChartPanel getChartPanel() {
		return panel;
	}
}
