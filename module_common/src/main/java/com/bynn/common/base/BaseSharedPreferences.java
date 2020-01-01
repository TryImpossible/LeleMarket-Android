package com.bynn.common.base;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.UnknownFormatConversionException;

public abstract class BaseSharedPreferences {
    public abstract SharedPreferences getSharedPreference(Context context);

    /**
     * 根据数据类型调用相应的保存方法
     *
     * @param context
     * @param key
     * @param object
     * @return
     */
    public String put(Context context, String key, Object object) {
        if (object == null) {
            return key;
        }

        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return null;
        }
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            throw new UnknownFormatConversionException("cannot save!");
        }
        //SharedPreferencesCompat.apply(editor);
        editor.commit();
        return key;
    }

    /**
     * 根据默认值的具体类型，调用相应的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return null;
        }
        if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Set<?>) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        }
        return null;
    }

    /**
     * 根据key，清除对应的值
     *
     * @param context
     * @param key
     */
    public void remove(Context context, String key) {
        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public void clear(Context context) {
        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询key否存在
     *
     * @param context
     * @param key
     * @return
     */
    public boolean contains(Context context, String key) {
        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return false;
        }
        return sp.contains(key);
    }

    /**
     * 返回所有的健值对
     *
     * @param context
     * @return
     */
    public Map<String, ?> getAll(Context context) {
        SharedPreferences sp = getSharedPreference(context);
        if (sp == null) {
            return null;
        }
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    public static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 通过反射查找apply方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
