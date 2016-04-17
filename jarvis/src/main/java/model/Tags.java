package model;

import java.util.ArrayList;

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class Tags {
    private ArrayList<Clothes> clothes;

    public Tags() {
        this.clothes = new ArrayList<Clothes>();
    }

    public Tags(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }
}
