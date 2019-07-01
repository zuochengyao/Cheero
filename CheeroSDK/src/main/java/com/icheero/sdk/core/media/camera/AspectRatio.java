package com.icheero.sdk.core.media.camera;

import android.os.Parcel;
import android.os.Parcelable;

import com.icheero.sdk.util.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

/**
 * 用于描述宽度和高度之间的比例关系的不可变类。
 */
class AspectRatio implements Comparable<AspectRatio>, Parcelable
{
    private static final SparseArrayCompat<SparseArrayCompat<AspectRatio>> mCache = new SparseArrayCompat<>(16);

    private final int mWidth;
    private final int mHeight;

    private AspectRatio(int width, int height)
    {
        this.mWidth = width;
        this.mHeight = height;
    }

    /**
     * 返回由 {@code width} 和 {@code height} 值指定的{@link AspectRatio}的实例。
     * 值 {@code width} 和 {@code height} 将通过其最大公因数减少。
     *
     * @param width 宽度
     * @param height 高度
     * @return AspectRatio实例对象
     */
    public static AspectRatio of(int width, int height)
    {
        int gcd = Common.gcd(width, height);
        width /= gcd;
        height /= gcd;
        SparseArrayCompat<AspectRatio> arrayX = mCache.get(width);
        AspectRatio ratio;
        if (arrayX == null)
        {
            ratio = new AspectRatio(width, height);
            arrayX = new SparseArrayCompat<>();
            arrayX.put(height, ratio);
            mCache.put(width, arrayX);
        }
        else
        {
            ratio = arrayX.get(height);
            if (ratio == null)
            {
                ratio = new AspectRatio(width, height);
                arrayX.put(height, ratio);
            }
        }
        return ratio;
    }

    /**
     * 将 {@code ratio} 类型格式转换成 {@link AspectRatio} 对象
     *
     * @param ratio 比例格式，Eg：4:3, 16:9
     * @return AspectRatio实例对象
     */
    public static AspectRatio parse(String ratio)
    {
        int position = ratio.indexOf(":");
        if (position == -1)
            throw new IllegalArgumentException("Malformed aspect ratio: " + ratio);
        String[] ratioArr = ratio.split(":");
        try
        {
            int width = Integer.parseInt(ratioArr[0].trim());
            int height = Integer.parseInt(ratioArr[1].trim());
            return AspectRatio.of(width, height);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Malformed aspect ratio: " + ratio, e);
        }
    }

    public AspectRatio inverse()
    {
        return AspectRatio.of(mHeight, mWidth);
    }

    public int getWidth()
    {
        return mWidth;
    }

    public int getHeight()
    {
        return mHeight;
    }

    public boolean match(Size size)
    {
        int gcd = Common.gcd(size.getWidth(), size.getHeight());
        int x = size.getWidth() / gcd;
        int y = size.getHeight() / gcd;
        return mWidth == x && mHeight == y;
    }

    public float toFloat()
    {
        return (float) mWidth / mHeight;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof AspectRatio)
        {
            AspectRatio ratio = (AspectRatio) obj;
            return mWidth == ratio.getWidth() && mHeight == ratio.getWidth();
        }
        return false;
    }

    @NonNull
    @Override
    public String toString()
    {
        return mWidth + ":" + mHeight;
    }

    @Override
    public int hashCode()
    {
        // assuming most sizes are <2^16, doing a rotate will give us perfect hashing
        return mHeight ^ ((mWidth << (Integer.SIZE / 2)) | (mWidth >>> (Integer.SIZE / 2)));
    }

    @Override
    public int compareTo(AspectRatio o)
    {
        if (equals(o))
            return 0;
        else if (toFloat() - o.toFloat() > 0)
            return 1;
        return -1;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
    }

    public static final Creator<AspectRatio> CREATOR = new Creator<AspectRatio>()
    {
        @Override
        public AspectRatio createFromParcel(Parcel in)
        {
            int x = in.readInt();
            int y = in.readInt();
            return new AspectRatio(x, y);
        }

        @Override
        public AspectRatio[] newArray(int size)
        {
            return new AspectRatio[size];
        }
    };
}
