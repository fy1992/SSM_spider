package github.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fy
 * 字符串处理
 */
public class StringUtil extends StringUtils{

	/**
	 * 是否是纯数字
	 * @param str
	 */
	public static boolean isNumber(String str){
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(str);
        return m.find();
	}

	/**
	 * 是否是电话
	 * @param str
	 */
	public static boolean isMobile(String str){
		return str.length() > 6 && str.length() <= 11 && str.matches("^1[34578][0-9]{9}$");
	}

    /**
     * 字符串中是否含有汉字或标点
     * @param str
     */
	public static boolean hasWordAndPoint(String str){
	    if(StringUtils.isBlank(str)){
	        return true;
        }
        String reg = "[^\\\\x00-\\\\xff]?";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        while(m.find()){
            return true;
        }
        return false;
	}

    /**
     * 数字字符串为null时赋值
     * @param str
     */
	public static int formatStr2Int(String str){
		if(StringUtils.isBlank(str)){
			return 0;
		}
		if(str.equals("0.0")){
			str = "0";
		}
		return Integer.parseInt(str);
	}

    /**
     * 字符串为null的赋值
     * @param str 字符串
     */
	public static String formatStr(String str){
	    if(StringUtils.isBlank(str)){
	        str = "";
        }
	    return str;
    }

	/**
	 * 通过数组获得StringBuffer （舆情方案组合用）
	 * @param array  数组
	 * @param space  间隔
	 * @return StringBuffer
	 */
    public static StringBuffer getStringBufferByArray(String[] array, StringBuffer sb, String space){
        if(array == null){
            return sb;
        }
        if(sb == null){
            sb = new StringBuffer();
        }
        sb.append("(");
		for(int i = 0, len = array.length; i < len; i++){
			sb.append(array[i] + space);
		}
		sb = sb.deleteCharAt(sb.length() - 1);
		sb.append(")_");
		return sb;
	}

    /**
     * 检查指定字符串的出现次数
     * @param words 原文
     * @param space 指定字符串
     * @param num 出现次数
     */
	public static boolean checkWordNum(String words, String space, int num){
        if(StringUtil.isEmpty(space) || space.length() == 0){
            return false;
        }
        if(num <= 0){
            num = 1;
        }
        return num >= words.split(space).length;
    }

	/**
	 * 过滤全部的标签
	 * @param str 字符串
	 */
	public static String replaceTag(String str){
		if(StringUtils.isBlank(str)){
			return "";
		}
		String regex = "(</?.*?>)";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(str);
		while(m.find()){
			str = str.replaceAll(m.group(), " ");
		}
		return str;
	}

    /**
     * 文章截取摘要
     *
     * @param con 文章内容
     * @return
     */
    public static String getSummary(String con) {
        String content = null;
        if (StringUtils.isNotBlank(con)) {
            String[] split = con.split("</p>");
            int index = 0;
            while (index < split.length) {
                String str = split[index];
                String regxpForImgTag = "<img\\s[^>]+/>";
                Pattern pattern = Pattern.compile(regxpForImgTag);
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    String temp = matcher.group();
                    String tempUrl = temp.substring(temp.indexOf("src=") + 5);
                    tempUrl = tempUrl.substring(0, tempUrl.indexOf("\""));
                    String urlResult = "";
                    str = str.replace(temp, urlResult);
                }
                str = str.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
                str = str.replaceAll("&.{2,6}?;", "");
                str = str.replaceAll("\\n", "");
                str = str.replaceAll("\\t", "");
                str = str.replaceAll("&nbsp;", "");
                str = str.replaceAll("\\r;", "");
                str = str.replaceAll(" ", "");
                str = str.replaceAll("\r", "");

                if (StringUtils.isBlank(str)) {
                    index++;
                    continue;
                } else {
                    content = str;
                    break;
                }
            }
        }
        return content;
    }

    public static void main(String[] args) {}
}
