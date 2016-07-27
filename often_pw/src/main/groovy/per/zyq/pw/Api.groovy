package per.zyq.pw

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.zyq.http.HttpUtils

/**
 * Created by Administrator on 2016/4/23.
 */
class Api {
    private HttpUtils _;

    Api(HttpUtils _) {
        this._ = _;
    }

    void viewHome() {
        String home = _.get("https://avmo.pw/cn");
        Document document = Jsoup.parse(home); ;
        Elements elements = document.select("div.item >  a.movie-box")
        for (Element e : elements.init()) {
            println e.attr("href")
            println getDowland(e.attr("href"));
        }
    }

    void test() {
        println Jsoup.parse(_.get("https://btio.pw/magnet/detail/hash/67F9F836B0C09828A19FB57F9A4788AC9437B6AF")).select("textarea.hidden-xs").text()
    }

    private String search(Document document) {
        Iterator<Element> it = document.select('div.data-list > div').iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (!element.hasClass("hidden-xs")) {
//                String re = Jsoup.parse(_.get(element.select("a").attr("href"))).select("textarea.hidden-xs").text()
                println element.select("a").attr("href")
//                println re
//                return re;
            }
        }
    }

    String getDowland(String dowUrl) {
        try {
//            String url = Jsoup.parse(_.get(dowUrl)).select("div.ptb10 > a").attr("href");
            String url = 'https://btio.pw/search/PARATHD-1660';
            Document document = Jsoup.parse(_.get(url));
            if (document.select('div.data-list > div').size() != 0) {
                search(document);
            }
//            println url
        } catch (Exception e) {
            e.printStackTrace()
        }

    }
}
