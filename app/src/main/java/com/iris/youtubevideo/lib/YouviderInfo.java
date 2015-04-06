package com.iris.youtubevideo.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Youvider
 * Created by 8psman on 2/5/2015.
 * Email: 8psman@gmail.com
 */
public class YouviderInfo {

    public String videoTitle;

    public List<EncodedStream> encodedStreams;


    public YouviderInfo(){
        encodedStreams = new ArrayList<EncodedStream>();
    }

    public List<EncodedStream> getEncodedStreams(){
        return encodedStreams;
    }
}