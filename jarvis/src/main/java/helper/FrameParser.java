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

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class FrameParser {
    private static String mPath;
    private static String mFileName;

    public FrameParser(String path, String name) {
        mPath = path;
        mFileName = name;
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
            File file = new File(mPath + "/" + "frame_" + frameNumber + ".png");
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

    public ArrayList<File> getTwentyFrames() {
        int count = getFrameCount(new File(mPath + "/" + mFileName + ".mp4"));
        int adder = count / 20;
        ArrayList<File> files = new ArrayList<File>();

        for(int i = 0; i < count; i= i + adder) {
            files.add(getFrame(i));
        }

        return files;
    }
}
