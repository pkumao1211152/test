package tf.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.core.io.ClassPathResource;
import tf.vo.Paragraphs;
import tf.vo.Result;
import tf.vo.Show;
import tf.vo.req.GraphqlRequestVo;
import tf.vo.req.VariablesVo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @date: 2024年1月6日 上午12:28:45
 */
public class PullDatUtil {

	private static final String url = "https://medium.com/_/graphql";

	/**
	 * 魔法测试
	 * 
	 * @return
	 */
	public static boolean testPing() {
		Connection connect = Jsoup.connect(url).timeout(3000).header("Content-Type", "application/json")
				.requestBody("[]").ignoreContentType(true).method(Connection.Method.POST);
		try {
			connect.execute();
			return true;
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				return false;

			}
			e.printStackTrace();
		}
		return false;
	}

	public static List<Result> pullData(List<String> ids){
		List<GraphqlRequestVo> reqs = new ArrayList<GraphqlRequestVo>();

		ids.forEach(e -> {
			reqs.add(new GraphqlRequestVo(new VariablesVo(e)));
		});
		HttpResponse execute = HttpUtil.createPost(url).body(JSON.toJSONString(reqs)).execute();
		int status = execute.getStatus();
		if (status == 200) {

			String responseBody = execute.body();

			return JSON.parseObject(responseBody, new TypeReference<List<Result>>() {
			});
		}
		return null;
	}
	/**
	 * 文章数据抓取
	 * 
	 * @param proxyType 1:socket代理 2:http代理
	 * @param proxyHost 代理服务器
	 * @param proxyPort 代理端口
	 * @param ids       文章id集合
	 * @return
	 */
	public static List<Result> pullData(int proxyType, String proxyHost, int proxyPort, List<String> ids) {
		List<GraphqlRequestVo> reqs = new ArrayList<GraphqlRequestVo>();

		ids.forEach(e -> {
			reqs.add(new GraphqlRequestVo(new VariablesVo(e)));
		});
		Connection connect = Jsoup.connect(url).timeout(5000).header("Content-Type", "application/json")
				.requestBody(JSON.toJSONString(reqs)).ignoreContentType(true).method(Connection.Method.POST);

		if (proxyType == 1) {
			connect.proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHost, proxyPort)));
		} else if (proxyType == 2) {
			connect.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
		}

		Response response;
		try {
			response = connect.execute();
			int statusCode = response.statusCode();
			System.out.println(statusCode);
			if (statusCode == 200) {
				String responseBody = response.body();

				return JSON.parseObject(responseBody, new TypeReference<List<Result>>() {
				});
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}


	public static void creatPdf(List<String> ids) throws Exception {


		String proxyHost = "127.0.0.1";

		int proxyPort = 7890;
		//是否需要魔法 3 不需要
		List<Result> list = PullDatUtil.pullData(2, proxyHost, proxyPort, ids);
		//List<Result> list = PullDatUtil.pullData(ids);

		//pdf的文件流
		List<byte[]> pdfBytesList = new ArrayList<byte[]>();
		//每个pdf的文件名
		List<String> fileNames = new ArrayList<String>();

		list.forEach(e->{
			List<Show> textList = new ArrayList<Show>();
			List<Paragraphs> paragraphsList = e.getData().getPostResult().getContent().getBodyModel().getParagraphs();
			paragraphsList.forEach(p->{
				if(!"IMG".equalsIgnoreCase(p.getType())) {
					String text = p.getText();
					String s = TranslateTool.translate(text, "EN", "ZH");

					textList.add(new Show(text,s));
				}

			});


			String html;
			try {
				html = HTMLTemplateUtils
						.getText(new ClassPathResource("template/PDF.html").getInputStream());
				HashMap<String, Object> map = new HashMap<>();
				map.put("list", textList);

				String render = HTMLTemplateUtils.render(html, map);


				PDFUtils pd = new PDFUtils();



				fileNames.add(e.getData().getPostResult().getTitle()+".pdf");

				pdfBytesList.add(pd.html2PDF(render));


			} catch (Exception e1) {
				e1.printStackTrace();
			}




		});

		try {


			byte[] zipFile = createZip(pdfBytesList, fileNames);

			FileOutputStream fos = new FileOutputStream("d:/pdf.zip");
			fos.write(zipFile);
			fos.flush();
			fos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}



	}



	public static byte[] createZip(List<byte[]> fileStreams, List<String> fileNames) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(baos)) {
			for (int i = 0; i < fileStreams.size(); i++) {
				byte[] fileStream = fileStreams.get(i);
				String fileName = fileNames.get(i);

				ZipEntry zipEntry = new ZipEntry(fileName);
				zos.putNextEntry(zipEntry);

				ByteArrayInputStream bis = new ByteArrayInputStream(fileStream);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = bis.read(buffer)) != -1) {
					zos.write(buffer, 0, bytesRead);
				}
				bis.close();

				zos.closeEntry();
			}
		}

		return baos.toByteArray();
	}




}
