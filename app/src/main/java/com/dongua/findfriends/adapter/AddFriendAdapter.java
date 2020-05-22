package com.dongua.findfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongua.findfriends.R;
import com.dongua.findfriends.model.AddFriendModel;

import java.util.List;

/**
 * 多type
 */
public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AddFriendAdapter> mList;
    private LayoutInflater inflater;

    //标题
    private static final int TVPE_TITLE=0;
    //内容
    private static final int TYPE_CONTENT=1;
    public AddFriendAdapter(Context mContext, List<AddFriendAdapter> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==TVPE_TITLE){

            return new TitleViewHolder(inflater.inflate(R.layout.layout_search_title_item,null));
        }else if(viewType==TYPE_CONTENT){
            return new ContentTitleViewHolder(inflater.inflate(R.layout.layout_search_user_item,null));

        }
        return null;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemViewType(position);
    }
    class TitleViewHolder extends RecyclerView.ViewHolder{
        public TitleViewHolder(View itemView){
            super(itemView);
        }
    }
    class ContentTitleViewHolder extends RecyclerView.ViewHolder{
        public ContentTitleViewHolder(View itemView){
            super(itemView);
        }
    }
}
