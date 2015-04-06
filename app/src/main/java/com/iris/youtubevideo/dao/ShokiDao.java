package com.iris.youtubevideo.dao;

/**
 * Created by 이민석
 *
 * @since 2015 - 04 - 06
 */
public class ShokiDao {
    private String videoUrl;
    private boolean downLoaderYn;
    private String title;
    private int level;

    public ShokiDao(String videoUrl, int level, String title) {
        this.videoUrl = videoUrl;
        this.level = level;
        this.title = title;
    }

    public boolean isDownLoaderYn() {
        return downLoaderYn;
    }

    public void setDownLoaderYn(boolean downLoaderYn) {
        this.downLoaderYn = downLoaderYn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
