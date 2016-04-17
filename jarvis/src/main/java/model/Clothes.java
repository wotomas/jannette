package model;

/**
 * Created by jkimab on 2016. 4. 16..
 */
public class Clothes {
    private String name;
    private String url;
    private String details;
    private double threshold;

    public Clothes(String name, String url, String details, double count) {
        this.name = name;
        this.url = url;
        this.details = details;
        this.threshold = count;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
