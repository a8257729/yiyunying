package com.ztesoft.mobile.common.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public class ObjectHelper {
    /*��Ҫ������object*/
    private Object obj;
    /*��Ҫ������class*/
    private Class cls;
    private static ClassLoader defaultClassLoader;
    /*��ʼ������*/
    public ObjectHelper(Object obj) {
        this.obj = obj;
        cls = obj.getClass();
    }


    public Object getObject() {
        return obj;
    }

    /**
     * ��ȡobject��ָ�����ֶ�
     * @param fieldName String
     * @return Field
     */
    public Field getField(String fieldName) {
        Field[] fieldArr = cls.getDeclaredFields();
        Field fld = null;
        for (int i = 0; i < fieldArr.length; i++) {

            if (fieldArr[i].getName().equals(fieldName)) {
                if (!fieldArr[i].isAccessible()) {
                    //�������ķ�����������Ϊ�ɷ���
                    fieldArr[i].setAccessible(true);
                }

                fld = fieldArr[i];
            }

        }

        return fld;

    }

    /**
     * ��ȡobject��ָ���ķ���������Լ���ɷ�����+��������
     * @param methodName String
     * @param paramCls Class[]
     * @return Method
     * @throws SecurityException
     * @throws NoSuchMethodException
     */

    public Method getMethod(String methodName, Class[] paramCls) throws
            SecurityException, NoSuchMethodException {
        Method mtd = null;
        mtd = cls.getMethod(methodName, paramCls);

        return mtd;
    }

    /**
     * ��ȡ�����÷����������ɷ������ƣ�������������+����ֵ���鹹��
     * @param methodName String
     * @param paramClsArr Class[]
     * @param paramVal Object[]
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */

    public Object callMethod(String methodName, Class[] paramClsArr,
                             Object[] paramVal) throws
            NoSuchMethodException,
            SecurityException, InvocationTargetException,
            IllegalArgumentException, IllegalAccessException {
        Object object = null;
        Method mtd = getMethod(methodName, paramClsArr);
        if (ValidateHelper.validateNotNull(mtd)) {
            object = mtd.invoke(obj, paramVal);

        }
        return object;
    }

    /**
     * Loads a class
     *
     * @param className - the class to load
     * @return The loaded class
     * @throws ClassNotFoundException If the class cannot be found (duh!)
     */
    public static Class classForName(String className) throws
            ClassNotFoundException {
        Class clazz = null;
        try {
            clazz = getClassLoader().loadClass(className);
        } catch (Exception e) {
            // Ignore.  Failsafe below.
        }
        if (clazz == null) {
            clazz = Class.forName(className);
        }
        return clazz;
    }

    public static ClassLoader getClassLoader() {
        if (defaultClassLoader != null) {
            return defaultClassLoader;
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }

    /**
     * ��ʼ������ʵ�����ɾ�̬�ⲿ����
     * @param className String
     * @return Object
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object newInstance(String className) throws
            ClassNotFoundException, IllegalAccessException,
            InstantiationException {

        return Class.forName(className).newInstance();

    }

    /**
     *  ʵ������������
     * @param vec Object
     * @param cls Class
     * @return Object
     */
    public static Object toArray(Object vec, Class cls) {
        // if null, return
        if (!ValidateHelper.validateNotNull(vec)) {
            return vec;
        }

        if (!vec.getClass().isArray()) {
            return vec;
        }

        // get array length and create Object output array

        int length = Array.getLength(vec);

        Object newvec[] = (Object[]) Array.newInstance(cls, length);
        // wrap and copy elements
        for (int i = 0; i < length; i++) {
            newvec[i] = Array.get(vec, i);
        }
        return newvec;
    }

    /**
     * Returns the default classloader (may be null).
     *
     * @return The default classloader
     */
    public static ClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    /**
     * Sets the default classloader
     *
     * @param defaultClassLoader - the new default ClassLoader
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
       ObjectHelper.defaultClassLoader = defaultClassLoader;
    }

}
