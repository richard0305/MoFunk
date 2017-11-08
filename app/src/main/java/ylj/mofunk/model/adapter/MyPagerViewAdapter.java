package ylj.mofunk.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.zyyoona7.lib.EasyPopup;

import java.io.File;
import java.util.List;

import ylj.mofunk.R;
import ylj.mofunk.model.Api.ApiConstans;
import ylj.mofunk.model.Entity.ImagePosition;
import ylj.mofunk.model.tools.ImageUtils;
import ylj.mofunk.model.tools.ToastUtils;

/**
 * Created by 我的样子平平无奇 on 2017/9/6 09:51.
 * Email: 2256669598@qq.com
 */

public class MyPagerViewAdapter extends PagerAdapter {
    List<ImagePosition> imgs;

    private EasyPopup mCirclePop;
    Context mContext;

    public MyPagerViewAdapter(Context context, List<ImagePosition> imgs, EasyPopup mCirclePop) {

        this.mContext = context;
        this.imgs = imgs;
        this.mCirclePop = mCirclePop;

    }

    @Override
    public int getCount() { // 获得size
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((PhotoView) object);  //删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        final ImagePosition im = imgs.get(position);

        final PhotoView full_image = new PhotoView(mContext);
        full_image.setWillNotDraw(true);
        full_image.setTag(im.getUrl());
        //full_image
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        full_image.setLayoutParams(params);

        Glide.with(mContext).load(im.getUrl()).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        bitmap = resource;
                        full_image.setWillNotDraw(false);
                        full_image.setImageBitmap(resource);
                    }
                });
        ((ViewPager) container).addView(full_image);

        full_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mCirclePop.showAtLocation(v, Gravity.BOTTOM, 0, 0);

                return true;
            }
        });

        TextView tvShare = mCirclePop.getView(R.id.tvShare);
        TextView tvSave = mCirclePop.getView(R.id.tvSave);
        TextView tvCancle = mCirclePop.getView(R.id.tvCancle);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "来自我的样子平平无奇的分享：");
                share.putExtra(Intent.EXTRA_TEXT, "美图分享:" + im.getUrl());
                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(Intent.createChooser(share, "美图分享"));

            }
        });


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();

                if(full_image.getTag().equals(im.getUrl()) &&full_image.getTag()!=null ){
                    if (ImageUtils.save(ImageUtils.view2Bitmap(full_image), ApiConstans.SavePath + "mofunk" + System.currentTimeMillis() + ".png", Bitmap.CompressFormat.PNG)) {
                        ToastUtils.showShortToastSafe("保存成功");
                    } else {
                        ToastUtils.showShortToastSafe("保存失败");
                    }
                }else{
                    ToastUtils.showShortToastSafe("发生了时空裂缝，图片丢失了 =-=");
                }


            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
            }
        });

        return full_image;
    }


    /**
     * 通知媒体库更新文件夹
     * * @param context
     * * @param filePath 文件夹
     * * *
     */
    public void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }


}
