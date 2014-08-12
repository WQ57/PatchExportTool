package com.wq.jfreechart.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Year;

import com.wq.utils.LogUtils;

public class TimeSeriesChartDataVO {

	public static final int DAY = 1;
	public static final int MONTH = 2;
	public static final int YEAR = 3;

	public static final String DAY_STR = "ÈÕ";
	public static final String MONTH_STR = "ÔÂ";
	public static final String YEAR_STR = "Äê";

	public static Map<String, Integer> typeMap = new HashMap<String, Integer>();
	static {
		typeMap.put(DAY_STR, DAY);
		typeMap.put(MONTH_STR, MONTH);
		typeMap.put(YEAR_STR, YEAR);
	}

	private Integer type;
	private String title;
	private List<TimeDataVO> data;
	TimeSeries timeseries;

	@SuppressWarnings("deprecation")
	public TimeSeriesChartDataVO(int type, String title, List<TimeDataVO> data) {
		this.title = title;
		this.type = type;
		this.data = data;
		switch (this.type) {
		case DAY:
			timeseries = new TimeSeries(this.title,
					org.jfree.data.time.Day.class);
			break;
		case MONTH:
			timeseries = new TimeSeries(this.title,
					org.jfree.data.time.Month.class);
			break;
		case YEAR:
			timeseries = new TimeSeries(this.title,
					org.jfree.data.time.Year.class);
			break;
		}
		setData(data);
	}

	public List<TimeDataVO> getData() {
		return data;
	}

	@SuppressWarnings("deprecation")
	public void setData() {
		for (int i = 0; i < data.size(); i++) {
			LogUtils.logToConsole(data.get(i).getDate());
			Date date = data.get(i).getDate();
			Double value = data.get(i).getValue();
			switch (this.type) {
			case DAY:
				timeseries.add(new Day(date.getDate(), date.getMonth() + 1,
						date.getYear() + 1900), value);
				break;
			case MONTH:
				timeseries.add(new Month(date.getMonth() + 1, date.getYear() + 1900),
						value);
				break;
			case YEAR:
				timeseries.add(new Year(date.getYear() + 1900), value);
				break;
			}
		}
	}

	public void setData(List<TimeDataVO> data) {
		this.data = data;
		setData();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TimeSeries getTimeseries() {
		return timeseries;
	}

	public void setTimeseries(TimeSeries timeseries) {
		this.timeseries = timeseries;
	}

}
