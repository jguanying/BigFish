package crown.dafish.com.dafishtv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;

import crown.dafish.com.model.TvModel;
import crown.dafish.com.utils.Constants;
import crown.dafish.com.utils.Util;

/**********************************************************************
 * @author sundi
 * @类名 TvAdapter
 * @包名 crown.dafish.com.dafishtv
 * @创建日期 16/12/26
 ***********************************************************************/
public class TvAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<TvModel> mDatas = new ArrayList<>();

    private int mItemHeight = 0;

    public TvAdapter (Context context, ArrayList<TvModel> datas) {
        mContext = context;
        if (datas != null) {
            mDatas = datas;
        }
    }

    public void setItemHeight(int itemHeight) {
        mItemHeight = itemHeight;
        if (mItemHeight > 0) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.tv_list_item,null,false);
            viewHolder = new ViewHolder();
            viewHolder.mImage = (ImageView) view.findViewById(R.id.list_item_icon);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.list_item_name);
            view.setTag(viewHolder);
            convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mImage.setImageBitmap(null);
            viewHolder.mImage.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setVisibility(View.VISIBLE);
        }

//        int res = 0;
//        switch (mDatas.get(position).getId()) {
//            case Constants.TV_GUANGDONGSTV:
//                res = R.drawable.gd;
//                break;
//            case Constants.TV_DONGFANGSTV:
//                res = R.drawable.df;
//                break;
//            case Constants.TV_BEIJINGSTV:
//                res = R.drawable.bj;
//                break;
//            case Constants.TV_KAKUKATONGTV:
//                res = R.drawable.sr;
//                break;
//            case Constants.TV_GUZHUANGJUCHANGTV:
//                res = R.drawable.dsj;
//                break;
//            case Constants.TV_JINGPANZONGYITV:
//                res = R.drawable.zy;
//                break;
//        }

        viewHolder.mTextView.setVisibility(View.VISIBLE);
        viewHolder.mTextView.setText(mDatas.get(position).getTvName());
        if (viewHolder.mImage.getDrawable() == null || ((BitmapDrawable)viewHolder.mImage.getDrawable()).getBitmap() == null) {
            viewHolder.mImage.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(mDatas.get(position).getTvIconPath())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                    .override(mDisplayMetrics.widthPixels,mDisplayMetrics.heightPixels)
                .into(new ImageViewTarget<Bitmap>(viewHolder.mImage) {

                    @Override
                    protected void setResource(Bitmap resource) {
                        viewHolder.mImage.setVisibility(View.VISIBLE);
                        viewHolder.mImage.setImageBitmap(resource);
                        viewHolder.mTextView.setVisibility(View.GONE);
                    }

                });

        if (mItemHeight > 0) {
            ViewGroup.LayoutParams lp = viewHolder.mImage.getLayoutParams();
//            lp.height = (int)(1f * mItemHeight / mDatas.size()) - Util.dp2px(mContext,2);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    private static class ViewHolder {
        private ImageView mImage;
        private TextView mTextView;
    }
}
