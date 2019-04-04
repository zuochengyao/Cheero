package com.icheero.sdk.core.reverse.so;

import com.icheero.sdk.core.reverse.IParser;
import com.icheero.sdk.util.FileUtils;
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
        parseHeader(FileUtils.copyBytes(mSoData, 0, 52));
        Log.i(TAG, "header:\n" + mElf32.hdr.toString());

        Log.i(TAG, "+++++++++++++++++++Program Header+++++++++++++++++");
        int p_header_offset = FileUtils.byte2Int(mElf32.hdr.e_phoff); // 52;
        Log.i(TAG, "offset:" + p_header_offset);
        parseProgramHeaderList(mSoData, p_header_offset);
        mElf32.printPhdrList();

        Log.i(TAG, "+++++++++++++++++++Section Header++++++++++++++++++");
        int s_header_offset = FileUtils.byte2Int(mElf32.hdr.e_shoff); // 12592;
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
        FileUtils.copyBytes(header, 0, mElf32.hdr.e_ident);
        FileUtils.copyBytes(header, 16, mElf32.hdr.e_type);
        FileUtils.copyBytes(header, 18, mElf32.hdr.e_machine);
        FileUtils.copyBytes(header, 20, mElf32.hdr.e_version);
        FileUtils.copyBytes(header, 24, mElf32.hdr.e_entry);
        FileUtils.copyBytes(header, 28, mElf32.hdr.e_phoff);
        FileUtils.copyBytes(header, 32, mElf32.hdr.e_shoff);
        FileUtils.copyBytes(header, 36, mElf32.hdr.e_flags);
        FileUtils.copyBytes(header, 40, mElf32.hdr.e_ehsize);
        FileUtils.copyBytes(header, 42, mElf32.hdr.e_phentsize);
        FileUtils.copyBytes(header, 44, mElf32.hdr.e_phnum);
        FileUtils.copyBytes(header, 46, mElf32.hdr.e_shentsize);
        FileUtils.copyBytes(header, 48, mElf32.hdr.e_shnum);
        FileUtils.copyBytes(header, 50, mElf32.hdr.e_shstrndx);

    }

    /**
     * 解析段头信息内容
     */
    private void parseSectionHeaderList(byte[] header, int offset)
    {
        int headerSize = 40;//40个字节
        int headerCount = FileUtils.byte2Short(mElf32.hdr.e_shnum);//头部的个数
        byte[] des = new byte[headerSize];
        for (int i = 0; i < headerCount; i++)
        {
            System.arraycopy(header, i * headerSize + offset, des, 0, headerSize);
            mElf32.shdrList.add(parseSectionHeader(des));
        }
    }

    private Elf32.Elf32_Shdr parseSectionHeader(byte[] header){
        Elf32.Elf32_Shdr shdr = new Elf32.Elf32_Shdr();
        FileUtils.copyBytes(header, 0, shdr.sh_name);
        FileUtils.copyBytes(header, 4, shdr.sh_type);
        FileUtils.copyBytes(header, 8, shdr.sh_flags);
        FileUtils.copyBytes(header, 12, shdr.sh_addr);
        FileUtils.copyBytes(header, 16, shdr.sh_offset);
        FileUtils.copyBytes(header, 20, shdr.sh_size);
        FileUtils.copyBytes(header, 24, shdr.sh_link);
        FileUtils.copyBytes(header, 28, shdr.sh_info);
        FileUtils.copyBytes(header, 32, shdr.sh_addralign);
        FileUtils.copyBytes(header, 36, shdr.sh_entsize);
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
        int headerCount = FileUtils.byte2Short(mElf32.hdr.e_phnum);
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
        FileUtils.copyBytes(header, 0, phdr.p_type);
        FileUtils.copyBytes(header, 4, phdr.p_offset);
        FileUtils.copyBytes(header, 8, phdr.p_vaddr);
        FileUtils.copyBytes(header, 12, phdr.p_paddr);
        FileUtils.copyBytes(header, 16, phdr.p_filesz);
        FileUtils.copyBytes(header, 20, phdr.p_memsz);
        FileUtils.copyBytes(header, 24, phdr.p_flags);
        FileUtils.copyBytes(header, 28, phdr.p_align);
        return phdr;
    }

}
