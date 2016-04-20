package curry.stephen.universalanroidtv.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class MediaItemModel implements Parcelable {

    private int mID;
    private int mTabID;
    private UUID mIdentifier;
    private Uri mPicture;
    private Uri mThumbnail;
    private Uri mVideoPath;
    private String mTitle;
    private String mDirector;
    private String mActor;
    private String mContent;
    private int mInterestingCount;

    public MediaItemModel(Builder builder) {
        mID = builder.mID;
        mTabID = builder.mTabID;
        mIdentifier = builder.mIdentifier;
        mPicture = builder.mPicture;
        mThumbnail = builder.mThumbnail;
        mVideoPath = builder.mVideoPath;
        mTitle = builder.mTitle;
        mDirector = builder.mDirector;
        mActor = builder.mActor;
        mContent = builder.mContent;
        mInterestingCount = builder.mInterestingCount;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getTabID() {
        return mTabID;
    }

    public void setTabID(int tabID) {
        mTabID = tabID;
    }

    public UUID getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(UUID identifier) {
        mIdentifier = identifier;
    }

    public Uri getPicture() {
        return mPicture;
    }

    public void setPicture(Uri picture) {
        mPicture = picture;
    }

    public Uri getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Uri thumbnail) {
        mThumbnail = thumbnail;
    }

    public Uri getVideoPath() {
        return mVideoPath;
    }

    public void setVideoPath(Uri videoPath) {
        mVideoPath = videoPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public String getActor() {
        return mActor;
    }

    public void setActor(String actor) {
        mActor = actor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getInterestingCount() {
        return mInterestingCount;
    }

    public void setInterestingCount(int interestingCount) {
        mInterestingCount = interestingCount;
    }

    public static class Builder {

        private int mID;
        private int mTabID;
        private UUID mIdentifier;
        private Uri mPicture;
        private Uri mThumbnail;
        private Uri mVideoPath;
        private String mTitle;
        private String mDirector;
        private String mActor;
        private String mContent;
        private int mInterestingCount;

        public MediaItemModel build() {
            return new MediaItemModel(this);
        }

        public Builder id(int id) {
            mID = id;
            return this;
        }

        public Builder tabID(int tabID) {
            mTabID = tabID;
            return this;
        }

        public Builder identifier(UUID identifier) {
            mIdentifier = identifier;
            return this;
        }

        public Builder picture(Uri picture) {
            mPicture = picture;
            return this;
        }

        public Builder thumbnail(Uri thumbnail) {
            mThumbnail = thumbnail;
            return this;
        }

        public Builder videoPath(Uri videoPath) {
            mVideoPath = videoPath;
            return this;
        }

        public Builder title(String title) {
            mTitle = title;
            return this;
        }

        public Builder director(String director) {
            mDirector = director;
            return this;
        }

        public Builder actor(String actor) {
            mActor = actor;
            return this;
        }
        public Builder content(String content) {
            mContent = content;
            return this;
        }

        public Builder isInterestring(int interestingCount) {
            mInterestingCount = interestingCount;
            return this;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mID);
        dest.writeInt(this.mTabID);
        dest.writeSerializable(this.mIdentifier);
        dest.writeParcelable(this.mPicture, flags);
        dest.writeParcelable(this.mThumbnail, flags);
        dest.writeParcelable(this.mVideoPath, flags);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDirector);
        dest.writeString(this.mActor);
        dest.writeString(this.mContent);
        dest.writeInt(this.mInterestingCount);
    }

    protected MediaItemModel(Parcel in) {
        this.mID = in.readInt();
        this.mTabID = in.readInt();
        this.mIdentifier = (UUID) in.readSerializable();
        this.mPicture = in.readParcelable(Uri.class.getClassLoader());
        this.mThumbnail = in.readParcelable(Uri.class.getClassLoader());
        this.mVideoPath = in.readParcelable(Uri.class.getClassLoader());
        this.mTitle = in.readString();
        this.mDirector = in.readString();
        this.mActor = in.readString();
        this.mContent = in.readString();
        this.mInterestingCount = in.readInt();
    }

    public static final Creator<MediaItemModel> CREATOR = new Creator<MediaItemModel>() {
        @Override
        public MediaItemModel createFromParcel(Parcel source) {
            return new MediaItemModel(source);
        }

        @Override
        public MediaItemModel[] newArray(int size) {
            return new MediaItemModel[size];
        }
    };
}
