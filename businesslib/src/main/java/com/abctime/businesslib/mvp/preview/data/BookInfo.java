//package com.abctime.businesslib.mvp.preview.data;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * Created by yseerd on 2018/6/22.
// */
//
//public class BookInfo implements Serializable {
//
//
//    /**
//     * id : 264
//     * cid : 10
//     * book_name : A Seed Grows
//     * words_num : 133
//     * pic : http://file.abctime.com/book_264.jpg
//     * zipfile : http://file.abctime.com/booknzf_58f0ec765f4506db34968e5e183c11da.zip
//     * orientation : 2
//     * bookpages : [{"orientation":"3","page_content":"A small seed falls to the ground and becomes buried in the soft soil.","word":""},{"orientation":"3","page_content":"The seed is buried in the soft soil, and the rain falls.","word":""},{"orientation":"3","page_content":"The rain soaks into the soil that holds the small seed.","word":""},{"orientation":"3","page_content":"The small seed soaks up water from the rain.","word":""},{"orientation":"3","page_content":"The water helps the seed, and roots grow down deep into the soil.","word":""},{"orientation":"3","page_content":"The leaves and a stem grow up through the ground.\nThe leaves gather in the sunshine.","word":""},{"orientation":"3","page_content":"The plant uses sunshine to make food.\nThe rain and the soil help the small plant.","word":""},{"orientation":"3","page_content":"The plant grows taller,\nand more leaves grow on the stem.\nThe roots grow longer.\nThey go deep in the ground.","word":""},{"orientation":"3","page_content":"The big plant grows flowers.\nThe flowers make seeds.","word":""},{"orientation":"3","page_content":"A new seed falls into the soft soil.\nWhat will happen next?","word":""}]
//     * quiz : [{"import_title":"What happens after the seed soaks up water?","tdata":[{"title":"Roots grow deep into the soil.","is_answer":true},{"title":"The seed grows flowers.","is_answer":false},{"title":"The seed grows more seeds.","is_answer":false}]},{"import_title":"How are roots different from flowers?","tdata":[{"title":"Roots grow under the ground.","is_answer":true},{"title":"Roots are part of a plant.","is_answer":false},{"title":"Roots use water to grow.","is_answer":false}]},{"import_title":"What do plants need to grow?","tdata":[{"title":"water","is_answer":false},{"title":"sunshine","is_answer":false},{"title":"both water and sunshine","is_answer":true}]},{"import_title":"What will happen to the new seed that falls to the ground?","tdata":[{"title":"The bug will eat it.","is_answer":false},{"title":"The seed will grow into a new plant.","is_answer":true},{"title":"both of the answers","is_answer":false}]},{"import_title":"Which part of the plant grows seeds?","tdata":[{"title":"flowers","is_answer":true},{"title":"leaves","is_answer":false},{"title":"stems","is_answer":false}]}]
//     * create_time : 2016
//     * update_time : 1509678087
//     * is_delete : 0
//     * is_show : 1
//     * words_list :
//     * grade : 0
//     * difficulty : 0
//     * new_difficulty : 0
//     * author : Julie Harding
//     * illustrator : Fred Volke
//     * guest_readable : 0
//     * words_card : 0
//     * page_num : 10
//     * visual_style :
//     * photo_credit :
//     * qrcode_num : 0
//     * is_new : 0
//     * cat_name : G
//     * pid : 1
//     * read_time : 10
//     * scene : ["Others"]
//     * sid : [0]
//     * topic : 0
//     * attr_list : [{"book_id":264,"page":1,"content":"A small seed falls to the ground and becomes buried in the soft soil.","pic_path":"https://qnfile.abctime.com/264_p1.png","audio_path":"https://qnfile.abctime.com/264_p1.mp3"},{"book_id":264,"page":2,"content":"The seed is buried in the soft soil, and the rain falls.","pic_path":"https://qnfile.abctime.com/264_p2.png","audio_path":"https://qnfile.abctime.com/264_p2.mp3"},{"book_id":264,"page":3,"content":"The rain soaks into the soil that holds the small seed.","pic_path":"https://qnfile.abctime.com/264_p3.png","audio_path":"https://qnfile.abctime.com/264_p3.mp3"},{"book_id":264,"page":4,"content":"The small seed soaks up water from the rain.","pic_path":"https://qnfile.abctime.com/264_p4.png","audio_path":"https://qnfile.abctime.com/264_p4.mp3"},{"book_id":264,"page":5,"content":"The water helps the seed, and roots grow down deep into the soil.","pic_path":"https://qnfile.abctime.com/264_p5.png","audio_path":"https://qnfile.abctime.com/264_p5.mp3"},{"book_id":264,"page":6,"content":"The leaves and a stem grow up through the ground.\nThe leaves gather in the sunshine.","pic_path":"https://qnfile.abctime.com/264_p6.png","audio_path":"https://qnfile.abctime.com/264_p6.mp3"},{"book_id":264,"page":7,"content":"The plant uses sunshine to make food.\nThe rain and the soil help the small plant.","pic_path":"https://qnfile.abctime.com/264_p7.png","audio_path":"https://qnfile.abctime.com/264_p7.mp3"},{"book_id":264,"page":8,"content":"The plant grows taller,\nand more leaves grow on the stem.\nThe roots grow longer.\nThey go deep in the ground.","pic_path":"https://qnfile.abctime.com/264_p8.png","audio_path":"https://qnfile.abctime.com/264_p8.mp3"},{"book_id":264,"page":9,"content":"The big plant grows flowers.\nThe flowers make seeds.","pic_path":"https://qnfile.abctime.com/264_p9.png","audio_path":"https://qnfile.abctime.com/264_p9.mp3"},{"book_id":264,"page":10,"content":"A new seed falls into the soft soil.\nWhat will happen next?","pic_path":"https://qnfile.abctime.com/264_p10.png","audio_path":"https://qnfile.abctime.com/264_p10.mp3"}]
//     * isRecordingScore : 0
//     * read_num : 66
//     * read_score_max : 0
//     * rank_num : 0
//     * readStars : 0
//     * previewStars : 0
//     * preview_words : 0
//     * quizStars : 0
//     * pkStars : 0
//     * goldStars : 0
//     * book_download : {"allow":0,"notice":0}
//     */
//
//    public int id;
//    public int cid;
//    public String book_name;
//    public int words_num;
//    public String pic;
//    public String zipfile;
//    public int orientation;
//    public int create_time;
//    public int update_time;
//    public int is_delete;
//    public int is_show;
//    //    public List<WordsListBean> words_list;
//    public int grade;
//    public int difficulty;
//    public int new_difficulty;
//    public String author;
//    public String illustrator;
//    public int guest_readable;
//    public int words_card;
//    public int page_num;
//    public String visual_style;
//    public String photo_credit;
//    public int qrcode_num;
//    public int is_new;
//    public String cat_name;
//    public int pid;
//    public int read_time;
//    public int topic;
//    public int isRecordingScore;
//    public int read_num;
//    public int read_score_max;
//    public String rank_num;
//    public int preview_words;
//    public int readStars;
//    public int quizStars;
//    public int pkStars;
//    public int goldStars;
//    public int previewStars;
//    public BookDownloadBean book_download;
//    public List<BookpagesBean> bookpages;
//    public List<QuizBean> quiz;
//    public List<String> scene;
//    public List<Integer> sid;
//    public boolean hasUnZip =false;
////    public List<AttrListBean> attr_list;
//
//
//    public static class BookDownloadBean implements Serializable {
//        /**
//         * allow : 0
//         * notice : 0
//         */
//
//        public int allow;
//        public int notice;
//
//    }
//
//    public static class BookpagesBean implements Serializable {
//        /**
//         * orientation : 3
//         * page_content : A small seed falls to the ground and becomes buried in the soft soil.
//         * word :
//         */
//
//        public String orientation;
//        public String page_content;
//        public String word;
//
//    }
//
//    public static class QuizBean implements Serializable {
//        /**
//         * import_title : What happens after the seed soaks up water?
//         * tdata : [{"title":"Roots grow deep into the soil.","is_answer":true},{"title":"The seed grows flowers.","is_answer":false},{"title":"The seed grows more seeds.","is_answer":false}]
//         */
//
//        public String import_title;
//
//        public String translate_title;
//
//        public boolean hasSeleted = false;
//
//        public boolean isRight; // 在结果页用到 回答是否正确
//
//        public List<TdataBean>  tdata;
//
//
//        public static class TdataBean implements Serializable {
//            /**
//             * title : Roots grow deep into the soil.
//             * is_answer : true
//             */
//
//            public String title;
//            public boolean is_answer;
//            public int state = 0; // 选中状态 0-normal 1-true 2-false
//        }
//    }
//
//    public static class AttrListBean implements Serializable {
//        /**
//         * book_id : 264
//         * page : 1
//         * content : A small seed falls to the ground and becomes buried in the soft soil.
//         * pic_path : https://qnfile.abctime.com/264_p1.png
//         * audio_path : https://qnfile.abctime.com/264_p1.mp3
//         */
//
//        public int book_id;
//        public int page;
//        public String content;
//        public String pic_path;
//        public String audio_path;
//
//
//    }
//
//    public static class WordsListBean implements Serializable {
//        /**
//         * id : 350
//         * name : dog
//         * pic_path : https://qnwords.abctime.com/dog_1507716291.png
//         * audio_path : https://qnwords.abctime.com/dog.mp3
//         * type : 0
//         * book_id : 1284
//         */
//
//        public int id;
//        public String name;
//        public String pic_path;
//        public String audio_path;
//        public int type;
//        public int book_id;
//
//
//    }
//
//}
