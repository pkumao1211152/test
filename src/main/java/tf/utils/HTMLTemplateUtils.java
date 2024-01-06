package tf.utils;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class HTMLTemplateUtils {

	
	private final static TemplateEngine engine=new TemplateEngine();
	 
    /**
     * 使用 Thymeleaf 渲染 HTML
     * @param template  HTML模板
     * @param params 参数
     * @return  渲染后的HTML
     */
    public static String render(String html,Map<String,Object> params){
        Context context = new Context();
        context.setVariables(params);
        return engine.process(html,context);
    }
    
    public static String getText(InputStream is)  {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String st;
		StringBuffer sb = new StringBuffer();
		try {
			while ((st = br.readLine()) != null) {
				sb.append(st);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
    
}
