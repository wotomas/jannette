package helper;

import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.MP4Util;
import org.jcodec.containers.mp4.boxes.MovieBox;
import org.jcodec.scale.AWTUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class FrameParser {
    private static String mPath;
    private static String mFileName;
    private static ArrayList<Long> mTimeStamps;

    public FrameParser(String path, String name) {
        mPath = path;
        mFileName = name;
        mTimeStamps = new ArrayList<Long>();
    }

    public static ArrayList<Long> getmTimeStamps() {
        return mTimeStamps;
    }

    public static void setmTimeStamps(ArrayList<Long> mTimeStamps) {
        FrameParser.mTimeStamps = mTimeStamps;
    }

    public static String getmPath() {
        return mPath;
    }

    public static void setmPath(String mPath) {
        FrameParser.mPath = mPath;
    }

    public static String getmFileName() {
        return mFileName;
    }

    public static void setmFileName(String mFileName) {
        FrameParser.mFileName = mFileName;
    }

    private File getFrame(int frameNumber) {
        try {
            Picture data = FrameGrab.getNativeFrame(new File(mPath + "/" + mFileName + ".mp4"), frameNumber);
            File file = new File(mPath + "/" + "frame" + frameNumber + ".png");
            AWTUtil.savePicture(data, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JCodecException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getFrameCount(File file) {
        MovieBox movieBox = null;
        try {
            movieBox = MP4Util.createRefMovie(file);
            return movieBox.getVideoTrack().getFrameCount();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private long getDuration(File file) {
        MovieBox movieBox = null;
        try {
            movieBox = MP4Util.createRefMovie(file);
            return movieBox.getVideoTrack().getMediaDuration();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map<File, Long> getTimestampFromString() {
        File file = new File(mPath + "/" + mFileName + ".mp4");
        int count = getFrameCount(file);
        long totalDuration = getDuration(file);
        System.out.println("Total Duration is: " + totalDuration);

        int adder = count / 20;
        long durationAdder = totalDuration / 20;
        Map<File, Long> files = new HashMap<File, Long>();

        long j = 0;
        for(int i = 0; i < count; i= i + adder) {
            System.out.println("Adding Frames to directory! " + i);
            files.put(getFrame(i), j);
            j = j + durationAdder;
        }

        return files;
    }

    public ArrayList<File> getTwentyFrames() {
        File file = new File(mPath + "/" + mFileName + ".mp4");
        int count = getFrameCount(file);

        int adder = count / 20;
        ArrayList<File> files = new ArrayList<File>();

        for(int i = 0; i < count; i= i + adder) {
            System.out.println("Adding Frames to directory! " + i);
            files.add(getFrame(i));
        }

        return files;
    }
}
