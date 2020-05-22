package com.dongua.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.framework.R;
import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * 3d星球适配器
 */
public class CloudTagAdapter extends TagsAdapter {
    private List<String> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public CloudTagAdapter(Context mContext,List<String> mList) {
        this.mList = mList;
        this.mContext = mContext;
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        //初始化View
        View view=inflater.inflate(R.layout.layout_star_view_item,null);
        //初始化控件
        ImageView imageView=view.findViewById(R.id.iv_star_icon);
        TextView textView=view.findViewById(R.id.tv_star_name);
        //赋值
        textView.setText(mList.get(position));
        switch (position%10){
            case 0:
                imageView.setImageResource(R.drawable.icontest);
                break;
            case 1:
                imageView.setImageResource(R.drawable.icontest);
                break;
            case 2:
                imageView.setImageResource(R.drawable.icontest);
                break;
            case 3:
                imageView.setImageResource(R.drawable.icontest);
                break;
            case 4:
                imageView.setImageResource(R.drawable.icontest);
                break;
            case 5:
                imageView.setImageResource(R.drawable.icontest);
                break;
            default:
                imageView.setImageResource(R.drawable.icontest);
                break;
        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
