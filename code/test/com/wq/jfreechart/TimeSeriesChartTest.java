package com.wq.jfreechart;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import com.wq.jfreechart.vo.TimeDataVO;
import com.wq.jfreechart.vo.TimeSeriesChartDataVO;

public class TimeSeriesChartTest {

	public static void main(String[] arg) {
		JFrame frame = new JFrame("Java����ͳ��ͼ");
		frame.setLayout(new GridLayout(2, 2, 10, 10));
		List<TimeSeriesChartDataVO> data = new ArrayList<TimeSeriesChartDataVO>();
		for (int i = 0; i < 3; i++) {
			List<TimeDataVO> row = new ArrayList<TimeDataVO>();
			for (int j = 0; j < 10; j++) {
				TimeDataVO v = new TimeDataVO();
				Double value = Double.parseDouble(String.valueOf(i + 1 + j));
				v.setValue(value);
				Date date = new Date();
				date.setYear(2000+j);
				date.setMonth(j);
				date.setDate(j + 1);
				v.setDate(date);
				row.add(v);
			}
			TimeSeriesChartDataVO vo = new TimeSeriesChartDataVO(
					TimeSeriesChartDataVO.YEAR, "����_" + i, row);
			data.add(vo);
		}
		TimeSeriesChart chart = new TimeSeriesChart("����", "X��", "Y��", data);
		frame.add(chart.getChartPanel()); // �������ͼ
		frame.setBounds(50, 50, 800, 600);
		frame.setVisible(true);
	}

}
