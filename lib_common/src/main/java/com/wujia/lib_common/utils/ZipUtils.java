package com.wujia.lib_common.utils;


import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by xmren on 2018/5/17.
 */

public final class ZipUtils {

    private ZipUtils() {
        throw new AssertionError();
    }

    /**
     * gzip compress string
     *
     * @param str string
     * @return gzipString
     */
    public static String gzipCompress2Base64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = null;
        GZIPOutputStream gzip = null;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            String gzipString = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP);
            return gzipString;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.flush();
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * gzip uncompress string
     *
     * @param str gzipString
     * @return string
     */
    public static String uncompress(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            // ToString () uses platform default encoding, and can explicitly specify such as toString ("GBK").
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
