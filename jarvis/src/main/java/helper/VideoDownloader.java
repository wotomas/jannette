package helper;

import com.github.axet.vget.VGet;

import java.io.File;
import java.net.URL;

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class VideoDownloader {
    private static String mPath;
    private static String mURL;
    private static String mFileName;

    public VideoDownloader() {
        // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
        // ex: "/Users/jkimab/Desktop/hackathon/jannette/jarvis/video"
        // ex: "The Prodigy - Breathe (HQ).mp4"
        mPath = "";
        mURL = "";
        mFileName = "";
    }

    public VideoDownloader(String path, String url) {
        // ex: http://www.youtube.com/watch?v=Nj6PFaDmp6c
        // ex: "/Users/jkimab/Desktop/hackathon/jannette/jarvis/video"
        // ex: "The Prodigy - Breathe (HQ).mp4"
        mPath = path;
        mURL = url;
        mFileName = "";
    }

    public boolean downloadVideos() {
        try {
            VGet v = new VGet(new URL(mURL), new File(mPath));
            v.download();

            mFileName = v.getVideo().getTitle();
            System.out.println("Video name is: " + mFileName);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static String getmPath() {
        return mPath;
    }

    public static void setmPath(String mPath) {
        VideoDownloader.mPath = mPath;
    }

    public static String getmURL() {
        return mURL;
    }

    public static void setmURL(String mURL) {
        VideoDownloader.mURL = mURL;
    }

    public static String getmFileName() {
        return mFileName;
    }

    public static void setmFileName(String mFileName) {
        VideoDownloader.mFileName = mFileName;
    }
}
