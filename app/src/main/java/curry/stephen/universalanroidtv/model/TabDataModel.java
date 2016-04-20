package curry.stephen.universalanroidtv.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class TabDataModel implements Parcelable {

    private int mID;
    private Uri mPictureNormal;
    private Uri mPictureSelected;
    private List<MediaItemModel> mMediaItemModelList;

    public TabDataModel(Builder builder) {
        mID = builder.mID;
        mPictureNormal = builder.mPictureNormal;
        mPictureSelected = builder.mPictureSelected;
        mMediaItemModelList = builder.mMediaItemModelList;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public Uri getPictureNormal() {
        return mPictureNormal;
    }

    public void setPictureNormal(Uri pictureNormal) {
        mPictureNormal = pictureNormal;
    }

    public Uri getPictureSelected() {
        return mPictureSelected;
    }

    public void setPictureSelected(Uri pictureSelected) {
        mPictureSelected = pictureSelected;
    }

    public List<MediaItemModel> getMediaItemModelList() {
        return mMediaItemModelList;
    }

    public void setMediaItemModelList(List<MediaItemModel> mediaItemModelList) {
        mMediaItemModelList = mediaItemModelList;
    }

    public static class Builder {

        private int mID;
        private Uri mPictureNormal;
        private Uri mPictureSelected;
        private List<MediaItemModel> mMediaItemModelList;

        public TabDataModel build() {
            return new TabDataModel(this);
        }

        public Builder id(int id) {
            mID = id;
            return this;
        }

        public Builder picureNormal(Uri pictureNormal) {
            mPictureNormal = pictureNormal;
            return this;
        }

        public Builder pictureSelected(Uri pictureSelected) {
            mPictureSelected = pictureSelected;
            return this;
        }

        public Builder mediaItemModelList(List<MediaItemModel> mediaItemModelList) {
            mMediaItemModelList = mediaItemModelList;
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
        dest.writeParcelable(this.mPictureNormal, flags);
        dest.writeParcelable(this.mPictureSelected, flags);
        dest.writeTypedList(mMediaItemModelList);
    }

    protected TabDataModel(Parcel in) {
        this.mID = in.readInt();
        this.mPictureNormal = in.readParcelable(Uri.class.getClassLoader());
        this.mPictureSelected = in.readParcelable(Uri.class.getClassLoader());
        this.mMediaItemModelList = in.createTypedArrayList(MediaItemModel.CREATOR);
    }

    public static final Creator<TabDataModel> CREATOR = new Creator<TabDataModel>() {
        @Override
        public TabDataModel createFromParcel(Parcel source) {
            return new TabDataModel(source);
        }

        @Override
        public TabDataModel[] newArray(int size) {
            return new TabDataModel[size];
        }
    };
}
