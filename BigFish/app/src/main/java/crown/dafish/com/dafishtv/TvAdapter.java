package crown.dafish.com.dafishtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.tv_list_item,null,false);
            viewHolder = new ViewHolder();
            viewHolder.mImage = (ImageView) view.findViewById(R.id.list_item);
            view.setTag(viewHolder);
            convertView = view;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int res = 0;
        switch (mDatas.get(position).getId()) {
            case Constants.TV_GUANGDONGSTV:
                res = R.drawable.gd;
                break;
            case Constants.TV_DONGFANGSTV:
                res = R.drawable.df;
                break;
            case Constants.TV_BEIJINGSTV:
                res = R.drawable.bj;
                break;
            case Constants.TV_KAKUKATONGTV:
                res = R.drawable.sr;
                break;
            case Constants.TV_GUZHUANGJUCHANGTV:
                res = R.drawable.dsj;
                break;
            case Constants.TV_JINGPANZONGYITV:
                res = R.drawable.zy;
                break;
        }
        viewHolder.mImage.setImageResource(res);
        if (mItemHeight > 0) {
            ViewGroup.LayoutParams lp = viewHolder.mImage.getLayoutParams();
            lp.height = (int)(1f * mItemHeight / mDatas.size()) - Util.dp2px(mContext,2);
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
    }
}
