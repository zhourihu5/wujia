package com.wujia.lib_common.utils;


import android.content.Context;
import android.os.Environment;


import com.wujia.lib_common.utils.disklrucache.DiskLruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class DiskLruCacheManager {
    private static final long REASONABLE_DISK_SIZE = 1024 * 1024; // 1 MB
    private static DiskLruCache mDiskLruCache = null;
    private DiskLruCache.Editor mEditor = null;
    private DiskLruCache.Snapshot mSnapshot = null;

    public DiskLruCacheManager(Context context, String uniqueName) {
        try {
            if (mDiskLruCache != null) {
                mDiskLruCache.close();
                mDiskLruCache = null;
            }
            File cacheFile = getCacheFile(context, uniqueName);
            mDiskLruCache = DiskLruCache.open(cacheFile, 1, 1, REASONABLE_DISK_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getCacheFile(Context context, String uniqueName) {
        String cachePath;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    private DiskLruCache.Editor edit(String key) throws IOException {
        if (mDiskLruCache != null) {
            mEditor = mDiskLruCache.edit(key);
        }
        return mEditor;
    }

    private DiskLruCache.Snapshot snapshot(String key) {
        if (mDiskLruCache != null) {
            try {
                mSnapshot = mDiskLruCache.get(key);
                System.out.println(mDiskLruCache);
                System.out.println(mSnapshot);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return mSnapshot;
    }

    public void put(String key, String value) {
        DiskLruCache.Editor editor = null;
        BufferedWriter writer = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            OutputStream os = editor.newOutputStream(0);
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (editor != null)
                    editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getString(String key) {
        InputStream inputStream = getCacheInputStream(key);
        if (inputStream == null) {
            return null;
        }
        try {
            return inputStream2String(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    public void put(String key, JSONObject value) {
        put(key, value.toString());
    }

    //取得 json 字符串再转为 Json对象
    public JSONObject getJsonObject(String key) {
        String json = getString(key);
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void put(String key, JSONArray array) {
        put(key, array.toString());
    }

    public JSONArray getJsonArray(String key) {
        try {
            return new JSONArray(getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void put(String key, byte[] bytes) {
        OutputStream out = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            out = editor.newOutputStream(0);
            out.write(bytes);
            out.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public byte[] getBytes(String key) {
        byte[] bytes = null;
        InputStream inputStream = getCacheInputStream(key);
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[256];
        int len = 0;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public void put(String key, Serializable object) {
        ObjectOutputStream oos = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = edit(key);
            if (editor == null) {
                return;
            }
            oos = new ObjectOutputStream(editor.newOutputStream(0));
            oos.writeObject(object);
            oos.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (editor != null)
                    editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public <T> T getSerializable(String key) {
        T object = null;
        ObjectInputStream ois = null;
        InputStream in = getCacheInputStream(key);
        if (in == null) {
            return null;
        }
        try {
            ois = new ObjectInputStream(in);
            object = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }


    private String inputStream2String(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private InputStream getCacheInputStream(String key) {
        InputStream in;
        DiskLruCache.Snapshot snapshot = snapshot(key);
        if (snapshot == null) {
            return null;
        }
        in = snapshot.getInputStream(0);
        return in;
    }

    public void flush() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
