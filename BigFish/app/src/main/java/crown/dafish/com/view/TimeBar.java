package crown.dafish.com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import crown.dafish.com.dafishtv.R;
import crown.dafish.com.utils.Util;

/**********************************************************************
 * @author sundi
 * @类名 TimeBar
 * @包名 crown.dafish.com.view
 * @创建日期 16/12/31
 ***********************************************************************/


public class TimeBar extends View{

    /**
     * 默认文字大小
     */
    private static final int DEFAULT_TEXT_SZIE = 15;

    /**
     * 图形画笔
     */
    private Paint mPaint;

    /**
     * 位置画笔
     */
    private Paint mTextPaint;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 文字大小
     */
    private float mTextSize = DEFAULT_TEXT_SZIE;

    /**
     * 一个单位占像素
     */
    private int mUnitInPixel = 10;

    /**
     * 单位数
     */
    private int mUnitCount = 24 * 60 / 5;

    private int mPadding = 20;

    private int mLongHeight = 15;

    private int mShortHeight = 5;

    private int mTextHeight = 10;

    /**
     * View的高度
     */
    private int mHeight = 0;

    /**
     * View的宽度
     */
    private int mWidth = 0;

    private String[] mTimes = {"00:00","00:30","01:00","01:30","02:00","02:30",
            "03:00","03:30","04:00","04:30","05:00","05:30",
            "06:00","06:30","07:00","07:30","08:00","08:30",
            "09:00","09:30","10:00","10:30","11:00","11:30",
            "12:00","12:30","13:00","13:30","14:00","14:30",
            "15:00","15:30","16:00","16:30","17:00","17:30",
            "18:00","18:30","19:00","19:30","20:00","20:30",
            "21:00","21:30","22:00","22:30","23:00","23:30",
            "24:00"};

    public TimeBar (Context context) {
        super(context,null);
    }

    public TimeBar (Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        mContext =context;
        mLongHeight = Util.dp2px(context,mLongHeight);
        mPadding = Util.dp2px(context,mPadding);
        mTextHeight = Util.dp2px(context,mTextHeight);

        mHeight = mLongHeight + mPadding * 2 + mTextHeight;
        mWidth = Util.dp2px(context,mUnitInPixel * mUnitCount + mPadding * 2);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);

        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.TimeBar);
        mTextSize  = a.getDimensionPixelSize(
                R.styleable.TimeBar_textSize, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_SP,
                                DEFAULT_TEXT_SZIE, getResources()
                                        .getDisplayMetrics()));

        a.recycle();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.WHITE);
    }


    /**
     * 设置文字大小
     * @param px 文字大小
     */
    public void setTextSize(int px) {
        mTextSize = px;
        postInvalidateOnAnimation();
    }

    public void setUnitInPixel(int unitInPixel) {
        mUnitInPixel = unitInPixel;
        postInvalidateOnAnimation();
    }

    public void setPadding(int padding) {
        mPadding = padding;
        postInvalidateOnAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(mPadding,mHeight - mPadding / 2,mUnitInPixel * mUnitCount + mPadding,mHeight - mPadding / 2,mPaint);
        for (int i = 0; i < mUnitCount + 1; i++) {
            if (i % 6 == 0 ) {
                //长刻度
                canvas.drawLine(mPadding + i * mUnitInPixel,mHeight - mPadding / 2,mPadding + i * mUnitInPixel,mHeight - mPadding / 2 - mLongHeight,mPaint);
                drawText(canvas,mTimes[i / 6],mPadding + i * mUnitInPixel,mHeight - mPadding / 2 - mLongHeight);
            } else {
                //短刻度
                canvas.drawLine(mPadding + i * mUnitInPixel,mHeight - mPadding / 2,mPadding + i * mUnitInPixel,mHeight - mPadding / 2 - mShortHeight,mPaint);
            }
        }
    }

    /**
     * 绘制文字
     * @param canvas 画布
     */
    private void drawText(Canvas canvas,String text,float positionX,float positionY) {

        float width = mTextPaint.measureText(text, 0 , text.length());

        float sTextX = positionX - width / 2;
        float sTextY = positionY - mPadding / 2;
        canvas.drawText(text, sTextX, sTextY, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight = mLongHeight + mPadding * 2 + mTextHeight;
        mWidth = mUnitInPixel * mUnitCount + mPadding * 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    public int getViewHeight() {
        return mHeight;
    }

    public int getViewWidth () {
        return mWidth;
    }
}
