package com.keduw.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 文件上传下载类
 * @author hsfeng
 */
public class FileUtil {

    //文件下载
    public static void download(HttpServletRequest request, HttpServletResponse response, String fileName){
        try {
            //获取绝对路径
            String path = request.getSession().getServletContext().getRealPath("");
            File file = new File(path + "/upload/append/" + fileName);
            // 如果文件不存在
            if (!file.exists()) {
                return;
            }
            // 设置响应头，控制浏览器下载该文件
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            // 读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(path + "/upload/append/" + fileName);
            // 创建输出流
            OutputStream out = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区当中
            while ((len = in.read(buffer)) > 0) {
                // 输出缓冲区的内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}
