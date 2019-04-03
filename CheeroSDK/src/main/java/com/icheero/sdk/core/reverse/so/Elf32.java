package com.icheero.sdk.core.reverse.so;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

import java.util.ArrayList;

public class Elf32
{
    private static final Class TAG = Elf32.class;

    public Elf32_Rel rel;
    public Elf32_Rela rela;
    public Elf32_Hdr hdr;
    public ArrayList<Elf32_Sym> symList;
    public ArrayList<Elf32_Phdr> phdrList; // 可能会有多个程序头
    public ArrayList<Elf32_Shdr> shdrList; // 可能会有多个段头
    public ArrayList<Elf32_Strtb> strtbList; // 可能会有多个字符串值

    public Elf32()
    {
        rel = new Elf32_Rel();
        rela = new Elf32_Rela();
        hdr = new Elf32_Hdr();
        symList = new ArrayList<>();
        phdrList = new ArrayList<>();
        shdrList = new ArrayList<>();
        strtbList = new ArrayList<>();
    }

    public class Elf32_Rel
    {
        public byte[] r_offset = new byte[4];
        public byte[] r_info = new byte[4];

        @Override
        public String toString()
        {
            return "r_offset:" + Common.byte2HexString(r_offset) + ";r_info:" + Common.byte2HexString(r_info);
        }
    }

    public class Elf32_Rela
    {
        public byte[] r_offset = new byte[4];
        public byte[] r_info = new byte[4];
        public byte[] r_addend = new byte[4];

        @Override
        public String toString()
        {
            return "r_offset:" + Common.byte2HexString(r_offset) + ";r_info:" + Common.byte2HexString(r_info) + ";r_addend:" + Common.byte2HexString(r_addend);
        }
    }

    public static class Elf32_Sym
    {
        public byte[] st_name = new byte[4];
        public byte[] st_value = new byte[4];
        public byte[] st_size = new byte[4];
        public byte st_info;
        public byte st_other;
        public byte[] st_shndx = new byte[2];

        @Override
        public String toString()
        {

            return "st_name:" + Common.byte2HexString(st_name) +
                    "\nst_value:" + Common.byte2HexString(st_value) +
                    "\nst_size:" + Common.byte2HexString(st_size) +
                    "\nst_info:" + (st_info / 16) +
                    "\nst_other:" + (((short) st_other) & 0xF) +
                    "\nst_shndx:" + Common.byte2HexString(st_shndx);
        }
    }

    public void printSymList()
    {
        for (int i = 0; i < symList.size(); i++)
        {
            Log.i(TAG, "The " + (i + 1) + " Symbol Table:");
            Log.i(TAG, symList.get(i).toString());
        }
    }
    //Bind字段==》st_info
    public static final int STB_LOCAL = 0;
    public static final int STB_GLOBAL = 1;
    public static final int STB_WEAK = 2;
    //Type字段==》st_other
    public static final int STT_NOTYPE = 0;
    public static final int STT_OBJECT = 1;
    public static final int STT_FUNC = 2;
    public static final int STT_SECTION = 3;
    public static final int STT_FILE = 4;

    public class Elf32_Hdr
    {
        public byte[] e_ident = new byte[16];
        public byte[] e_type = new byte[2];
        public byte[] e_machine = new byte[2];
        public byte[] e_version = new byte[4];
        public byte[] e_entry = new byte[4];
        /**
         * 程序头（Program Header）内容在整个文件的偏移值
         * 可以用这个值来定位程序头的开始位置，用于解析程序头信息
         */
        public byte[] e_phoff = new byte[4];
        /**
         * 段头（Section Header）内容在整个文件的偏移值
         * 可以用这个值来定位段头的开始位置，用于解析段头的信息
         */
        public byte[] e_shoff = new byte[4];
        public byte[] e_flags = new byte[4];
        public byte[] e_ehsize = new byte[2];
        public byte[] e_phentsize = new byte[2];
        /**
         * 程序头的个数
         */
        public byte[] e_phnum = new byte[2];
        public byte[] e_shentsize = new byte[2];
        /**
         * 段头的个数
         */
        public byte[] e_shnum = new byte[2];
        /**
         * 是 String 段在真个段列表中的索引值
         * 用于后面定位 String 段的位置
         */
        public byte[] e_shstrndx = new byte[2];

        @Override
        public String toString()
        {
            return "magic:" + Common.byte2HexString(e_ident) + "\ne_type:" + Common.byte2HexString(e_type) + "\ne_machine:" + Common.byte2HexString(e_machine) + "\ne_version:" + Common.byte2HexString(e_version) + "\ne_entry:" + Common
                    .byte2HexString(e_entry) + "\ne_phoff:" + Common.byte2HexString(e_phoff) + "\ne_shoff:" + Common.byte2HexString(e_shoff) + "\ne_flags:" + Common.byte2HexString(e_flags) + "\ne_ehsize:" + Common
                    .byte2HexString(e_ehsize) + "\ne_phentsize:" + Common.byte2HexString(e_phentsize) + "\ne_phnum:" + Common.byte2HexString(e_phnum) + "\ne_shentsize:" + Common.byte2HexString(e_shentsize) + "\ne_shnum:" + Common
                    .byte2HexString(e_shnum) + "\ne_shstrndx:" + Common.byte2HexString(e_shstrndx);
        }
    }

    public static class Elf32_Phdr
    {
        public byte[] p_type = new byte[4];
        public byte[] p_offset = new byte[4];
        public byte[] p_vaddr = new byte[4];
        public byte[] p_paddr = new byte[4];
        public byte[] p_filesz = new byte[4];
        public byte[] p_memsz = new byte[4];
        public byte[] p_flags = new byte[4];
        public byte[] p_align = new byte[4];

        @Override
        public String toString()
        {
            return "p_type:" + Common.byte2HexString(p_type) + "\np_offset:" + Common.byte2HexString(p_offset) + "\np_vaddr:" + Common.byte2HexString(p_vaddr) + "\np_paddr:" + Common.byte2HexString(p_paddr) + "\np_filesz:" + Common
                    .byte2HexString(p_filesz) + "\np_memsz:" + Common.byte2HexString(p_memsz) + "\np_flags:" + Common.byte2HexString(p_flags) + "\np_align:" + Common.byte2HexString(p_align);
        }
    }

    public void printPhdrList()
    {
        for (int i = 0; i < phdrList.size(); i++)
        {
            Log.i(TAG, "The " + (i + 1) + " Program Header:");
            Log.i(TAG, phdrList.get(i).toString());
        }
    }

    public static class Elf32_Shdr
    {
        public byte[] sh_name = new byte[4];
        public byte[] sh_type = new byte[4];
        public byte[] sh_flags = new byte[4];
        public byte[] sh_addr = new byte[4];
        public byte[] sh_offset = new byte[4];
        public byte[] sh_size = new byte[4];
        public byte[] sh_link = new byte[4];
        public byte[] sh_info = new byte[4];
        public byte[] sh_addralign = new byte[4];
        public byte[] sh_entsize = new byte[4];

        @Override
        public String toString()
        {
            return "sh_name:" + Common.byte2HexString(sh_name)/*Common.byte2Int(sh_name)*/ + "\nsh_type:" + Common.byte2HexString(sh_type) + "\nsh_flags:" + Common.byte2HexString(sh_flags) + "\nsh_add:" + Common
                    .byte2HexString(sh_addr) + "\nsh_offset:" + Common.byte2HexString(sh_offset) + "\nsh_size:" + Common.byte2HexString(sh_size) + "\nsh_link:" + Common.byte2HexString(sh_link) + "\nsh_info:" + Common
                    .byte2HexString(sh_info) + "\nsh_addralign:" + Common.byte2HexString(sh_addralign) + "\nsh_entsize:" + Common.byte2HexString(sh_entsize);
        }
    }

    /****************sh_type********************/
    public static final int SHT_NULL = 0;
    public static final int SHT_PROGBITS = 1;
    public static final int SHT_SYMTAB = 2;
    public static final int SHT_STRTAB = 3;
    public static final int SHT_RELA = 4;
    public static final int SHT_HASH = 5;
    public static final int SHT_DYNAMIC = 6;
    public static final int SHT_NOTE = 7;
    public static final int SHT_NOBITS = 8;
    public static final int SHT_REL = 9;
    public static final int SHT_SHLIB = 10;
    public static final int SHT_DYNSYM = 11;
    public static final int SHT_NUM = 12;
    public static final int SHT_LOPROC = 0x70000000;
    public static final int SHT_HIPROC = 0x7fffffff;
    public static final int SHT_LOUSER = 0x80000000;
    public static final int SHT_HIUSER = 0xffffffff;
    public static final int SHT_MIPS_LIST = 0x70000000;
    public static final int SHT_MIPS_CONFLICT = 0x70000002;
    public static final int SHT_MIPS_GPTAB = 0x70000003;
    public static final int SHT_MIPS_UCODE = 0x70000004;
    /*****************sh_flag***********************/
    public static final int SHF_WRITE = 0x1;
    public static final int SHF_ALLOC = 0x2;
    public static final int SHF_EXECINSTR = 0x4;
    public static final int SHF_MASKPROC = 0xf0000000;
    public static final int SHF_MIPS_GPREL = 0x10000000;

    public void printShdrList()
    {
        for (int i = 0; i < shdrList.size(); i++)
        {
            Log.i(TAG, "The " + (i + 1) + " Section Header:");
            Log.i(TAG, shdrList.get(i) + "");
        }
    }

    public static class Elf32_Strtb
    {
        public byte[] str_name;
        public int len;

        @Override
        public String toString()
        {
            return "str_name:" + str_name + "len:" + len;
        }
    }
}
