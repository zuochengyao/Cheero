package com.icheero.app.activity.reverse;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.reverse.so.Elf32;
import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

public class DisposeSoActivity extends BaseActivity
{
    private Elf32 mElf32;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispose_so);
        doInit();
        byte[] data = new byte[4];
        data[0] = 0;
        data[1] = 0;
        data[2] = 4;
        data[3] = -34;
        int i = Common.byte2Int(data);
        Log.i(TAG, i + "");
    }

    private void doInit()
    {
        mElf32 = new Elf32();
        byte[] soData = FileUtils.readRawResource(this, R.raw.libcheero);
    }

    /**
     * 解析elf文件的头信息
     */
    private void parseHeader(byte[] header, int offset)
    {
        if (header == null)
        {
            Log.i(TAG, "Header is null");
            return;
        }
        mElf32.hdr.e_ident = Common.copyBytes(header, 0, 16);
        mElf32.hdr.e_type = Common.copyBytes(header, 16, 2);
        mElf32.hdr.e_machine = Common.copyBytes(header, 18, 2);
        mElf32.hdr.e_version = Common.copyBytes(header, 20, 4);
        mElf32.hdr.e_entry = Common.copyBytes(header, 24, 4);
        mElf32.hdr.e_phoff = Common.copyBytes(header, 28, 4);
        mElf32.hdr.e_shoff = Common.copyBytes(header, 32, 4);
        mElf32.hdr.e_flags = Common.copyBytes(header, 36, 4);
        mElf32.hdr.e_ehsize = Common.copyBytes(header, 40, 2);
        mElf32.hdr.e_phentsize = Common.copyBytes(header, 42, 2);
        mElf32.hdr.e_phnum = Common.copyBytes(header, 44,2);
        mElf32.hdr.e_shentsize = Common.copyBytes(header, 46,2);
        mElf32.hdr.e_shnum = Common.copyBytes(header, 48, 2);
        mElf32.hdr.e_shstrndx = Common.copyBytes(header, 50, 2);
    }


    /**
     * 解析段头信息内容
     */
    public void parseSectionHeaderList(byte[] header, int offset)
    {
        int header_size = 40;//40个字节
        int header_count = Common.byte2Short(mElf32.hdr.e_shnum);//头部的个数
        byte[] des = new byte[header_size];
        for (int i = 0; i < header_count; i++)
        {
            System.arraycopy(header, i * header_size + offset, des, 0, header_size);
            mElf32.shdrList.add(parseSectionHeader(des));
        }
    }

    private Elf32.Elf32_Shdr parseSectionHeader(byte[] header){
        Elf32.Elf32_Shdr shdr = new Elf32.Elf32_Shdr();
        shdr.sh_name = Common.copyBytes(header, 0, 4);
        shdr.sh_type = Common.copyBytes(header, 4, 4);
        shdr.sh_flags = Common.copyBytes(header, 8, 4);
        shdr.sh_addr = Common.copyBytes(header, 12, 4);
        shdr.sh_offset = Common.copyBytes(header, 16, 4);
        shdr.sh_size = Common.copyBytes(header, 20, 4);
        shdr.sh_link = Common.copyBytes(header, 24, 4);
        shdr.sh_info = Common.copyBytes(header, 28, 4);
        shdr.sh_addralign = Common.copyBytes(header, 32, 4);
        shdr.sh_entsize = Common.copyBytes(header, 36, 4);
        return shdr;
    }
}
