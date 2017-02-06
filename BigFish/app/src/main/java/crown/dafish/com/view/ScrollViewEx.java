package crown.dafish.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**********************************************************************
 * @author sundi
 * @类名 ScrollViewEx
 * @包名 crown.dafish.com.view
 * @创建日期 17/1/19
 ***********************************************************************/
public class ScrollViewEx extends ScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public ScrollViewEx(Context context) {
        super(context, null);
    }

    public ScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChange(l,t,oldl,oldt);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        mOnScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollChange(int l, int t, int oldl, int oldt);
    }

}
