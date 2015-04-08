package com.iris.youtubevideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iris.youtubevideo.R;
import com.iris.youtubevideo.dao.ShokiDao;

import java.util.ArrayList;

/**
 * Created by 이민석
 *
 * @since 2015 - 04 - 06
 */
public class ListAdapter extends BaseAdapter {

    private ArrayList<ShokiDao> mListData;
    private Context mContext;

    public ListAdapter(ArrayList<ShokiDao> listData, Context context) {
        this.mListData = listData;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.item_list_title_txt);
//        if(mListData.get(position).isDownLoaderYn()) {
//            text.setText(mListData.get(position).getTitle());
//        }
//        else {
//            text.setText(mListData.get(position).getTitle()+" 다운로드 필요");
//        }
        text.setText(mListData.get(position).getTitle());


        return convertView;
    }
}
