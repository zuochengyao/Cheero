package com.icheero.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefInvoke
{
    public static Object invokeStaticMethod(String class_name, String method_name, Class[] pareType, Object[] paramValues)
    {
        return invokeMethod(class_name, method_name, null, pareType, paramValues);
    }

    public static Object invokeMethod(String class_name, String method_name, Object obj, Class[] pareType, Object[] paramValues)
    {
        try
        {
            Class<?> obj_class = Class.forName(class_name);
            Method method = obj_class.getMethod(method_name, pareType);
            return method.invoke(obj, paramValues);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getStaticFieldOjbect(String class_name, String filedName)
    {
        return getFieldObject(class_name, null, filedName);
    }

    public static Object getClassInstance(String class_name)
    {
        try
        {
            Class<?> obj_class = Class.forName(class_name);

            return obj_class.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(String class_name, String filedName)
    {
        try
        {
            Class<?> obj_class = Class.forName(class_name);
            Object obj = obj_class.newInstance();
            Field field = obj_class.getField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldObject(String class_name, Object obj, String filedName)
    {
        try
        {
            Class<?> obj_class = Class.forName(class_name);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldObject(String class_name, String filedName, Object obj, Object filedVaule)
    {
        try
        {
            Class<?> obj_class = Class.forName(class_name);
            Field field = obj_class.getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(obj, filedVaule);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setStaticObject(String class_name, String filedName, Object filedVaule)
    {
        setFieldObject(class_name, filedName, null, filedVaule);
    }
}
