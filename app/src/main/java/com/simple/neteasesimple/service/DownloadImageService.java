package com.simple.neteasesimple.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.simple.neteasesimple.splash.bean.Ads;
import com.simple.neteasesimple.splash.bean.AdsDetail;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.ImageUtil;
import com.simple.neteasesimple.until.Md5Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadImageService extends IntentService {

    public static final String ADS_DATE="ads";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.simple.neteasesimple.service.action.FOO";
    private static final String ACTION_BAZ = "com.simple.neteasesimple.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.simple.neteasesimple.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.simple.neteasesimple.service.extra.PARAM2";

    public DownloadImageService() {
        super("DownloadImageService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadImageService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadImageService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }

        //接收到http请求的对象
        Ads ads = (Ads) intent.getSerializableExtra(ADS_DATE);
        //下载图片
        List<AdsDetail> list = ads.getAds();
        for(int i = 0 ;i<list.size();i++){
            AdsDetail detail =  list.get(i);
            List<String> imgs = detail.getRes_url();
            if(null!=imgs){
                String img_url = imgs.get(0);
                if(!TextUtils.isEmpty(img_url)){
                    //图片地址转成唯一的MD5文件名
                    String catche_neme = Md5Helper.toMD5(img_url);
                    //先判断图片是否存在,如果存在不下载
                    if(!ImageUtil.checkImageIsDownLoad(catche_neme)){
                        Log.i("caiiiac","downing");
                        //下载图片
                        downloadImage(img_url,catche_neme);
                    }


                }
            }

        }

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void downloadImage(String url ,String MD5_name){
        try {
            URL uri = new URL(url);
            URLConnection urlConnection = uri.openConnection();
//            //拿到图片的边间,大小
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;


            //拿到一张图片
            Bitmap bitmap =  BitmapFactory.decodeStream(urlConnection.getInputStream()) ;
            //往sd卡上写入图片
            saveToSD(bitmap,MD5_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 保存到sd
    public void saveToSD( Bitmap bitmap,String MD5_name){
        if(null==bitmap){
            return ;
        }
        //判断手机Sd卡是否装载
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File SD = Environment.getExternalStorageDirectory();
            File cacheFile = new File(SD, Constant.CACHE);
            if(!cacheFile.exists()){
                cacheFile.mkdirs();
            }

            File image = new File(cacheFile,MD5_name+".jpg");
            //图片存在
            if(image.exists()){
                return;
            }
            try {
                FileOutputStream image_out = new FileOutputStream(image);

                //bitmap压缩到sd卡
                bitmap.compress(Bitmap.CompressFormat.JPEG,60,image_out);
                image_out.flush();
                image_out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
