package com.iris.youtubevideo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iris.youtubevideo.adapter.ListAdapter;
import com.iris.youtubevideo.constatns.VideoUrl;
import com.iris.youtubevideo.dao.ShokiDao;
import com.iris.youtubevideo.lib.EncodedStream;
import com.iris.youtubevideo.lib.Utils;
import com.iris.youtubevideo.lib.Youvider;
import com.iris.youtubevideo.lib.YouviderInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ListActivity extends ActionBarActivity {


    private ArrayList<ShokiDao> mListData;
    private ListView mListiView;
    private ListAdapter mListAdapter;

    private final static String SD_CARD_PATH = "/sdcard/android/data/com.iris.youtubevideo/shoki";
    private final static String LOCAL_PATH =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/shoki";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        int level = getIntent().getExtras().getInt("level");

        init();
        initData(level);
    }

    private void init() {
        mListData = new ArrayList<ShokiDao>();

        mListiView = (ListView) findViewById(R.id.list_listview);

        mListAdapter = new ListAdapter(mListData, this);

        mListiView.setOnItemClickListener(mItemClickListener);
    }


    private void onActionDownload(ShokiDao item) throws IOException {

        String link = item.getVideoUrl();
        if (!TextUtils.isEmpty(link)){

            checkDownloadLink(link);

//            File file = new File(path);
//            if(file.exists()) {
//                Intent intent = new Intent();
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse(path), "vidio/*");
//                startActivity(intent);
//            }
//            else {
//                onDownloadLink(link);
//            }
        }
        else {
            showMessage("Please input video link");
            return ;
        }

    }

    private void checkDownloadLink(String link) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("해당 영상이 다운로드 되어 있는지 확인중이니 기달리시죠");
        progressDialog.show();
        new AsyncTask<String, Void, YouviderInfo>() {
            private String videoLink="";
            @Override
            protected YouviderInfo doInBackground(String... links) {
                try {
                    videoLink = links[0];
                    return Youvider.getVideoInfo(links[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(YouviderInfo youviderInfo) {
                super.onPostExecute(youviderInfo);
                if (youviderInfo == null){
                    showMessage("Could not parse video info");
                    return;
                }
                if (youviderInfo.encodedStreams == null || youviderInfo.encodedStreams.size() == 0){
                    showMessage("Could not parse video stream");
                    return;
                }
                progressDialog.hide();
                onPlayVideoInfo(youviderInfo, videoLink);
            }
        }.execute(link);
    }

    private void onDownloadLink(String link){
        final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("영상 다운로드 준비중입니다 기다리세요!");
            progressDialog.show();
            new AsyncTask<String, Void, YouviderInfo>() {
                @Override
                protected YouviderInfo doInBackground(String... links) {
                    try {
                        return Youvider.getVideoInfo(links[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(YouviderInfo youviderInfo) {
                    super.onPostExecute(youviderInfo);
                    if (youviderInfo == null){
                        showMessage("비디오 변환 실패 전화주세요~");
                        return;
                    }
                    if (youviderInfo.encodedStreams == null || youviderInfo.encodedStreams.size() == 0){
                        showMessage("비디오 변환 실패 전홪쉐요~2");
                        return;
                    }
                    progressDialog.hide();
                    onReceiveDownloadInfo(youviderInfo);
                }
        }.execute(link);
    }

    private void onPlayVideoInfo(final YouviderInfo info, String link){
        String[] streams = new String[info.encodedStreams.size()];
//        for (int i=0; i<info.encodedStreams.size(); i++)
        streams[0] = info.encodedStreams.get(0).itag.toString();
        EncodedStream stream = info.encodedStreams.get(0);
        String fileName = info.videoTitle == null ? "Unknown title video" : Utils.getSafeFileNameFor(info.videoTitle);
        fileName += "." + stream.itag.format.toLowerCase();

        String path = getExternalStoragePath() + File.separator + fileName;

        File file = new File(path);
        if(file.exists()) {
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(path), "video/*");
            startActivity(intent);
        }
        else {
            onDownloadLink(link);
        }
    }

    private void onReceiveDownloadInfo(final YouviderInfo info){
//        String[] streams = new String[info.encodedStreams.size()];
////        for (int i=0; i<info.encodedStreams.size(); i++)
//            streams[0] = info.encodedStreams.get(0).itag.toString();

        EncodedStream stream = info.encodedStreams.get(0);
        String fileName = info.videoTitle == null ? "Unknown title video" : Utils.getSafeFileNameFor(info.videoTitle);
        fileName += "." + stream.itag.format.toLowerCase();
        String path = getExternalStoragePath() + File.separator + fileName;
        onChooseDownloadVideo(stream, path);

//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Download video")
//                .setItems(streams, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int position) {
////                        showMessage("Choose " + position);
//                        EncodedStream stream = info.encodedStreams.get(position);
//                        String fileName = info.videoTitle == null ? "Unknown title video" : Utils.getSafeFileNameFor(info.videoTitle);
//                        fileName += "." + stream.itag.format.toLowerCase();
//                        String path = getExternalStoragePath() + File.separator + fileName;
//                        onChooseDownloadVideo(stream, path);
//                    }
//                })
//                .create();
//        dialog.show();
    }

    private void onChooseDownloadVideo(final EncodedStream stream, final String path){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMax(100);
        progressDialog.show();

        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return Youvider.downloadEncodedStream(stream, path, new Youvider.OnDownloadingProgress() {
                    @Override
                    public void onDownloadingProgress(int percent) {
                        publishProgress(percent);
                    }
                });
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressDialog.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                progressDialog.hide();
                if (result){
                    showMessage("Video saved: " + path);
                }else{
                    showMessage("Error downloading video");
                }
            }
        }.execute();

    }

    public  String getExternalStoragePath(){
        String path = null;
        try{
            File extSt = Environment.getExternalStorageDirectory();
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion < 8) {

                path = extSt.getAbsolutePath() + "/Android" + "/data"+ "/com.shoki";
            } else {
                path = getExternalFilesDir(null).getAbsolutePath();
            }

            return path;

//
//            String str = Environment.getExternalStorageState();
//            if ( str.equals(Environment.MEDIA_MOUNTED)) {
//
//                File file = new File(SD_CARD_PATH);
//                if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
//                    file.mkdirs();
//
//                return SD_CARD_PATH;
//            }
//            else {
//                path = LOCAL_PATH;
//
//                File file = new File(path);
//                if( !file.exists() )  // 원하는 경로에 폴더가 있는지 확인
//                    file.mkdirs();
//
//                return path;
//            }

        }catch (Exception ex){
            Toast.makeText(ListActivity.this, "SD Card 인식 실패", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return "";
        }
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                onActionDownload(mListData.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    private void initData(int level) {
        if(mListData != null) {
            mListData.add(new ShokiDao(VideoUrl.video1.videoUrl, level, VideoUrl.video1.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video2.videoUrl, level, VideoUrl.video2.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video3.videoUrl, level, VideoUrl.video3.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video4.videoUrl, level, VideoUrl.video4.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video5.videoUrl, level, VideoUrl.video5.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video6.videoUrl, level, VideoUrl.video6.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video7.videoUrl, level, VideoUrl.video7.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video8.videoUrl, level, VideoUrl.video8.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video9.videoUrl, level, VideoUrl.video9.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video10.videoUrl, level, VideoUrl.video10.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video11.videoUrl, level, VideoUrl.video11.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video12.videoUrl, level, VideoUrl.video12.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video13.videoUrl, level, VideoUrl.video13.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video14.videoUrl, level, VideoUrl.video14.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video15.videoUrl, level, VideoUrl.video15.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video16.videoUrl, level, VideoUrl.video16.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video17.videoUrl, level, VideoUrl.video17.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video18.videoUrl, level, VideoUrl.video18.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video19.videoUrl, level, VideoUrl.video19.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video20.videoUrl, level, VideoUrl.video20.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video21.videoUrl, level, VideoUrl.video21.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video22.videoUrl, level, VideoUrl.video22.videoTitle));
            mListData.add(new ShokiDao(VideoUrl.video23.videoUrl, level, VideoUrl.video23.videoTitle));

//            final Dialog dialog = new Dialog(this);
//            dialog.show();
//            new AsyncTask<ArrayList<ShokiDao>, Void, HashMap<String, Boolean>>() {
//                @Override
//                protected HashMap<String, Boolean> doInBackground(ArrayList<ShokiDao>... links) {
//                    try {
//                        HashMap<String, Boolean> resultMap = new HashMap<String, Boolean>();
//                        for(int i = 0 ; i < links[0].size(); i ++) {
//                            YouviderInfo info = Youvider.getVideoInfo(links[0].get(i).getVideoUrl());
//
//                            String[] streams = new String[info.encodedStreams.size()];
//                            streams[0] = info.encodedStreams.get(0).itag.toString();
//                            EncodedStream stream = info.encodedStreams.get(0);
//                            String fileName = info.videoTitle == null ? "Unknown title video" : Utils.getSafeFileNameFor(info.videoTitle);
//                            fileName += "." + stream.itag.format.toLowerCase();
//
//                            String path = getExternalStoragePath() + File.separator + fileName;
//
//                            File file = new File(path);
//                            if(file.exists()) {
//                                resultMap.put(links[0].get(i).getTitle(), true);
//                            }
//                            else {
//                                resultMap.put(links[0].get(i).getTitle(), false);
//                            }
//                        }
//                        return resultMap;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//
//                @Override
//                protected void onPostExecute(HashMap<String, Boolean> result) {
//                    super.onPostExecute(result);
//                    dialog.dismiss();
//                    for(int i = 0 ; i < mListData.size() ; i ++) {
//                        ShokiDao item = mListData.get(i);
//                        item.setDownLoaderYn(result.get(mListData.get(i).getTitle()));
//                        mListData.set(i, item);
//                    }
//
//                    mListiView.setAdapter(mListAdapter);
//                }
//            }.execute(mListData);
        }

        mListiView.setAdapter(mListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
