package com.example.mwojcik.masteringnetworkingtwo;

public class SimpleItem {

    private String mImageUrl;
    private String mAuthor;
    private int mLikes;

    public SimpleItem(String mImageUrl, String mAuthor, int mLikes) {
        this.mImageUrl = mImageUrl;
        this.mAuthor = mAuthor;
        this.mLikes = mLikes;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getmLikes() {
        return mLikes;
    }

    public void setmLikes(int mLikes) {
        this.mLikes = mLikes;
    }
}
