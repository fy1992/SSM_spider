package github.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * Date 2017/5/20.
 */
public class ZxingUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 200;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    public static BufferedImage createImage(JSONObject json, boolean needCompress) throws Exception {
        String content = json.toJSONString();// 内容
//        int width = 200; // 图像宽度
//        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, 200, 200, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        ZxingUtil.insertImage(image, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source
     *            二维码图片
     *            LOGO图片地址
     * @param needCompress
     *            是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source,
                                    boolean needCompress) throws Exception {
        String imagePath = SpringContextHolder.getPath("/");
        imagePath += "/resources/img/qrlogo.png";
        File file = new File(imagePath);
        if (!file.exists()) {
            return;
        }
        Image src = ImageIO.read(new File(imagePath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content
     *            内容
     *            LOGO地址
     * @param output
     *            输出流
     * @param needCompress
     *            是否压缩LOGO
     * @throws Exception
     */
    public static void encode(JSONObject content, OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = ZxingUtil.createImage(content,needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

}
