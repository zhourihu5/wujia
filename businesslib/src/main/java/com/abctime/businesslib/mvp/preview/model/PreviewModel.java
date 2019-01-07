//package com.abctime.businesslib.mvp.preview.model;
//
//import com.abctime.businesslib.data.ApiResponse;
//import com.abctime.businesslib.data.CommonApiService;
//import com.abctime.businesslib.data.DataManager;
//import com.abctime.businesslib.mvp.preview.data.BookInfo;
//import com.abctime.businesslib.mvp.preview.data.TopicData;
//import com.abctime.lib_common.data.network.HttpHelper;
//import com.abctime.lib_common.data.network.RxUtil;
//
//
//import java.util.ArrayList;
//
//import javax.inject.Inject;
//
//import io.reactivex.Flowable;
//
///**
// * Created by yseerd on 2018/6/22.
// */
//
//public class PreviewModel{
//
//    HttpHelper httpHelper;
//
//    @Inject
//    public PreviewModel(HttpHelper httpHelper) {
//        this.httpHelper = httpHelper;
//    }
//
//
//    public  Flowable<ApiResponse<BookInfo>> getBookInfo(int bookid, int type) {
//        return httpHelper.create(CommonApiService.class).getBookInfo(bookid,Integer.parseInt(DataManager.getMemberid()),type).compose(RxUtil.<ApiResponse<BookInfo>>rxSchedulerHelper());
//    }
//
//    public Flowable<ApiResponse<ArrayList<TopicData>>> getTopic(int book_id) {
//        return httpHelper.create(CommonApiService.class).getTopicData(book_id,Integer.parseInt(DataManager.getMemberid())).compose(RxUtil.<ApiResponse<ArrayList<TopicData>>>rxSchedulerHelper());
//    }
//
//}
