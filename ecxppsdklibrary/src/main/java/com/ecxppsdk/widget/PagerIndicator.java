package com.ecxppsdk.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author VincenT
 *
 */
public class PagerIndicator extends View {
	/**空心圆左边位置及两圆心距离*/
	private int width = 30;
	private int height = 20;
	private int radius=10;//r为半径
	/**实心圆x坐标*/
	private float varWidth;
	private Paint paint;
	private int num=1;
	
	public PagerIndicator(Context context){
		this(context,null);
	}
	public PagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public PagerIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public void setIndicatorNumber(int num){
		this.num=num;
	}
	public int getIndicatorNumber(){
		return num;
	}
	/**此方法在View大小发生变化时执行*/
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		varWidth=width;
		height=h/2;
	}

	/**
	 * Android 中所有的view都是绘制的，
	 * 每次绘制图形都要调用onDraw方法
	 * 绘制图形对象:
	 * 1.画布(Canvas)
	 * 2.画笔(Paint)
	 * */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#64FFFFFF"));//100透明值
		paint.setStyle(Style.STROKE);
		//根据数目绘制所有空心圆形
		for(int i=0;i<getIndicatorNumber();i++){
		canvas.drawCircle(width+i*width,height,radius, paint);
		}
		//设置实心圆形画笔，画一个实心圆
		paint.setColor(Color.parseColor("#C8FFFFFF"));//200透明值
		paint.setStyle(Style.FILL);
		canvas.drawCircle(varWidth,height,radius, paint);
	}
	
	/**
	 * wrap_parent -> MeasureSpec.AT_MOST
	 * match_parent -> MeasureSpec.EXACTLY
	 * 具体值 -> MeasureSpec.EXACTLY
	 * */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
	    int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();
	    if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {//width=match或值,height=wrap
	        height = width/10;
	        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
	    } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {//width=wrap,height=match或值
	        width = width/10;
	        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
	    } else if (widthMode != MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY){//width=wrap,height=wrap
	    	width = width/2;
//	    	height = width/10;
	    	widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
	    	heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
	    }else{//width=match或值,height=match或值
	    }
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	/**更新单一实心圆view位置
	 * @param position
	 */
	public void onUpdate(int position){
		varWidth=width+(position)*width;
		invalidate();
//		Log.i("Indicator", "小width="+width+",大varWidth="+varWidth);
	}

	/**测量宽度
	 * @param measureSpec
	 * @return*/
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = getPaddingLeft() + getPaddingRight()
					+ (getIndicatorNumber() * 2 * radius) + (getIndicatorNumber() - 1) * radius + 1;
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**测量高度
	 * @param measureSpec
	 * @return*/
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = 2 * radius + getPaddingTop() + getPaddingBottom() + 1;
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

}
