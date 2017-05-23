package github.vo;

public class AjaxVo {

    private int result;
    private String msg;
    private Object obj;

    @Override
    public String toString() {
        return "AjaxDto{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public AjaxVo() {
    }

    public AjaxVo(int result, String msg, Object obj) {
        this.result = result;
        this.msg = msg;
        this.obj = obj;
    }

    public AjaxVo(int result) {
        this.result = result;
    }

    public AjaxVo(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
