package per.zyq.pw;

import org.zyq.http.DefaultHttpUtils;
import org.zyq.http.HttpUtils;
import org.zyq.http.entity.Config;

/**
 * Created by Administrator on 2016/4/23.
 */
public class Start {
    public static void main(String[] args) {
        HttpUtils _ = new DefaultHttpUtils(new Config().setRepeatCount(5));
//        new Api(_).viewHome();
        new Api(_).getDowland("https://avmo.pw/cn/movie/5gzu");
//        new Api(_).test();
    }
}
