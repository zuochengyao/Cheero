package com.icheero.reverse.manifest;

import java.util.List;

public class AttributeType {

	public static final int ATTR_NULL = 0;
	public static final int ATTR_REFERENCE 	= 1;
	public static final int ATTR_ATTRIBUTE 	= 2;
	public static final int ATTR_STRING 	= 3;
	public static final int ATTR_FLOAT 	= 4;
	public static final int ATTR_DIMENSION 	= 5;
	public static final int ATTR_FRACTION 	= 6;
	public static final int ATTR_FIRST_INT = 16;
	public static final int ATTR_HEX	= 17;
	public static final int ATTR_BOOLEAN	= 18;
	public static final int ATTR_FIRST_COLOR = 28;
	public static final int ATTR_RGB8	= 29;
	public static final int ATTR_ARGB4	= 30;
	public static final int ATTR_RGB4	= 31;
	public static final int ATTR_LAST_COLOR = 31;
	public static final int ATTR_LAST_INT = 31;

	public static final int
    COMPLEX_UNIT_PX			=0,
    COMPLEX_UNIT_DIP		=1,
    COMPLEX_UNIT_SP			=2,
    COMPLEX_UNIT_PT			=3,
    COMPLEX_UNIT_IN			=4,
    COMPLEX_UNIT_MM			=5,
	COMPLEX_UNIT_SHIFT		=0,
    COMPLEX_UNIT_MASK		=15,
    COMPLEX_UNIT_FRACTION	=0,
    COMPLEX_UNIT_FRACTION_PARENT=1,
    COMPLEX_RADIX_23p0		=0,
    COMPLEX_RADIX_16p7		=1,
    COMPLEX_RADIX_8p15		=2,
    COMPLEX_RADIX_0p23		=3,
    COMPLEX_RADIX_SHIFT		=4,
    COMPLEX_RADIX_MASK		=3,
    COMPLEX_MANTISSA_SHIFT	=8,
    COMPLEX_MANTISSA_MASK	=0xFFFFFF;
	
	/*public static String getAttributeData(AttributeData data){
		if(data.type == ATTR_STRING){
			return ParseChunkUtils.getStringContent(data.data);
		}else if(type == ATTR_BOOLEAN){
			return data==0 ? "false" : "true";
		}else{
			return ""+data;
		}
	}*/

	public static String getAttributeData(List<String> stringContent, int type, int data){
		if (type == ATTR_STRING) {
			return stringContent.get(data);
		}
		if (type == ATTR_ATTRIBUTE) {
			return String.format("?%s%08X",getPackage(data),data);
		}
		if (type == ATTR_REFERENCE) {
			return String.format("@%s%08X",getPackage(data),data);
		}
		if (type == ATTR_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type == ATTR_HEX) {
			return String.format("0x%08X",data);
		}
		if (type == ATTR_BOOLEAN) {
			return data!=0?"true":"false";
		}
		if (type == ATTR_DIMENSION) {
			return Float.toString(complexToFloat(data))+
				DIMENSION_UNITS[data & COMPLEX_UNIT_MASK];
		}
		if (type == ATTR_FRACTION) {
			return Float.toString(complexToFloat(data))+
				FRACTION_UNITS[data & COMPLEX_UNIT_MASK];
		}
		if (type >= ATTR_FIRST_COLOR && type <= ATTR_LAST_COLOR) {
			return String.format("#%08X",data);
		}
		if (type >= ATTR_FIRST_INT && type <= ATTR_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format("<0x%X, type 0x%02X>",data, type);
	}

	private static String getPackage(int id) {
		if (id>>>24==1) {
			return "android:";
		}
		return "";
	}

	public static float complexToFloat(int complex) {
		return (float)(complex & 0xFFFFFF00)*RADIX_MULTS[(complex>>4) & 3];
	}

	private static final float RADIX_MULTS[]={
		0.00390625F,3.051758E-005F,1.192093E-007F,4.656613E-010F
	};

	private static final String DIMENSION_UNITS[]={
		"px","dip","sp","pt","in","mm","",""
	};

	private static final String FRACTION_UNITS[]={
		"%","%p","","","","","",""
	};

	public static String getAttrType(int type){
		switch(type){
			case ATTR_NULL:
				return "ATTR_NULL";
			case ATTR_REFERENCE:
				return "ATTR_REFERENCE";
			case ATTR_ATTRIBUTE:
				return "ATTR_ATTRIBUTE";
			case ATTR_STRING:
				return "ATTR_STRING";
			case ATTR_FLOAT:
				return "ATTR_FLOAT";
			case ATTR_DIMENSION:
				return "ATTR_DIMENSION";
			case ATTR_FRACTION:
				return "ATTR_FRACTION";
			case ATTR_FIRST_INT:
				return "ATTR_FIRST_INT";
			case ATTR_HEX:
				return "ATTR_HEX";
			case ATTR_BOOLEAN:
				return "ATTR_BOOLEAN";
			case ATTR_FIRST_COLOR:
				return "ATTR_FIRST_COLOR";
			case ATTR_RGB8:
				return "ATTR_RGB8";
			case ATTR_ARGB4:
				return "ATTR_ARGB4";
			case ATTR_RGB4:
				return "ATTR_RGB4";
		}
		return "";
	}

}
