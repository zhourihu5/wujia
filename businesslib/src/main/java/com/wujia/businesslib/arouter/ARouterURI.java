package com.wujia.businesslib.arouter;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/11 下午5:53
 */

public class ARouterURI {

    public interface LoginURI {
        String GROUP = "/login";
        String LOGIN = GROUP + "/loginactivity";
    }

    public interface LibraryURI {
        String GROUP = "/library";
        String MAIN = GROUP + "/entrance";
        String MAIN_ACTIVITY = GROUP + "/main";
        String WORD_CARD = GROUP + "/wordcard";
        String WORD_CARD_LIST = GROUP + "/wordcardlist";
        String BOOK_ACTIVE = GROUP + "/book_active";
        String BOOK_PRE = GROUP + "/book_pre";
        String STUDY_REPORT = GROUP + "/study_report";
        String READ_FOLLOW = GROUP + "/readfollow";
        String READ_STATE = GROUP + "/readstate";
        String BOOK_RANKING = GROUP + "/readranking";
        String BOOK_LIST = GROUP + "/booklist";
        String BOOKSHELF = GROUP + "/bookshelf";
    }

    public interface FmURI {
        String GROUP = "/fm";
        String MAIN = GROUP + "/entrance";
        String MAIN_ACTIVITY = GROUP + "/main_activity";
        String SERVICE = GROUP + "/service";
    }

    public interface MyStoryURI {
        String GROUP = "/mystory";
        String MAIN = GROUP + "/entrance";
    }

    public interface PaymentURI {
        String GROUP = "/payment";
        String MAIN = GROUP + "/main";
        String ORDER = GROUP + "/order";
    }

    public interface UserCenterURI {
        String GROUP = "/usercenter";
        String MAIN = GROUP + "/main";
        String CHILD = GROUP + "/child";
        String REPORT = GROUP + "/report";
        String ACCOUNT = GROUP + "/account";
        String ABOUT = GROUP + "/about";
        String HISTORY = GROUP + "/history";
        String COUPON = GROUP + "/coupon";
    }

    public interface QrcodeURI {
        String GROUP = "/qrcode";
        String SCAN = GROUP + "/qrscan";

    }

    public interface QuizURI {
        String GROUP = "/quiz";
        String QUIZ = GROUP + "/quizactivity";
        String RESULT = GROUP + "/result";

    }

    public interface WechatURI {
        String GROUP = "/wechat";
        String MAIN = GROUP + "/main";

    }

    public interface AppURI {
        String GROUP = "/home";
        String MAIN = GROUP + "/main";

    }

    public interface webURI {
        String GROUP = "/webview";

        String MAIN = GROUP + "/commonwebviewactivity";
    }

    //学习计划
    public interface PlanURI {
        String GROUP = "/plan";

        String LIST = GROUP + "/list";
        String INFO = GROUP + "/info";
        String BOSS = GROUP + "/boss";
        String WEB = GROUP + "/web";
        String BOSS_RESULT = GROUP + "/boss_result";
        String CHANGE = GROUP + "/change";
    }
}
