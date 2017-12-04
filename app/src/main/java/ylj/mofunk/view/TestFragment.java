package ylj.mofunk.view;


import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.model.Base.BaseFragment;


public class TestFragment extends BaseFragment {
    @BindView(R.id.videoplayer)
    StandardGSYVideoPlayer videoPlayer;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_test);
        ButterKnife.bind(getActivity());

    }

    @Override
    protected void initData() {
//        String url = "http://live.bilibili.com/api/playurl?player=1&quality=0&cid=190";
//        String url = "http://live.bilibili.com/api/playurl?player=1&quality=0&cid=67223";
        String url = "http://fus.cdn.krcom.cn/003TLOK3lx07gdk3DtN601040103bAUx0k03.mp4?label=mp4_1080p&template=27&Expires=1512099803&ssig=z7Gq%2Fjkmpc&KID=unistore,video";
//        String url="http://txy.live-play.acgvideo.com/live-txy/592024/live_301940_3118084.flv?wsSecret=bdbae182ef3b847167c2ee606b1d908d";
//增加封面
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.huge);

        Map<String, String> map = new HashMap<>();
        map.put("User-Agent", "OhMyBiliBili Android Client/2.1 (100332338@qq.com)");

        GSYVideoManager manager = GSYVideoManager.instance();
        GSYVideoOptionBuilder builder = new GSYVideoOptionBuilder();
        builder.setMapHeadData(map).setUrl(url).setVideoTitle("我的样子平平无奇").setIsTouchWiget(true).setCacheWithPlay(true).setThumbPlay(true).setSeekOnStart(System.currentTimeMillis()).
                setThumbImageView(imageView).
                build(videoPlayer);

//        new Retrofit.Builder()
//                .client(new OkHttpClient.Builder().addInterceptor(new ApiConstans.UserAgentInterceptor()).build())
//                .baseUrl(ApiConstans.LIVE_BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(ApiService.class).getLiveUrl(67223).map(responseBody -> {
//            try {
//                String str = responseBody.string();
//                String result = str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("]") - 1);
//                return result;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        })
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Function<String, Publisher<Long>>() {
//                    @Override
//                    public Publisher<Long> apply(String s) throws Exception {
////                        playVideo(s);
//                        LogUtils.e("直播地址url"+s);
//                        return Flowable.timer(2000, TimeUnit.MILLISECONDS);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aLong -> {
//
//                }, throwable -> LogUtils.e("直播地址url获取失败" + throwable.getMessage()));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void lazyLoad() {

    }
}
