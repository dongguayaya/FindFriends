package com.dongua.findfriends.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongua.findfriends.R;
import com.dongua.findfriends.model.AddFriendModel;
import com.dongua.framework.helper.GlideHelper;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 多type
 */
public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<AddFriendModel> mList;
    private LayoutInflater inflater;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    //标题
    public  static final int TVPE_TITLE=0;
    //内容
    public static final int TYPE_CONTENT=1;
    public AddFriendAdapter(Context mContext, List<AddFriendModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if(viewType==TVPE_TITLE){
            return new TitleViewHolder(inflater.inflate(R.layout.layout_search_title_item,null));
        }else if(viewType==TYPE_CONTENT){
            return new ContentTitleViewHolder(inflater.inflate(R.layout.layout_search_user_item,null));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    AddFriendModel model=mList.get(position);
    if(model.getType()==TVPE_TITLE){
        ((TitleViewHolder)holder).tvtitle.setText(model.getTitle());

    }else if(model.getType()==TYPE_CONTENT){
        //设置头像
        GlideHelper.loadUrl(mContext,model.getPhoto(),((ContentTitleViewHolder)holder).iv_photo);
        //设置性别
        ((ContentTitleViewHolder)holder).iv_sex.setImageResource(model.isSex()?R.drawable.img_boy_icon:R.drawable.img_girl_icon);
        //设置昵称
        ((ContentTitleViewHolder)holder).tv_nickname.setText(model.getNikeName());
        //设置年龄
        ((ContentTitleViewHolder)holder).tv_age.setText(model.getAge()+"岁");
        //设置描述
        ((ContentTitleViewHolder)holder).tv_desc.setText(model.getDesc());
        //通讯录
        if(model.isContact()){
            ((ContentTitleViewHolder)holder).ll_contact_info.setVisibility(View.VISIBLE);
            ((ContentTitleViewHolder)holder).tv_contact_name.setText(model.getContactName());
            ((ContentTitleViewHolder)holder).tv_contact_phone.setText(model.getContactPhone());
        }
    }
    //点击事件
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onClickListener!=null){
                onClickListener.OnClick(position);
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }
    class TitleViewHolder extends RecyclerView.ViewHolder{
        private TextView tvtitle;
        public TitleViewHolder(View itemView){
            super(itemView);
            tvtitle=itemView.findViewById(R.id.tv_title);
        }

    }
    class ContentTitleViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView iv_photo;
        private ImageView iv_sex;
        private TextView tv_nickname;
        private TextView tv_age;
        private TextView tv_desc;
        private LinearLayout ll_contact_info;
        private TextView tv_contact_name;
        private TextView tv_contact_phone;
        public ContentTitleViewHolder(View itemView){
            super(itemView);
            iv_photo=itemView.findViewById(R.id.iv_photo);
            iv_sex=itemView.findViewById(R.id.iv_sex);
            tv_nickname=itemView.findViewById(R.id.tv_nickname);
            tv_age=itemView.findViewById(R.id.tv_age);
            tv_desc=itemView.findViewById(R.id.tv_desc);

        }
    }
    public interface  OnClickListener{
        void OnClick(int position);
    }

}
