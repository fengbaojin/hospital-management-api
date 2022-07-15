package com.example.demo.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author yuanzhou on 2018/1/3.
 */
@Component
public class BeanUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    public static ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
    private static BeanUtilsBean beanUtilsBean;

    public BeanUtils() {
    }

    public static <T> void setValueIgnoreEmpty(T value, Consumer<T> consumer) {
        if(isNotEmpty(value)) {
            consumer.accept(value);
        }

    }

    public static <T> void setValueElseEmpty(T value, T defaultValue, Consumer<T> consumer) {
        if(isNotEmpty(value)) {
            consumer.accept(value);
        } else {
            consumer.accept(defaultValue);
        }

    }

    public static <T> T getValueElseDefault(T value, T defaultValue) {
        return isNotEmpty(value)?value:defaultValue;
    }

    public static boolean isEmpty(Object o) {
        if(o == null) {
            return true;
        } else {
            if(o instanceof String) {
                if(((String)o).trim().length() == 0) {
                    return true;
                }
            } else if(o instanceof Collection) {
                if(((Collection)o).isEmpty()) {
                    return true;
                }
            } else if(o.getClass().isArray()) {
                if(((Object[])((Object[])o)).length == 0) {
                    return true;
                }
            } else if(o instanceof Map) {
                if(((Map)o).isEmpty()) {
                    return true;
                }
            } else if(o instanceof Long) {
                Long sEmpty = Long.valueOf(0L);
                if(sEmpty.equals(o)) {
                    return true;
                }
            } else if(o instanceof Short) {
                Short sEmpty1 = Short.valueOf((short)0);
                if(sEmpty1.equals(o)) {
                    return true;
                }
            } else if(o instanceof Integer) {
                Integer sEmpty2 = Integer.valueOf(0);
                if(sEmpty2.equals(o)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotEmpty(Long o) {
        return !isEmpty(o);
    }

    public static boolean isNumber(Object o) {
        if(o == null) {
            return false;
        } else if(o instanceof Number) {
            return true;
        } else if(o instanceof String) {
            try {
                Double.parseDouble((String)o);
                return true;
            } catch (NumberFormatException var2) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Object populateEntity(Map<String, Object> map, Object entity) throws IllegalAccessException, InvocationTargetException {
        beanUtilsBean.populate(entity, map);
        return entity;
    }

    public static boolean validClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException var2) {
            return false;
        }
    }

    public static boolean isInherit(Class<?> cls, Class parentClass) {
        return parentClass.isAssignableFrom(cls);
    }

    public static Object cloneBean(Object bean) {
        try {
            return beanUtilsBean.cloneBean(bean);
        } catch (Exception var2) {
            handleReflectionException(var2);
            return null;
        }
    }

    public static <T> T copyProperties(Class<T> destClass, Object orig) {
        Object target = null;

        try {
            target = destClass.newInstance();
            copyProperties(target, orig);
            return (T) target;
        } catch (Exception var4) {
            handleReflectionException(var4);
            return null;
        }
    }

    public static void copyProperties(Object dest, Object orig) {
        try {
            beanUtilsBean.copyProperties(dest, orig);
        } catch (Exception var3) {
            handleReflectionException(var3);
        }

    }

    public static void copyProperty(Object bean, String name, Object value) {
        try {
            beanUtilsBean.copyProperty(bean, name, value);
        } catch (Exception var4) {
            handleReflectionException(var4);
        }

    }

    public static Map describe(Object bean) {
        try {
            return beanUtilsBean.describe(bean);
        } catch (Exception var2) {
            handleReflectionException(var2);
            return null;
        }
    }

    public static String[] getArrayProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getArrayProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static ConvertUtilsBean getConvertUtils() {
        return beanUtilsBean.getConvertUtils();
    }

    public static String getIndexedProperty(Object bean, String name, int index) {
        try {
            return beanUtilsBean.getIndexedProperty(bean, name, index);
        } catch (Exception var4) {
            handleReflectionException(var4);
            return null;
        }
    }

    public static String getIndexedProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getIndexedProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static String getMappedProperty(Object bean, String name, String key) {
        try {
            return beanUtilsBean.getMappedProperty(bean, name, key);
        } catch (Exception var4) {
            handleReflectionException(var4);
            return null;
        }
    }

    public static String getMappedProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getMappedProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static String getNestedProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getNestedProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static String getProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static PropertyUtilsBean getPropertyUtils() {
        try {
            return beanUtilsBean.getPropertyUtils();
        } catch (Exception var1) {
            handleReflectionException(var1);
            return null;
        }
    }

    public static String getSimpleProperty(Object bean, String name) {
        try {
            return beanUtilsBean.getSimpleProperty(bean, name);
        } catch (Exception var3) {
            handleReflectionException(var3);
            return null;
        }
    }

    public static void populate(Object bean, Map<String, Object> properties) {
        try {
            beanUtilsBean.populate(bean, properties);
        } catch (Exception var3) {
            handleReflectionException(var3);
        }

    }

    public static void setProperty(Object bean, String name, Object value) {
        try {
            beanUtilsBean.setProperty(bean, name, value);
        } catch (Exception var4) {
            handleReflectionException(var4);
        }

    }

    private static void handleReflectionException(Exception ex) {
        if(ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if(ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        } else {
            if(ex instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException)ex);
            }

            if(ex instanceof RuntimeException) {
                throw (RuntimeException)ex;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }

    private static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    private static void rethrowRuntimeException(Throwable ex) {
        if(ex instanceof RuntimeException) {
            throw (RuntimeException)ex;
        } else if(ex instanceof Error) {
            throw (Error)ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    static {
        beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
        convertUtilsBean.register(new DateConverter(), Date.class);
        convertUtilsBean.register(new LongConverter((Object)null), Long.class);
    }

    public static <T> T cloneTo(T src) throws RuntimeException {
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        T dist = null;
        try {
            out = new ObjectOutputStream(memoryBuffer);
            out.writeObject(src);
            out.flush();
            in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
            dist = (T) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            if (in != null)
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return dist;
    }
}

