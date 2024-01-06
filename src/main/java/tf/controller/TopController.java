package tf.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Authenticator;
import javafx.scene.transform.Translate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tf.entity.SourceData;
import tf.server.SourceDataService;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequestMapping("cyc/test")
public class TopController {


    @Autowired
    private SourceDataService sourceDataService;



    @GetMapping("/top")
    public String Top() {


        List<SourceData>  SourceData =  sourceDataService.queryTop10();
        if(SourceData != null){
        }
        return "请求成功";
    }


    @GetMapping("/download")
    public void downloadPDF(HttpServletResponse response) {
        // 设置文件路径
        Path file = Paths.get("/tmp", "pdf.zip");
        if (Files.exists(file)) {
            // 设置响应头
            response.setContentType("application/zip");
            response.addHeader("Content-Disposition", "attachment; filename=pdf.zip");
            try {
                // 将文件写入到响应的输出流中
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                System.err.println("Error writing file to output stream. Filename was '" + file.getFileName() + "'");
                throw new RuntimeException("IOError writing file to output stream");
            }
        } else {
            // 如果文件不存在，可以返回404或其他错误状态码
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
