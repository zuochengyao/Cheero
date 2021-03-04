package com.icheero.sdk.core.reverse.so;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.util.IOUtils;
import com.icheero.sdk.util.Log;

public class SoParser implements IParser
{
    private static final Class TAG = SoParser.class;

    private byte[] mSoData;
    private Elf32 mElf32;

    public SoParser(byte[] soData)
    {
        this.mSoData = soData;
        mElf32 = new Elf32();
    }

    @Override
    public void parse()
    {
        Log.i(TAG, "+++++++++++++++++++Elf Header+++++++++++++++++");
        parseHeader(IOUtils.copyBytes(mSoData, 0, 52));
        Log.i(TAG, "header:\n" + mElf32.hdr.toString());

        Log.i(TAG, "+++++++++++++++++++Program Header+++++++++++++++++");
        int p_header_offset = IOUtils.byte2Int(mElf32.hdr.e_phoff); // 52;
        Log.i(TAG, "offset:" + p_header_offset);
        parseProgramHeaderList(mSoData, p_header_offset);
        mElf32.printPhdrList();

        Log.i(TAG, "+++++++++++++++++++Section Header++++++++++++++++++");
        int s_header_offset = IOUtils.byte2Int(mElf32.hdr.e_shoff); // 12592;
        Log.i(TAG, "offset:" + s_header_offset);
        parseSectionHeaderList(mSoData, s_header_offset);
        mElf32.printShdrList();
    }

    /**
     * 解析elf文件的头信息
     */
    private void parseHeader(byte[] header)
    {
        if (header == null)
        {
            Log.i(TAG, "Header is null");
            return;
        }
        IOUtils.copyBytes(header, 0, mElf32.hdr.e_ident);
        IOUtils.copyBytes(header, 16, mElf32.hdr.e_type);
        IOUtils.copyBytes(header, 18, mElf32.hdr.e_machine);
        IOUtils.copyBytes(header, 20, mElf32.hdr.e_version);
        IOUtils.copyBytes(header, 24, mElf32.hdr.e_entry);
        IOUtils.copyBytes(header, 28, mElf32.hdr.e_phoff);
        IOUtils.copyBytes(header, 32, mElf32.hdr.e_shoff);
        IOUtils.copyBytes(header, 36, mElf32.hdr.e_flags);
        IOUtils.copyBytes(header, 40, mElf32.hdr.e_ehsize);
        IOUtils.copyBytes(header, 42, mElf32.hdr.e_phentsize);
        IOUtils.copyBytes(header, 44, mElf32.hdr.e_phnum);
        IOUtils.copyBytes(header, 46, mElf32.hdr.e_shentsize);
        IOUtils.copyBytes(header, 48, mElf32.hdr.e_shnum);
        IOUtils.copyBytes(header, 50, mElf32.hdr.e_shstrndx);

    }

    /**
     * 解析段头信息内容
     */
    private void parseSectionHeaderList(byte[] header, int offset)
    {
        int headerSize = 40;//40个字节
        int headerCount = IOUtils.byte2Short(mElf32.hdr.e_shnum);//头部的个数
        byte[] des = new byte[headerSize];
        for (int i = 0; i < headerCount; i++)
        {
            System.arraycopy(header, i * headerSize + offset, des, 0, headerSize);
            mElf32.shdrList.add(parseSectionHeader(des));
        }
    }

    private Elf32.Elf32_Shdr parseSectionHeader(byte[] header){
        Elf32.Elf32_Shdr shdr = new Elf32.Elf32_Shdr();
        IOUtils.copyBytes(header, 0, shdr.sh_name);
        IOUtils.copyBytes(header, 4, shdr.sh_type);
        IOUtils.copyBytes(header, 8, shdr.sh_flags);
        IOUtils.copyBytes(header, 12, shdr.sh_addr);
        IOUtils.copyBytes(header, 16, shdr.sh_offset);
        IOUtils.copyBytes(header, 20, shdr.sh_size);
        IOUtils.copyBytes(header, 24, shdr.sh_link);
        IOUtils.copyBytes(header, 28, shdr.sh_info);
        IOUtils.copyBytes(header, 32, shdr.sh_addralign);
        IOUtils.copyBytes(header, 36, shdr.sh_entsize);
        return shdr;
    }

    /**
     * 解析程序头信息
     */
    private void parseProgramHeaderList(byte[] header, int offset)
    {
        // header占用32个字节
        int headerSize = 32;
        // header个数
        int headerCount = IOUtils.byte2Short(mElf32.hdr.e_phnum);
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
        IOUtils.copyBytes(header, 0, phdr.p_type);
        IOUtils.copyBytes(header, 4, phdr.p_offset);
        IOUtils.copyBytes(header, 8, phdr.p_vaddr);
        IOUtils.copyBytes(header, 12, phdr.p_paddr);
        IOUtils.copyBytes(header, 16, phdr.p_filesz);
        IOUtils.copyBytes(header, 20, phdr.p_memsz);
        IOUtils.copyBytes(header, 24, phdr.p_flags);
        IOUtils.copyBytes(header, 28, phdr.p_align);
        return phdr;
    }

}
