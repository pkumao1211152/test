package tf.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * 数据翻译
 */
public class TranslateTool {
    //蹭用俊贤的翻译
    public static String translateUrl = "http://wangjx.eu.org:9527/translate";

    /**
     * @param text 源内容
     * @param s 源  EN
     * @param t 目标 ZH
     * @return 目标类容
     * */
    public static String translate(String text,String s,String t ) {

        JSONObject params = new JSONObject();

        params.put("text",text);
        params.put("source_lang", s);
        params.put("target_lang", t);
        HttpRequest request = HttpUtil.createPost(translateUrl);
        String body = request.body(params.toString()).execute().body();
        if (body != null && body.contains("data")) {
            Map<String, String> object = JSON.parseObject(body, new TypeReference<Map<String, String>>() {
            });
            return object.get("data");
        }

        return "";
    }

    public static void main(String[] args) {
        String s = translate("home page Reading ListWith Catalogs Query","EN","ZH");
        System.out.println(s);
    }
}
