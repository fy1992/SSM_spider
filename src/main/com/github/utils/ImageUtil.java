package github.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 2016/12/12
 * Time: 11:06
 * Created by IntelliJ IDEA.
 */
public class ImageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 从HTML源码中提取图片路径，最后以一个 String 类型的 List 返回，如果不包含任何图片，则返回一个 size=0　的List
     * 需要注意的是，此方法只会提取以下格式的图片：.jpg|.bmp|.eps|.gif|.mif|.miff|.png|.tif|.tiff|.svg|.wmf|.jpe|.jpeg|.dib|.ico|.tga|.cut|.pic
     * @param htmlCode HTML源码
     * @return <img>标签 src 属性指向的图片地址的List集合
     * @author Carl He
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


    public static String getImageSrcByString(String htmlCode) {
        List<String> str = getImageSrc(htmlCode); // 调用返回 List 形式的方法
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = str.iterator();
        while (iterator.hasNext()) {
            String src = iterator.next();
            stringBuffer.append(src);
            if (iterator.hasNext()) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * @param content
     */
    public static List<String> listContentImgUrl(String content) {
        Pattern p = Pattern.compile("<img.*?\\s+src=['\"](.*?)['\"].*?>");
        Matcher m = p.matcher(content);
        List<String> srcs = new ArrayList<>();
        while (m.find()) {
            srcs.add(m.group(1));
        }
        return srcs;
    }

    /**
     * 取出img中的alt
     * @param content
     * @return
     */
    public static List<String> listContentAlt(String content) {
        Pattern p = Pattern.compile("<img.*?\\s+alt=['\"](.*?)['\"].*?>");
        Matcher m = p.matcher(content);
        List<String> srcs = new ArrayList<>();
        while (m.find()) {
            srcs.add(m.group(1));
        }
        return srcs;
    }

    /**
     * 取出img中的title
     * @param content
     * @return
     */
    public static List<String> listContentTitle(String content) {
        Pattern p = Pattern.compile("<llll.*?>(.*?)<\\/llll>");
        Matcher m = p.matcher(content);
        List<String> srcs = new ArrayList<>();
        while (m.find()) {
            srcs.add(m.group(1));
        }
        return srcs;
    }
    /**
     * 取出img中的title
     * @param content
     * @return
     */
    public static List<String> listContentType(String content) {
        Pattern p = Pattern.compile("<zzzz.*?>(.*?)<\\/zzzz>");
        Matcher m = p.matcher(content);
        List<String> srcs = new ArrayList<>();
        while (m.find()) {
            srcs.add(m.group(1));
        }
        return srcs;
    }

    /**
     * 图片切割
     *
     * @param imagePath 原图地址
     * @param x         目标切片坐标 X轴起点
     * @param y         目标切片坐标 Y轴起点
     * @param w         目标切片 宽度
     * @param h         目标切片 高度
     */
    public static void cutImage(String imagePath, int x, int y, int w, int h, int iw, int ih) {
        try {
            logger.info("x---------->" + x);
            logger.info("y---------->" + y);
            logger.info("w---------->" + w);
            logger.info("h---------->" + h);
            logger.info("iw--------->" + iw);
            logger.info("ih--------->" + ih);
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(imagePath));
            int srcWidth = bi.getWidth();      // 源图宽度
            logger.info("源图宽度" + srcWidth);
            int srcHeight = bi.getHeight();    // 源图高度
            logger.info("源图高度" + srcHeight);

            //若原图大小大于切片大小，则进行切割
            if (srcWidth >= w && srcHeight >= h) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);

                int x1 = x * srcWidth / iw;
                logger.info("x1---------->" + x1);
                int y1 = y * srcHeight / ih;
                logger.info("y1---------->" + y1);
                int w1 = w * srcWidth / iw;
                logger.info("w1---------->" + w1);
                int h1 = h * srcHeight / ih;
                logger.info("h1---------->" + h1);
                cropFilter = new CropImageFilter(x1, y1, w1, h1);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(imagePath));
                logger.info("裁切图片完毕");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查文件后缀
     * @param fileName    文件名
     * @param targetPostFix 要检查的后缀名
     * @return
     */
    public static boolean checkFilePostfix(String fileName, String targetPostFix){
        if(StringUtils.isEmpty(fileName) || fileName.equals("")){
            return false;
        }
        if(fileName.contains(".")){
            String lastStr = fileName.substring(fileName.lastIndexOf("."));
            return lastStr.equals(targetPostFix);
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        double x = 283;
        double y = 412;
        double w = 117;
        double i = w / 400;
        double v = y / i;
        System.out.println(i + "-------------" + v);
    }
}


