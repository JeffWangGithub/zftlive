package com.zftlive.android.sample.chart;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
	private LinearLayout ll_chat; 
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
	private double[] xValueArray = new double[]{
											  0,1,2,3,4,5,6,7,8,9,10,
	                                          11,12,13,14,15,16,17,18,19,20,
	                                          21,22,23,24,25,26,27,28,29,30,
	                                          31,32,33,34,35,36,37,38,39,40,
	                                          41,42,43,44,45,46,47,48,49
	                                          };
	private double[] yValueArray = new double[]{
		3.73491011,2.88276139,3.06720639,3.08372,3.16196556,3.17777,3.37103458,3.37658,
        3.40842,3.45135696,3.46222594,3.485872182,3.533854426,3.595742956,3.661108056,
        3.71952001,3.772147714,3.825863297,3.878791897,3.929058652,3.9747887,4.012527655,
        4.041093865,4.062021566,4.076844994,4.087098388,4.094315982,4.100032013,4.105780719,
        4.113096336,4.1235131,4.135114096,4.145144375,4.153881122,4.161601522,4.168582763,
        4.175102028,4.181436503,4.187863375,4.194659829,4.20210305,4.209482797,4.216004934,
        4.221820804,4.22708175,4.231939115,4.236544242,4.241048474,4.245603154,4.250359625
	};
	private double xMinValue = 0.5;
	private double xMaxValue = 50.0;
	private double yMinValue = 2.0;
	private double yMaxValue = 5.0;
	private PopupWindow mUpLeftPopupTip,mUpRightPopupTip;//提示信息pop(Left/Right)
	private View mUpLeftTipView,mUpRightTipView;//提示信息PopView
	private int mPopTipsWidth;//pop提示窗口宽度
	private int mPopTipsHeight;//pop提示窗口高度
	private TextView tv_tips_ul,tv_tips_ur;//提示信息Textview
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
		
		//坐标轴刻度字体大小
		axisTextSize = getResources().getDimensionPixelSize(R.dimen.axis_label_text);
		//曲线大小
		lineWidth = getResources().getDimensionPixelSize(R.dimen.line_size);
		//曲线点的大小
		pointSize = getResources().getDimensionPixelSize(R.dimen.line_point_size);
		
		//提示信息窗口宽高
		mPopTipsWidth = ToolUnit.dipTopx(100);
		mPopTipsHeight = ToolUnit.dipTopx(60);
		
		//pop提示信息
		mUpLeftTipView = LayoutInflater.from(this).inflate(R.layout.chat_tips_up_left, null);
	    tv_tips_ul = (TextView)mUpLeftTipView.findViewById(R.id.tv_tips);
		
	    mUpRightTipView = LayoutInflater.from(this).inflate(R.layout.chat_tips_up_right, null);
	    tv_tips_ur = (TextView)mUpRightTipView.findViewById(R.id.tv_tips);
	    
	}

	@Override
	public void doBusiness(Context mContext) {
		paintLine();
	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {

	}
	
	/**
	 * 绘制曲线
	 */
	private void paintLine(){
		//初始化数据
		initData();
		
		//构建多条曲线渲染器
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
		}
		
		//设置图表
		renderer.setChartTitle(chartTitle);
		renderer.setXTitle(xLabel);
		renderer.setYTitle(yLabel);
		renderer.setXAxisMin(xMinValue);
		renderer.setXAxisMax(xMaxValue);
		renderer.setYAxisMin(yMinValue);
		renderer.setYAxisMax(yMaxValue);
		
		//设置渲染器
		configRenderer(renderer);
		
		//构造图表
		xychart = new LineChart(buildDataset(titles, xValues, yValues), renderer);
		mChartView = new GraphicalView(this, xychart);
		
		mChartView.setBackgroundColor(Color.WHITE);
		mChartView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				GraphicalView gv = (GraphicalView) v;
				//将view转换为可以监听的GraphicalView
				SeriesSelection ss = gv.getCurrentSeriesAndPoint();
				//获得被点击的系列和点
				if (ss == null) return ;
				double[] point = new double[]{ss.getXValue(),ss.getValue()};
				//获得当前被点击点的X位置和Y数值
				final double[] dest = xychart.toScreenPoint(point);
				int x = (int)dest[0];
				int y =  (int)dest[1];
				
				//获取当前控件距离顶部的坐标-高度
				int[] location = new int[2];  
		        v.getLocationOnScreen(location);

		        //先关闭泡泡窗口
		        dismissPopupWindow();
		        
				//左侧宽度不够显示pop，则显示右侧
				if(getDisplayMetrics().widthPixels - x < mPopTipsWidth ){
					tv_tips_ur.setText(point[0]+" , "+point[1]);
					//右上方
					popwindowR(LineChartActivity.this, mChartView, mUpRightTipView,x-mPopTipsWidth + 3,y+location[1]-mPopTipsHeight);
				}else{
					tv_tips_ul.setText(point[0]+" , "+point[1]);
					//左上方--OK精准点
					popwindowL(LineChartActivity.this, mChartView, mUpLeftTipView, x ,y+location[1]-mPopTipsHeight);
				}
			}
		});
		
		//将曲线添加到界面
		ll_chat.removeAllViews();
		ll_chat.addView(mChartView);
	}
	
	/**获取系统显示材质***/
	public DisplayMetrics getDisplayMetrics(){
		  DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		  WindowManager windowMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		  windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
		  return mDisplayMetrics;
	}
	
    /**
     * 弹出Pop窗口
     * @param context 依赖界面上下文
     * @param anchor 触发pop界面的控件
     * @param popView pop窗口界面
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     */
    public  PopupWindow popwindowL(Context context,View anchor,View popView,int xoff,int yoff){
		mUpLeftPopupTip = new PopupWindow(popView,mPopTipsWidth, mPopTipsHeight,true);
		mUpLeftPopupTip.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    	mUpLeftPopupTip.setOutsideTouchable(false);
    	
    	if (mUpLeftPopupTip.isShowing()) {
    		mUpLeftPopupTip.update(xoff,yoff,mPopTipsWidth, mPopTipsHeight);
		} else {
			mUpLeftPopupTip.showAtLocation(mChartView,Gravity.NO_GRAVITY, xoff,yoff);
		}	
    	
        return mUpLeftPopupTip;
    }
    
    /**
     * 弹出Pop窗口
     * @param context 依赖界面上下文
     * @param anchor 触发pop界面的控件
     * @param popView pop窗口界面
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     */
    public  PopupWindow popwindowR(Context context,View anchor,View popView,int xoff,int yoff){
    	
		mUpRightPopupTip = new PopupWindow(popView, mPopTipsWidth, mPopTipsHeight,true);
		mUpRightPopupTip.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		mUpRightPopupTip.setOutsideTouchable(false);
    	
    	if (mUpRightPopupTip.isShowing()) {
    		mUpRightPopupTip.update(xoff,yoff,mPopTipsWidth, mPopTipsHeight);
		} else {
			mUpRightPopupTip.showAtLocation(mChartView,Gravity.NO_GRAVITY, xoff,yoff);
		}	
    	
        return mUpRightPopupTip;
    }

    /**
     * 关闭泡泡窗口
     */
	private void dismissPopupWindow() {
		if (mUpRightPopupTip != null) {
			if (mUpRightPopupTip.isShowing()) {
				mUpRightPopupTip.dismiss();
			}
			mUpRightPopupTip = null;
		}
		if (mUpLeftPopupTip != null) {
			if (mUpLeftPopupTip.isShowing()) {
				mUpLeftPopupTip.dismiss();
			}
			mUpLeftPopupTip = null;
		}
	}
     
	/**
	 * 初始化数据
	 */
	private void initData(){
		//曲线标题集合
		titles = new String[] { chartTitle };
		//X轴数据集合
		xValues = new ArrayList<double[]>();
		for (int i = 0; i < titles.length; i++) {
			xValues.add(xValueArray);
		}
		//Y轴数据集合
		yValues = new ArrayList<double[]>();
		yValues.add(yValueArray);
		//渲染器颜色集合（线条颜色）
		colors = new int[] {Color.parseColor(lineColor)};
		//点的样式集合 SQUARE-方块  /TRIANGLE-山角形 / POINT-点（看不出来） /DIAMOND-菱形角/ CIRCLE-实心圆形
		styles = new PointStyle[] { PointStyle.CIRCLE };		
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
