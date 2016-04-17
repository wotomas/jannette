package helper;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import model.Clothes;
import model.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class TagBuilder {
    private static String mURL;
    private static Tags mTag;
    private static Firebase mFirebase;
    private final static String FIREBASE_URL = "https://jannette.firebaseio.com/videos";

    public TagBuilder(String url) {
        //url - https://www.youtube.com/watch?v=nAxTe-gUnwk
        mFirebase = new Firebase(FIREBASE_URL);
        this.mURL = "nAxTe-gUnwk";
        this.mTag = new Tags();
    }

    public TagBuilder build(String result, Clothes cloth) {
        //result - /Users/jkimab/Desktop/hackathon/jannette/jarvis/video/filename.png.sample
        //mURL - nAxTe-gUnwk
        this.mTag.getClothes().add(cloth);
        return this;
    }

    public void postToFirebase(Long timestamp) {
        String stringTimestamp = timestamp.toString();
        System.out.println("string: " + stringTimestamp);
        mFirebase = mFirebase.child(mURL).child(stringTimestamp);

        for(Clothes clothes : mTag.getClothes()) {

            mFirebase.setValue(clothes, new Firebase.CompletionListener() {
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if(firebaseError != null) {
                        System.out.println("Error get Trace: " + firebaseError.getMessage());
                    } else {
                        System.out.println("no Error upload complete");
                    }
                }
            });
        }
    }

    public void postToFliker() {
        //TODO: need to implemented post to server
    }
}
