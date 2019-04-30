package com.icheero.sdk.core.reverse.dex.model;

import com.icheero.sdk.core.reverse.dex.DexParser;
import com.icheero.sdk.util.FileUtils;

import androidx.annotation.NonNull;

/**
 * (1) class_idx：描述具体的 class 类型 ，值是 type_ids 的一个 index 。值必须是一个 class 类型 ，不能是数组类型或者基本类型 。
 * (2) access_flags： 描述 class 的访问类型 ，诸如 public , final , static 等 。在 dex-format.html 里 “access_flagsDefinitions” 有具体的描述 。
 * (3) superclass_idx：描述 supperclass 的类型 ，值的形式跟 class_idx 一样 。
 * (4) interfaces_off：值为偏移地址 ，指向 class 的 interfaces , 被指向的数据结构为 type_list 。class 若没有interfaces ,值为 0。
 * (5) source_file_idx：表示源代码文件的信息 ，值是 string_ids 的一个 index 。若此项信息缺失 ，此项值赋值为NO_INDEX=0xffff ffff 
 * (6) annotions_off：值是一个偏移地址 ，指向的内容是该 class 的注释 ，位置在 data 区，格式为annotations_direcotry_item 。若没有此项内容 ，值为 0 。
 * (7) class_data_off：值是一个偏移地址 ，指向的内容是该 class 的使用到的数据 ，位置在 data 区，格式为class_data_item 。若没有此项内容 ，值为 0 。该结构里有很多内容 ，详细描述该 class 的 field ，method, method 里的执行代码等信息 ，后面有一个比较大的篇幅来讲述 class_data_item 。
 * (8) static_value_off：值是一个偏移地址 ，指向 data 区里的一个列表 ( list ) ，格式为 encoded_array_item。若没有此项内容 ，值为 0 。
 */
public class ClassDefItem
{
    public byte[] classIdx = new byte[4];
    public byte[] accessFlags = new byte[4];
    public byte[] superclassIdx = new byte[4];
    public byte[] interfacesOff = new byte[4];
    public byte[] sourceFileIdx = new byte[4];
    public byte[] annotationsOff = new byte[4];
    public byte[] classDataOff = new byte[4];
    public byte[] staticValueOff = new byte[4];

    public final static int
            ACC_PUBLIC = 0x00000001,       // class, field, method, ic
            ACC_PRIVATE = 0x00000002,       // field, method, ic
            ACC_PROTECTED = 0x00000004,       // field, method, ic
            ACC_STATIC = 0x00000008,       // field, method, ic
            ACC_FINAL = 0x00000010,       // class, field, method, ic
            ACC_SYNCHRONIZED = 0x00000020,       // method (only allowed on natives)
            ACC_SUPER = 0x00000020,       // class (not used in Dalvik)
            ACC_VOLATILE = 0x00000040,       // field
            ACC_BRIDGE = 0x00000040,       // method (1.5)
            ACC_TRANSIENT = 0x00000080,       // field
            ACC_VARARGS = 0x00000080,       // method (1.5)
            ACC_NATIVE = 0x00000100,       // method
            ACC_INTERFACE = 0x00000200,       // class, ic
            ACC_ABSTRACT = 0x00000400,       // class, method, ic
            ACC_STRICT = 0x00000800,       // method
            ACC_SYNTHETIC = 0x00001000,       // field, method, ic
            ACC_ANNOTATION = 0x00002000,       // class, ic (1.5)
            ACC_ENUM = 0x00004000,       // class, field, ic (1.5)
            ACC_CONSTRUCTOR = 0x00010000,       // method (Dalvik only)
            ACC_DECLARED_SYNCHRONIZED = 0x00020000,       // method (Dalvik only)
            ACC_CLASS_MASK = (ACC_PUBLIC | ACC_FINAL | ACC_INTERFACE | ACC_ABSTRACT | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM),
            ACC_INNER_CLASS_MASK = (ACC_CLASS_MASK | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC),
            ACC_FIELD_MASK = (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL | ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM),
            ACC_METHOD_MASK = (ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE | ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC | ACC_CONSTRUCTOR | ACC_DECLARED_SYNCHRONIZED);

    public static int getLength(){
        return 4 * 8;
    }

    public int getClassIdxValue()
    {
        return FileUtils.byte2Int(classIdx);
    }

    public String getClassIdxString()
    {
        return DexParser.getInstance().getTypeString(getClassIdxValue());
    }

    public int getAccessFlagsValue()
    {
        return FileUtils.byte2Int(accessFlags);
    }

    public String getSuperclassIdxString()
    {
        int superClassIdx = FileUtils.byte2Int(superclassIdx);
        return DexParser.getInstance().getTypeString(superClassIdx);
    }

    public int getInterfacesOffValue()
    {
        return FileUtils.byte2Int(interfacesOff);
    }

    public String getSourceFileIdxString()
    {
        int sourceIdx = FileUtils.byte2Int(sourceFileIdx);
        return DexParser.getInstance().getDataString(sourceIdx);
    }

    public int getAnnotationsOffValue()
    {
        return FileUtils.byte2Int(annotationsOff);
    }

    public int getClassDataOffValue()
    {
        return FileUtils.byte2Int(classDataOff);
    }

    public int getStaticValueOffValue()
    {
        return FileUtils.byte2Int(staticValueOff);
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Class: ").append(getClassIdxString()).append("\n");
        builder.append("SuperClass: ").append(getSuperclassIdxString()).append("\n");
        builder.append("SourceFile: ").append(getSourceFileIdxString()).append("\n");
        return builder.toString();
    }
}
