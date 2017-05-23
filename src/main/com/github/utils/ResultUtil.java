package github.utils;

import cn.dahe.rm.dto.AjaxDto;
import cn.dahe.rm.dto.BigScreenDto;

import java.util.ArrayList;

/**
 * 返回数据工具类
 * Created by wh on 2017/4/13.
 */
public class ResultUtil {

    public static AjaxDto success(Object object){
        AjaxDto ajaxDto = new AjaxDto();
        ajaxDto.setResult(200);
        ajaxDto.setMsg("成功");
        ajaxDto.setObj(object);
        return  ajaxDto;
    }

    public static AjaxDto success(){
        return success(new ArrayList<>());
    }
    public static AjaxDto fail(){
        return fail();
    }

    public static AjaxDto fail(Integer code,String msg){
        AjaxDto ajaxDto = new AjaxDto();
        ajaxDto.setMsg(msg);
        ajaxDto.setResult(code);
        ajaxDto.setObj("");
        return ajaxDto;
    }

    public static AjaxDto fail(Integer code,String msg,Object obj){
        AjaxDto ajaxDto = new AjaxDto();
        ajaxDto.setResult(code);
        ajaxDto.setMsg(msg);
        ajaxDto.setObj(obj);
        return ajaxDto;
    }

    /**
     * 大屏接口返回成功
     * @param data
     * @return
     */
    public static BigScreenDto bigScreenSuccess(Object data){
        BigScreenDto result = new BigScreenDto(200, data);
        return result;
    }
    public static BigScreenDto bigScreenSuccessAndMsg(String msg, Object data){
        BigScreenDto result = new BigScreenDto(200,msg, data);
        return result;
    }

    /**
     * 大屏接口返回失败
     * @param code
     * @param msg
     * @return
     */
    public static BigScreenDto bigScreenFail(int code, String msg){
        BigScreenDto result = new BigScreenDto(code, msg);
        return result;
    }

}
