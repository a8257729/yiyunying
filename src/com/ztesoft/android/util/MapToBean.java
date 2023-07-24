package com.ztesoft.android.util;

import java.beans.BeanInfo;     
import java.beans.IntrospectionException;     
import java.beans.Introspector;     
import java.beans.PropertyDescriptor;     
import java.util.HashMap;     
import java.util.Map;     
import java.lang.reflect.InvocationTargetException;     
      
public class MapToBean {     
      
    public static void main(String[] args) throws Exception {     
        Map<Object, Object> map = new HashMap<Object, Object>();     
        map.put("name", "张三");     
        map.put("age", 30);     
        Person p = convertMap(Person.class, map);     
        System.out.println(p.getName() + ", " + p.getAge());     
    }     
      
    /**   
     * 将一个 Map 对象转化为一个 JavaBean   
     *   
     * @param type 要转化的类型   
     * @param map  包含属性值的 map   
     *   
     * @return 转化出来的 JavaBean 对象   
     *   
     * @throws IntrospectionException    如果分析类属性失败   
     * @throws IllegalAccessException    如果实例化 JavaBean 失败   
     * @throws InstantiationException    如果实例化 JavaBean 失败   
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败   
     */    
    private static <T> T convertMap(Class<T> type, Map<Object, Object> map)     
            throws IntrospectionException, IllegalAccessException,     
            InstantiationException, InvocationTargetException {     
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性     
        T t = type.newInstance();   // 创建 JavaBean 对象     
      
        // 给 JavaBean 对象的属性赋值     
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {     
            String propertyName = descriptor.getName();     
            if (map.containsKey(propertyName)) {     
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。     
                descriptor.getWriteMethod().invoke(t, map.get(propertyName));     
            }     
        }     
        return t;     
    }     
}     
      
class Person {     
      
    private String name;     
      
    private int age;     
      
    public String getName() {     
        return name;     
    }     
      
    public void setName(String name) {     
        this.name = name;     
    }     
      
    public int getAge() {     
        return age;     
    }     
      
    public void setAge(int age) {     
        this.age = age;     
    }     
}    
