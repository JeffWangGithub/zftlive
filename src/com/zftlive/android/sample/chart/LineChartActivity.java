package com.zftlive.android.sample.chart;

import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolUnit;

/**
 * 基于AChartEngine绘制的折线图，点击带泡泡提示、可缩放、拖拽
 * @author 曾繁添
 * @version 1.0
 *
 */
public class LineChartActivity extends BaseActivity {

	private GraphicalView mChartView;
	private LineChart xychart;
	private LinearLayout ll_query_date,ll_chat; 
	private TextView tv_line_name,tv_line_date,query_date_value,year_value, pay_value;
	private View view;
	private String[] titles = null;//曲线标题集合
	private List<double[]> xValues = null;//X轴数据集合
	private List<double[]> yValues = null;//Y轴数据集合
	private int[] colors = null;//渲染器颜色集合
	private String lineColor = "#e64d00";//曲线颜色
	private String gridBgColor = "#eeeeee";//坐标系网格背景色
	private int lineWidth = 3;//曲线的宽度大小px
	private int pointSize = 5;//曲线点的大小
	private int axisTextSize = 16;//px 坐标轴刻度字体大小
	private PointStyle[] styles = null;	//点的样式集合
	private int xDisplayPoint = 10;//X轴显示刻度个数
	private int yDisplayPoint = 8;//Y轴显示刻度个数
	private String chartTitle = "";
	private String xLabel = "";
	private String yLabel = "";
	private double[] xValueArray = new double[50];
	private double[] yValueArray = new double[50];
	private double xMinValue = 0.5;
	private double xMaxValue = 50.0;
	private double yMinValue = 2.0;
	private double yMaxValue = 5.0;
	private int chat_margin_top = 0;
	private int chat_margin_buttom = 0;
	private int chat_margin_left = ToolUnit.dipTopx(30);
	private int chat_margin_right = ToolUnit.dipTopx(30);
	private int panMaximumX = 50;//平移X最大值
	
	@Override
	public int bindLayout() {
		return R.layout.activity_line_chart;
	}

	@Override
	public void initView(View view) {

		//曲线容器
		ll_chat = (LinearLayout) view.findViewById(R.id.ll_chat);
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
	
	/**
	 * 配置渲染器,设置坐标轴颜色、刻度、XY轴显示个数、网格、背景色等等
	 * @param renderer 渲染器
	 */
	@SuppressLint("ResourceAsColor")
	private void configRenderer(XYMultipleSeriesRenderer renderer)
	{
		//设置外边框(单位:pixels)上/左/下/右
		renderer.setMargins(new int[] { chat_margin_top, chat_margin_right, chat_margin_buttom, chat_margin_left });
		//设置用于X轴标签的个数
		renderer.setXLabels(xDisplayPoint);
		//Sets the approximate number of labels for the Y axis
		renderer.setYLabels(yDisplayPoint);
		//X轴标签文字对齐方式
		renderer.setXLabelsAlign(Align.CENTER);
		//Y轴标签文字对齐方式
		renderer.setYLabelsAlign(Align.RIGHT);
		//设置X轴字体颜色
		renderer.setXLabelsColor(Color.BLACK);
		//设置Y轴字体颜色
		renderer.setYLabelsColor(0, Color.BLACK);
		renderer.setYLabelsPadding(3);
		//设置是否显示网格
		renderer.setShowGrid(true);
		//renderer.setShowGridX(true);
		//renderer.setShowGridY(true);
		// 坐标轴四周外间距的颜色Sets the color of the margins
		renderer.setMarginsColor(Color.WHITE);
		//设置图表标题字号
		//renderer.setChartTitleTextSize(20);
		// 设置背景颜色
		renderer.setBackgroundColor(Color.parseColor(gridBgColor));
		// 设置背景颜色生效
		renderer.setApplyBackgroundColor(true);
		//Sets the axis title text size
		//renderer.setAxisTitleTextSize(16);

		//TODO 坐标轴颜色
		renderer.setAxesColor(Color.BLACK);
		//TODO 坐标轴单位文字颜色、字号
		renderer.setLabelsColor(Color.BLACK);
		//TODO 设置坐标轴刻度字体大小
		renderer.setLabelsTextSize(ToolUnit.pxTosp(axisTextSize));
		//TODO 设置点大小
		renderer.setPointSize(ToolUnit.pxTodip(pointSize));
		//是否显示缩放按钮
		renderer.setZoomButtonsVisible(false);
		// 是否支持图表移动
		renderer.setPanEnabled(true, true);
		// 是否支持图表缩放
		renderer.setZoomEnabled(true, true);
		//是否可点击
		renderer.setClickEnabled(true);
		//TODO Sets the pan limits as an array of 4 values. Setting it to null or a different size array will disable the panning(平移) limitation. Values: [panMinimumX, panMaximumX, panMinimumY, panMaximumY] 
		renderer.setPanLimits(new double[] { 0.5, panMaximumX, 2.0, 6 });
		//TODO Sets the zoom limits as an array of 4 values. Setting it to null or a different size array will disable the zooming(缩放) limitation. Values: [zoomMinimumX, zoomMaximumX, zoomMinimumY, zoomMaximumY] 
		renderer.setZoomLimits(new double[] { 1, 50, 0.5, 6 });
		// 是否显示图例
		renderer.setShowLegend(false);
		//设置图例字体大小
		//renderer.setLegendTextSize(15);
		// 上面的每个“点”显示出该“点”的数值
		renderer.setDisplayValues(true);
		//设置点击该点的范围半径 Sets the selectable radius value around clickable points
		renderer.setSelectableBuffer(20);
	}

	/**
	 * 设置渲染器
	 * @param renderer 渲染器
	 * @param colors 颜色
	 * @param styles 点的样式
	 */
	private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,PointStyle[] styles) {
		//设置每一条曲线渲染器的颜色、宽度、点的风格样式
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			//设置线条颜色
			r.setColor(colors[i]);
			// 设置线的宽度
			r.setLineWidth(ToolUnit.pxTodip(lineWidth));
			//点的风格样式
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	/**
	 * 构建XYMultipleSeriesDataset
	 * @param titles 标题
	 * @param xValues x轴数据
	 * @param yValues Y轴数据
	 * @return
	 */
	private XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	/**
	 * 构建XY分类数据
	 * @param dataset 数据结果集
	 * @param titles 标题
	 * @param xValues x轴数据
	 * @param yValues y周数据
	 * @param scale 缩放倍数
	 */
	private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries mySeries = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				mySeries.add(xV[k], yV[k]);
			}
			dataset.addSeries(mySeries);
		}
	}

}
