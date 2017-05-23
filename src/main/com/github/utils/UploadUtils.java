package github.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by laiyy
 * Date: 17-4-14. 下午1:54
 */
public class UploadUtils {

    /**
     * 上传方法
     * @param request
     * @param response
     * @throws IOException
     */
    public static String upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String savePath = ResourcesUtils.getFilePath();
            String saveUrl = ResourcesUtils.getFileUrl();

            Date date = new Date();
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

            savePath += yearFormat.format(date) + File.separator + monthFormat.format(date) + File.separator + dayFormat.format(date) + File.separator;
            saveUrl += yearFormat.format(date) + "/" + monthFormat.format(date) + "/" + dayFormat.format(date) + "/";

            File uploadDir = new File(savePath);
            if (!uploadDir.exists()){
                uploadDir.mkdirs();
            }

            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件需要上传，即：多部分请求
            if (multipartResolver.isMultipart(request)){

                // 转换成多部分 request
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                // 取得 request 中的所有文件名
                Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
                if (fileNames.hasNext()){
                    MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());
                    if (file != null){
                        String filename = file.getOriginalFilename();
                        if (StringUtils.isNotBlank(filename.trim())){

                            // 重命名
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                            String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
                            String newFileName = dateFormat.format(date) + new Random().nextInt(1000) + "." + fileExt;

                            savePath += newFileName;
                            File localFile = new File(savePath);
                            // 上传
                            file.transferTo(localFile);

                            return saveUrl + newFileName;
                        }
                    }
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载方法
     * @param filePath 文件路径
     * @param fileName 下载文件名
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String filePath, String fileName) throws IOException {
        // 获取需要下载的文件
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        // 设置编码，防止出现中文乱码问题
        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        // 设置 Header
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 开始下载
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    /**
     * 获取新闻内容中的图片地址
     * @param htmlCode 新闻内容
     * @return
     */
    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        return imageSrcList;

    }

}
