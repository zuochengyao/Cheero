package com.icheero.sdk.core.media.camera;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class SizeMap
{
    private final Map<AspectRatio, SortedSet<Size>> mRatios = new HashMap<>();

    boolean add(Size size)
    {
        for (AspectRatio ratio : mRatios.keySet())
        {
            if (ratio.match(size))
            {
                SortedSet<Size> sizes = mRatios.get(ratio);
                if (Objects.requireNonNull(sizes).contains(size))
                    return false;
                else
                {
                    sizes.add(size);
                    return true;
                }
            }
        }
        SortedSet<Size> sizes = new TreeSet<>();
        sizes.add(size);
        mRatios.put(AspectRatio.of(size.getWidth(), size.getHeight()), sizes);
        return true;
    }

    void remove(AspectRatio ratio)
    {
        mRatios.remove(ratio);
    }

    Set<AspectRatio> ratios()
    {
        return mRatios.keySet();
    }

    SortedSet<Size> get(AspectRatio ratio)
    {
        return mRatios.get(ratio);
    }

    void clear()
    {
        mRatios.clear();
    }

    boolean isEmpty()
    {
        return mRatios.isEmpty();
    }
}
