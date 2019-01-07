package com.abctime.businesslib.mvp.book;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/8/30 上午10:57
 */

public class BookQuery {

    private static BookQuery mInstance;
    private boolean isReady;
    private Map<String, Map<String, List<BookData>>> mBookMap;

    public static BookQuery getInstance() {
        if (mInstance == null)
            mInstance = new BookQuery();
        return mInstance;
    }

    private BookQuery() {
    }

    public void configBook(Map<String, Map<String, List<BookData>>> bookMap) {
        if (bookMap == null)
            return;
        isReady = true;
        mBookMap = bookMap;
    }

    @Nullable
    public Map<String, Map<String, List<BookData>>> getBookMap() {
        return mBookMap;
    }

    public boolean isReady() {
        return isReady;
    }

    public BookData queryNextBook(String screenId, String catId, String bookId) {
        if (isReady) {
            Map<String, List<BookData>> screenListMap = mBookMap.get(screenId);
            if (screenListMap != null) {
                List<BookData> bookDatas = screenListMap.get(catId);
                if (bookDatas != null) {
                    int currentPos = -1;
                    boolean query = false;
                    for (BookData bookData : bookDatas) {
                        currentPos++;
                        if (TextUtils.equals(bookData.id, bookId)) {
                            query = true;
                            break;
                        }
                    }
                    if (query) {
                        int nextPos = currentPos + 1;
                        if (nextPos < bookDatas.size() && null != bookDatas.get(nextPos)) {
                            return bookDatas.get(nextPos);
                        } else {
                            return bookDatas.get(0);
                        }
                    }
                }
            }

        }
        return null;
    }

    @Nullable
    public BookInfo queryBookInfo(String screenId, String catId, String bookId) {
        if (isReady) {
            BookInfo info = new BookInfo();
            Map<String, List<BookData>> screenListMap = mBookMap.get(screenId);
            boolean isFirstScreen = false;
            try {
                String[] screenIds = new String[mBookMap.keySet().size()];
                mBookMap.keySet().toArray(screenIds);
                isFirstScreen = TextUtils.equals(screenId, screenIds[0]);
            } catch (Exception e) {
            }
            if (screenListMap != null) {
                List<BookData> bookDatas = screenListMap.get(catId);
                if (bookDatas != null) {
                    int currentPos = -1;
                    for (BookData bookData : bookDatas) {
                        currentPos++;
                        if (TextUtils.equals(bookData.id, bookId)) {
                            info.book = bookData;
                            info.posInLevel = currentPos;
                            info.isFirstScreen = isFirstScreen;
                            return info;
                        }
                    }
                }
            }
        }
        return null;
    }

    private BookData queryBook(String screenId, String catId, String bookId) {
        if (isReady) {

        }
        return null;
    }

    public static class BookInfo {
        public BookData book;
        public int posInLevel;
        public boolean isFirstScreen;
    }
}
