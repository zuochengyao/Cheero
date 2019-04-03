package com.icheero.sdk.core.reverse.so;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

public class SoParser
{
    private static final Class TAG = SoParser.class;

    private Elf32 mElf32;

    public SoParser(Elf32 elf32)
    {
        this.mElf32 = elf32;
    }

    /**
     * 解析elf文件的头信息
     */
    public void parseHeader(byte[] header)
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
        int headerSize = 40;//40个字节
        int headerCount = Common.byte2Short(mElf32.hdr.e_shnum);//头部的个数
        byte[] des = new byte[headerSize];
        for (int i = 0; i < headerCount; i++)
        {
            System.arraycopy(header, i * headerSize + offset, des, 0, headerSize);
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

    /**
     * 解析程序头信息
     */
    public void parseProgramHeaderList(byte[] header, int offset)
    {
        // header占用32个字节
        int headerSize = 32;
        // header个数
        int headerCount = Common.byte2Short(mElf32.hdr.e_phnum);
        byte[] des = new byte[headerSize];
        for (int i = 0; i < headerCount; i++)
        {
            System.arraycopy(header, i * headerSize + offset, des, 0, headerSize);
            mElf32.phdrList.add(parseProgramHeader(des));
        }
    }

    private Elf32.Elf32_Phdr parseProgramHeader(byte[] header)
    {
        Elf32.Elf32_Phdr phdr = new Elf32.Elf32_Phdr();
        phdr.p_type = Common.copyBytes(header, 0, 4);
        phdr.p_offset = Common.copyBytes(header, 4, 4);
        phdr.p_vaddr = Common.copyBytes(header, 8, 4);
        phdr.p_paddr = Common.copyBytes(header, 12, 4);
        phdr.p_filesz = Common.copyBytes(header, 16, 4);
        phdr.p_memsz = Common.copyBytes(header, 20, 4);
        phdr.p_flags = Common.copyBytes(header, 24, 4);
        phdr.p_align = Common.copyBytes(header, 28, 4);
        return phdr;
    }
}
