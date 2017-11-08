package ylj.mofunk.model.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collection;
import java.util.List;

import ylj.mofunk.R;
import ylj.mofunk.model.Entity.GankAndroid;

/**
 * Created by ice on 2016/8/29.
 */
public class MyReAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final Object mLock = new Object();
    List<GankAndroid> arrayList;
    Context context;

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(Context context, OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.context = context;
    }

    public MyReAdapter() {
        this.context = context;
        this.arrayList = arrayList;
    }
    public List<GankAndroid> getmObjects() {
        return arrayList;
    }

    public MyReAdapter(List<GankAndroid> arrayList) {
        this.arrayList = arrayList;
    }
    public void upData(List<GankAndroid> object) {
        synchronized (mLock) {
            this.arrayList=object;
        }
        notifyDataSetChanged();
    }
    public void addAll(Collection<? extends GankAndroid> collection) {
        synchronized (mLock) {
            arrayList.addAll(collection);
            int postion = getItemCount();
            notifyItemRangeInserted(postion, collection.size());
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gank_android_list, parent, false);

        return new ViewHolder(v);
    }


//    public Bitmap resizeBitmap(int position) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(context.getResources(), arrayList.get(position).getDrawable(), options);
//
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//
//        options.inSampleSize = 2;
//        options.inJustDecodeBounds = false;
//
//        return BitmapFactory.decodeResource(context.getResources(), arrayList.get(position).getDrawable(), options);
//    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GankAndroid b=arrayList.get(position);
        if(b.getImages()!=null){
            Glide.with(context).load(b.getImages().get(0)).error(R.mipmap.apply_fail).into(holder.imageView);
        }else{
            holder.imageView.setImageResource(R.mipmap.apply_fail);
        }


        holder.tvName.setText("by "+b.getWho());
        holder.tvTitle.setText(b.getDesc());
        holder.tvType.setText(b.getType());

        ViewCompat.setTransitionName(holder.imageView, String.valueOf(position) + "_image");


        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.imageView, position); // 2
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (arrayList.size()==0||arrayList==null)?0:arrayList.size();
    }
}


class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView tvTitle;
    TextView tvType;
    TextView tvName;

    public ViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

         tvTitle=(TextView) itemView.findViewById(R.id.tvTitle);
         tvType=(TextView) itemView.findViewById(R.id.tvType);
         tvName=(TextView) itemView.findViewById(R.id.tvName);


    }




}
  interface  OnItemClickListener {
    void onItemClick(View view, int position);
}
