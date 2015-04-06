package com.iris.youtubevideo.constatns;

/**
 * Created by 이민석
 *
 * @since 2015 - 04 - 06
 */
public enum VideoUrl {

    video1("https://youtu.be/xtgZ_lcxVQk?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급1"),
    video2("https://youtu.be/HcbIbDXwl-4?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급2"),
    video3("https://youtu.be/7pHzc-XY7-0?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급3"),
    video4("https://youtu.be/CNqn2t-VdOM?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급4"),
    video5("https://youtu.be/8a7R3XIZ1l8?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급5"),
    video6("https://youtu.be/O8gaoiYBCUE?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급6"),
    video7("https://youtu.be/-Tlb8zngd6U?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급7"),
    video8("https://youtu.be/WTmTguA_HME?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급8"),
    video9("https://youtu.be/gfcUTE2MSP8?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급9"),
    video10("https://youtu.be/__ypYqDoYK8?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급10"),
    video11("https://youtu.be/ZVDuS2ARAOo?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급11"),
    video12("https://youtu.be/_0sSrZOUHrw?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급12"),
    video13("https://youtu.be/jZ4xlTz8DUk?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급13"),
    video14("https://youtu.be/YQxIX4iTs_s?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급14"),
    video15("https://youtu.be/lfkDF6yXD_E?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급15"),
    video16("https://youtu.be/yOmcyEJU9bA?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급16"),
    video17("https://youtu.be/CC8l3t_HpbE?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급17"),
    video18("https://youtu.be/u6WmTSmLB9w?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급18"),
    video19("https://youtu.be/9kFrJ-g-1ag?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급19"),
    video20("https://youtu.be/AT4oClECfmI?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급20"),
    video21("https://youtu.be/PSRivY4o6V0?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급21"),
    video22("https://youtu.be/0w1ovbBfCyk?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급22"),
    video23("https://youtu.be/AMfx4npCOfw?list=PLZZhxvdAjAr5Bqd324uk5xY-3CRrZZqBw", "초급23"),;


    public String videoUrl;
    public String videoTitle;

    VideoUrl(String url, String title) {
        videoTitle = title;
        videoUrl = url;
    }
}
