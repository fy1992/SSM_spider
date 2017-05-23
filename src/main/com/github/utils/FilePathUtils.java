package github.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 文件路径工具类
 * Created by fy on 2017/5/6.
 */
public class FilePathUtils {
    public static String getNewsLayoutPath(){
        return ResourcesUtils.getNewsLayoutPath();
    }

    /**
     * 通过目录名获取目录下所有的文件的路径
     * @param fileName    目录名
     */
    public static List<Map<String, String>> getLayoutPathsByName(String fileName){
        String path = getNewsLayoutPath();
        File paperStyleDir = new File(path + File.separator + fileName);
        List<Map<String, String>> result = Lists.newArrayList();
        if(paperStyleDir.exists() && paperStyleDir.isDirectory()){
            File[] files = paperStyleDir.listFiles();
            for(int i = 0, len = files.length; i < len; i++){
                File file = files[i];
                if(file.isFile()){
                    Map<String, String> item = Maps.newHashMap();
                    item.put("path", getTargetDirPath(file.getPath(), 3, 0, new StringBuffer()));
                    item.put("name", Files.getNameWithoutExtension(file.getPath()));
                    result.add(item);
                }
            }
        }
        return result;
    }

    /**
     * 根据目录名称 新增或修改目录名称
     * @param dirName     新目录名
     * @param oldName     旧目录名
     */
    public static boolean createOrUpdateDirByName(String dirName, String oldName){
        String path = getNewsLayoutPath();
        File file;
        if(StringUtils.isBlank(oldName)){
            file = new File(path + File.separator + dirName);
            return file.mkdirs();
        }else{
            file = new File(path + File.separator + oldName);
            if(!file.exists()){
                file.mkdirs();
            }
            return file.renameTo(new File(path + File.separator + dirName));
        }
    }

    /**
     * 获得指定深度的文件路径
     * @param filePath  完整文件路径
     * @param deep      指定文件路径深度
     * @param mark      标志位
     * @param paths     结果
     */
    public static String getTargetDirPath(String filePath, int deep, int mark, StringBuffer paths){
        ++ mark;
        if(mark <= deep){
            File file = new File(filePath);
            getTargetDirPath(file.getParent(), deep, mark, paths);
            String path = StringUtils.substringAfterLast(filePath, File.separator);
            paths.append(File.separator + path);
        }
        return paths.toString();
    }
}
