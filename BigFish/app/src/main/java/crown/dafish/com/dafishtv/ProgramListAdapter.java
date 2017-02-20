package crown.dafish.com.dafishtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import crown.dafish.com.model.ProgramModel;

/**
 * Created by jianggy on 2017/2/19.
 */

public class ProgramListAdapter extends BaseAdapter {

    private Context mContext;

    List<ProgramModel> modelList = new ArrayList<>();

    private int mItemHeight = 0;

    public ProgramListAdapter (Context context, ArrayList<ProgramModel> datas) {
        mContext = context;
        if (datas != null) {
            modelList = datas;
        }
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ProgramListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.program_item_detail,null,false);
            viewHolder = new ProgramListAdapter.ViewHolder();
            viewHolder.mTextDuration = (TextView) view.findViewById(R.id.program_duration);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.program_name);
            view.setTag(viewHolder);
            convertView = view;
        } else {
            viewHolder = (ProgramListAdapter.ViewHolder) convertView.getTag();
         }

        ProgramModel model = modelList.get(position);
        viewHolder.mTextDuration.setText(model.getStartTime().substring(0,5) + "-" + model.getEndTime().substring(0,5));
        viewHolder.mTextView.setText(model.getProgramName());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    private static class ViewHolder {
        private TextView mTextDuration;
        private TextView mTextView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
