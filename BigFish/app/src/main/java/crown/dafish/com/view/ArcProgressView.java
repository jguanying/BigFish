//package crown.dafish.com.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//
//import com.frogshealth.photoframe.R;
//import com.frogshealth.photoframe.module.health.model.ArcData;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**********************************************************************
// * @author sundi
// * @类名 ArcProgressView
// * @包名 com.frogshealth.photoframe.module.health.view
// * @创建日期 16/12/31
// ***********************************************************************/
//public class ArcProgressView extends View {
//
//    /**
//     * 图形描画矩形
//     */
//    private RectF mRectF;
//
//    /**
//     * 图形画笔
//     */
//    private Paint mPaint;
//
//    /**
//     * 位置画笔
//     */
//    private Paint mTextPaint;
//
//    /**
//     * 上下文
//     */
//    private Context mContext;
//
//    /**
//     * 起始文字
//     */
//    private String mStartText = "";
//
//    /**
//     * 结束文字
//     */
//    private String mEndText = "";
//
//    /**
//     * 默认描边宽度
//     */
//    private static final int CIRCLE_DEFAULT_STROKE_WIDTH = 22;
//
//    /**
//     * 描边宽度
//     */
//    private int mCircleLineStrokeWidth = CIRCLE_DEFAULT_STROKE_WIDTH;
//
//    /**
//     * 起始角度
//     */
//    private int mStartDegree = 0;
//
//    /**
//     * 结束角度
//     */
//    private int mEndDegree = -180;
//
//    /**
//     * 值总和
//     */
//    private int mValueSum;
//
//    /**
//     * 默认半径
//     */
//    private static final int CIRCLE_DEFAULT_RADIUS = 200;
//
//    /**
//     * 默认文字大小
//     */
//    private static final int DEFAULT_TEXT_SZIE = 20;
//
//    /**
//     * 半径
//     */
//    private float mRadius = CIRCLE_DEFAULT_RADIUS;
//
//    /**
//     * 文字显示开关
//     */
//    private boolean mIsShowText = true;
//
//    /**
//     * 文字大小
//     */
//    private float mTextSize = DEFAULT_TEXT_SZIE;
//
//    /**
//     * 文字颜色
//     */
//    private int mTextColor = Color.BLACK;
//
//    /**
//     * 间隔
//     */
//    private float mPadding;
//
//    /**
//     * 中央显示图片
//     */
//    private Bitmap mBitmap;
//
//    /**
//     * 数据列表
//     */
//    private List<ArcData> mArcDatas = new ArrayList<>();
//
//    public ArcProgressView (Context context) {
//        this(context, null);
//    }
//
//    public ArcProgressView (Context context, AttributeSet attributeSet) {
//        super(context, attributeSet);
//        mContext = context;
//        mRectF = new RectF();
//
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//
//        TypedArray a = context.obtainStyledAttributes(attributeSet,
//                R.styleable.ArcProgressView);
//        mRadius = a.getDimensionPixelSize(
//                R.styleable.ArcProgressView_diameter, (int) TypedValue
//                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                CIRCLE_DEFAULT_RADIUS, getResources()
//                                        .getDisplayMetrics()));
//        mTextSize  = a.getDimensionPixelSize(
//                R.styleable.ArcProgressView_textSize, (int) TypedValue
//                        .applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                                DEFAULT_TEXT_SZIE, getResources()
//                                        .getDisplayMetrics()));
//        mIsShowText = a.getBoolean( R.styleable.ArcProgressView_show_text,true);
//
//        mTextColor = a.getColor(R.styleable.ArcProgressView_textColor, Color.BLACK);
//
//
//        mCircleLineStrokeWidth = a.getDimensionPixelSize(
//                R.styleable.ArcProgressView_strokeWidth, (int) TypedValue
//                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                CIRCLE_DEFAULT_STROKE_WIDTH, getResources()
//                                        .getDisplayMetrics()));
//
//        a.recycle();
//        mTextPaint = new Paint();
//        mTextPaint.setAntiAlias(true);
//        mTextPaint.setColor(mTextColor);
//        mTextPaint.setTextSize(mTextSize);
//    }
//
//    /**
//     * 设置文字大小
//     * @param px 文字大小
//     */
//    public void setTextSize(int px) {
//        mTextSize = px;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 设置文字颜色
//     * @param color 颜色
//     */
//    public void setTextColor(int color) {
//        mTextColor = color;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 设置数据
//     * @param datas 数据列表
//     */
//    public void setProgressData(List<ArcData> datas) {
//        mArcDatas = (ArrayList<ArcData>)((ArrayList<ArcData>)datas).clone();
//        postInvalidateOnAnimation();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int width = this.getWidth();
//        int height = this.getHeight();
//        int arcSize = Math.min(width, height);
//        computePadding();
//        mRadius = arcSize - 1.2f * mPadding;
//
//        canvas.drawColor(Color.TRANSPARENT);
//        Rect rect = new Rect();
//        getDrawingRect(rect);
//        int originX = rect.left + rect.width() / 2;
//        int originY = (int) (rect.top + rect.height() - mPadding);
//
//        // 位置
//        mRectF.left = originX - mRadius + mCircleLineStrokeWidth / 2f; // 左上角x
//        mRectF.top = originY - mRadius + mCircleLineStrokeWidth / 2f; // 左上角y
//        mRectF.right = originX + mRadius - mCircleLineStrokeWidth / 2f; // 左下角x
//        mRectF.bottom = originY + mRadius - mCircleLineStrokeWidth / 2f; // 右下角y
//        drawSlices(canvas);
//        if (mIsShowText) {
//            drawText(canvas);
//        }
//
//        drawBitmap(canvas);
//    }
//
//
//    /**
//     * 计算数值总和
//     */
//    private void computeSumValue() {
//        mValueSum = 0;
//        for(ArcData value : mArcDatas) {
//            mValueSum += value.getValue();
//        }
//    }
//
//
//    /**
//     * 计算留白间隔
//     */
//    private void computePadding() {
//        float sWidth = mTextPaint.measureText(mStartText, 0 , mStartText.length());
//        float eWidth = mTextPaint.measureText(mEndText, 0 , mEndText.length());
//
//        float max = Math.max(sWidth, eWidth);
//        mPadding = max / 2;
//    }
//
//
//    /**
//     * 描画分片
//     * @param canvas 画布
//     */
//    private void drawSlices(Canvas canvas) {
//        float lastAngle = mStartDegree;
//        int sliceIndex = 0;
//
//        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        drawSlice(canvas, mRectF,getResources().getColor(R.color.blue_7B95AA), mStartDegree, mEndDegree,mPaint);
//
//        if (mArcDatas != null && mArcDatas.size() > 0) {
//            computeSumValue();
//            final float sliceScale = Math.abs(mEndDegree - mStartDegree) * 1f / mValueSum;
//            for (ArcData value : mArcDatas) {
//                final float angle = -1 * value.getValue() * sliceScale;
//                if (sliceIndex == 0 || sliceIndex == mArcDatas.size() - 1) {
//                    mPaint.setStrokeCap(Paint.Cap.ROUND);
//                } else {
//                    mPaint.setStrokeCap(Paint.Cap.BUTT);
//                }
//                drawSlice(canvas, mRectF, value.getColor(), lastAngle, angle,mPaint);
//                lastAngle += angle;
//                sliceIndex++;
//            }
//        }
//
//
//        //描边        mPaint.setStrokeCap(Paint.Cap.BUTT);
////        RectF rectF = new RectF(mRectF);
////        rectF.left = rectF.left -  mCircleLineStrokeWidth / 2f;
////        rectF.right = rectF.right + mCircleLineStrokeWidth / 2f;
////        rectF.top = rectF.top - mCircleLineStrokeWidth / 2f;
////        rectF.bottom = rectF.bottom + mCircleLineStrokeWidth / 2f;
////        drawSlice(canvas, rectF,getResources().getColor(R.color.white), mStartDegree, mEndDegree,mPaint);
////
//    }
//
//    /**
//     * 绘制分片
//     * @param canvas 画布
//     * @param rectF 绘制矩形
//     * @param color 画笔颜色
//     * @param lastAngle 上一次绘制角度
//     * @param angle 本次绘制角度
//     * @param paint 画笔
//     */
//    private void drawSlice(Canvas canvas, RectF rectF ,int color, float lastAngle, float angle, Paint paint) {
//        mPaint.setColor(color);
//        canvas.drawArc(rectF, lastAngle, angle, false, paint);
//    }
//
//    /**
//     * 绘制文字
//     * @param canvas 画布
//     */
//    private void drawText(Canvas canvas) {
//
//        float sWidth = mTextPaint.measureText(mStartText, 0 , mStartText.length());
//        float eWidth = mTextPaint.measureText(mEndText, 0 , mEndText.length());
//
//        float sTextX = mRectF.left - sWidth / 2f;
//        float sTextY = mRectF.top + mRectF.width()/2f +  mCircleLineStrokeWidth * 2f;
//        canvas.drawText(mStartText, sTextX, sTextY, mTextPaint);
//
//        float eTextX = mRectF.right - eWidth / 2f;
//        float eTextY = mRectF.top + mRectF.width()/2f +  mCircleLineStrokeWidth * 2f;
//        canvas.drawText(mEndText, eTextX, eTextY, mTextPaint);
//    }
//
//    /**
//     * 绘制图片
//     * @param canvas 画布
//     */
//    private void drawBitmap(Canvas canvas) {
//        if (mBitmap != null) {
//            Rect rect = new Rect();
//            float centerX = mRectF.left + mRectF.width() / 2f;
//            float centerY = mRectF.top + mRectF.height() / 2f;
//            rect.left = (int) (centerX - mBitmap.getWidth() / 2);
//            rect.top = (int) (centerY - mBitmap.getHeight());
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            canvas.drawBitmap(mBitmap,rect.left,rect.top,paint);
//        }
//
//    }
//
//    /**
//     * 设置结束文字
//     * @param endText 结束文字
//     */
//    public void setEndText(String endText) {
//        mEndText = endText;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 设置开始文字
//     * @param startText 开始文字
//     */
//    public void setStartText(String startText) {
//        mStartText = startText;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 设置画笔宽度
//     * @param circleLineStrokeWidth 画笔宽度
//     */
//    public void setCircleLineStrokeWidth(int circleLineStrokeWidth) {
//        mCircleLineStrokeWidth = circleLineStrokeWidth;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 取得半径
//     * @return 半径
//     */
//    public float getRadius() {
//        return mRadius;
//    }
//
//    /**
//     * 设置半径
//     * @param radius 半径
//     */
//    public void setRadius(float radius) {
//        mRadius = radius;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 设置图片
//     * @param bitmap 图片
//     */
//    public void setBitmap(Bitmap bitmap) {
//        release();
//        mBitmap = bitmap;
//        postInvalidateOnAnimation();
//    }
//
//    /**
//     * 释放资源
//     */
//    public void release() {
//        if (mBitmap != null && !mBitmap.isRecycled()) {
//            mBitmap.recycle();
//            mBitmap = null;
//        }
//    }
//}
