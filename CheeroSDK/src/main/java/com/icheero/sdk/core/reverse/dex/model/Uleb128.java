package com.icheero.sdk.core.reverse.dex.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 引用：https://github.com/zcd/libosu/blob/master/src/main/java/com/zerocooldown/libosu/util/Uleb128.java
 *
 * See <a href="https://en.wikipedia.org/wiki/LEB128#Unsigned_LEB128">wiki</a> for details on encoding.
 * <p>
 * Adopted from <a href="http://llvm.org/docs/doxygen/html/LEB128_8h_source.html">LLVM source</a>. This class assumes
 * the maximum input value fits into a Java long type.
 */
public final class Uleb128
{
    private final static int BITS_LONG = 64;
    private final static int MASK_DATA = 0x7f;
    private final static int MASK_CONTINUE = 0x80;
    private byte[] mBytes;
    private final long value;

    private Uleb128(long value)
    {
        this.value = value;
    }

    /**
     * Reads a ULEB128 value from the input bytestream. This method does not close the stream when finished.
     * @param bytes ULEB128-encoded long bytes.
     * @return a Uleb128 instance representing the byte sequence.
     * @throws IOException on any read error.
     * @throws ArithmeticException if the encoding indicates that the input value is too large to fit into a Java long.
     * This method will consume the input up to the maximum allowed ULEB128 encoding which
     * fits into a long, even on error.
     */
    public static Uleb128 from(InputStream bytes) throws IOException
    {
        return new Uleb128(decode(bytes));
    }

    public static Uleb128 from(byte[] bytes)
    {
        return new Uleb128(decode(bytes));
    }

    public static Uleb128 from(long value)
    {
        return new Uleb128(value);
    }

    private static long decode(InputStream bytes) throws IOException
    {
        long value = 0;
        int bitSize = 0;
        int read;
        do
        {
            if ((read = bytes.read()) == -1)
                throw new IOException("Unexpected EOF");
            value += ((long) read & MASK_DATA) << bitSize;
            bitSize += 7;
            if (bitSize >= BITS_LONG)
                throw new ArithmeticException("ULEB128 value exceeds maximum value for long type.");
        }
        while ((read & MASK_CONTINUE) != 0);
        return value;
    }

    private static long decode(byte[] bytes)
    {
        long value = 0;
        int bitSize = 0;
        int read;
        int offset = 0;
        do
        {
            read = bytes[offset];
            value += ((long) read & MASK_DATA) << bitSize;
            bitSize += 7;
            if (bitSize >= BITS_LONG)
                throw new ArithmeticException("ULEB128 value exceeds maximum value for long type.");
            offset++;
        }
        while ((read & MASK_CONTINUE) != 0);
        return value;
    }

    private byte[] encode(long value)
    {
        ArrayList<Byte> bytes = new ArrayList<>();
        do
        {
            byte b = (byte) (value & MASK_DATA);
            value >>= 7;
            if (value != 0)
            {
                b |= MASK_CONTINUE;
            }
            bytes.add(b);
        }
        while (value != 0);

        mBytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++)
        {
            mBytes[i] = bytes.get(i);
        }
        return mBytes;
    }

    public long asLong()
    {
        return value;
    }

    public byte[] asBytes()
    {
        return encode(value);
    }

    public String toString()
    {
        return Long.toString(value);
    }

    public int getLength()
    {
        return asBytes().length;
    }
}
