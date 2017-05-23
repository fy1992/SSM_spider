package github.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IDEA
 * Date: 17/4/28
 * Time: 16:28
 */
public class POIUtil {
    /**
     * Word 2007以上
     *
     * @param filePath 文件绝对路径
     * @return word内容
     */
    public static String readDocx(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            fis.close();
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readDocx(InputStream fis) {
        try {
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            fis.close();
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Word 97 - Word 2003
     *
     * @param filePath 文件绝对路径
     * @return word内容
     */
    public static String readDoc(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            fis.close();
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Word 97 - Word 2003
     *
     * @param inputStream 文件流
     * @return word内容
     */
    public static String readDoc(InputStream inputStream) {
        try {
            HWPFDocument document = new HWPFDocument(inputStream);
            WordExtractor extractor = new WordExtractor(document);
            inputStream.close();
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
