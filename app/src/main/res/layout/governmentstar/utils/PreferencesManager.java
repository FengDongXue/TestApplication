package com.lanwei.governmentstar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/28.
 */

public class PreferencesManager {

    private final String tag = PreferencesManager.class.getSimpleName();
    private Context mContext;
    private SharedPreferences preferences;
    // 默认系统SharedPreferences KEY值名称
    private static String shareName = "ACCOUNT_BEAN";
    public static final String THEME = "Theme";
    public static final String LANG = "Lang";
    private static PreferencesManager instance;

    /**
     * 构造方法
     * @param context
     */
    private PreferencesManager(Context context) {
        this(context, shareName);
    }

    /**
     * 构造方法
     * @param context
     * @param shareName
     */
    private PreferencesManager(Context context, String shareName) {
        mContext = context;
        preferences = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
    }

    /****
     * 得到单例模式的PreferencesManager对象<br>
     * @param context
     * @return
     */
    public static PreferencesManager getInstance(Context context) {
        return getInstance(context, shareName);
    }

    /****
     * 得到单例模式的PreferencesManager对象<br>
     * @param context
     * @param shareName
     * @return
     */
    public static PreferencesManager getInstance(Context context, String shareName) {
        if (instance == null) {
            synchronized (PreferencesManager.class) {
                if (instance == null) {
                    instance = new PreferencesManager(context, shareName);
                }
            }
        }
        return instance;
    }

    public void put(String key, boolean value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public void put(String key, String value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putString(key, value);
            edit.commit();
        }
    }

    public void put(String key, int value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putInt(key, value);
            edit.commit();
        }
    }

    public void put(String key, float value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putFloat(key, value);
            edit.commit();
        }
    }

    public void put(String key, long value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putLong(key, value);
            edit.commit();
        }
    }

    public void put(String key, Set<String> value) {
        SharedPreferences.Editor edit = preferences.edit();
        if (edit != null) {
            edit.putStringSet(key, value);
            edit.commit();
        }
    }

    /*****
     * 保存复杂对象<br>
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            // 然后通过将字对象进行64转码，写入key值为key的sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor edit = preferences.edit();
            if (edit != null) {
                edit.putString(key, objectVal);
                edit.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String get(String key) {
        return preferences.getString(key, "");
    }

    public String get(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public int get(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public long get(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public Set<String> get(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }

    /*****
     * 获取对象<br>
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        if (preferences.contains(key)) {
            String objectVal = preferences.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            // 一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public int getTheme(int defThemeId) {
        return instance.get(THEME, defThemeId);
    }

    public void setTheme(int themeId) {
        instance.put(THEME, themeId);
    }

    public String getLanguage(String defLang) {
        return instance.get(LANG, defLang);
    }

    public void setLang(String Language) {
        instance.put(LANG, Language);
    }

    /*****
     * 清除SharedPreferences全部保存数据
     */
    public void clearAll() {
        preferences.edit().clear().commit();
    }
}
