package github.utils;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/5.
 */

public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;

    //2.暴露出一个方法，返回当前类的对象
    private static OkHttpUtils mInstance;
    public static OkHttpUtils newInstance() {
        if (mInstance == null) {
            //实例化对象
            //加上一个同步锁，只能有一个执行路径进入
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                }
            }
        }
        return mInstance;
    }




//            //关闭防止内存泄漏
//            if(response.body()!=null){
//                response.body().close();
//  }


    /**
     * Get请求
     *
     * @param url
     */
    public  Response doGet(String url) {
        Response response = null;
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            response =mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Post请求发送键值对数据
     *
     * @param
     * @param url
     * @param mapParams
     */
    public  Response doPost(String url, Map<String, String> mapParams) {
        Response response = null;
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        try {
            response =mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Post请求发送JSON数据
     *  @param url
     * @param jsonParams
     */
    public Response doPost(String url, String jsonParams) {
//        System.out.println("--------"+url);
//        System.out.println("+++++++++++"+jsonParams);
        Response response = null;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            response = mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public interface HttpCallBack {
        void onError(Response response, IOException e);

        void onSuccess(Response response, String result);
    }

    public void asyncGet(String url, final HttpCallBack httpCallBack) {
        final Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (httpCallBack != null) {
                    httpCallBack.onSuccess(response, result);
                }
            }
        });
    }


    public void asyncPost(String url, RequestBody formBody, final HttpCallBack httpCallBack) {
        final Request request = new Request.Builder().url(url).post(formBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (httpCallBack != null) {
                    httpCallBack.onSuccess(response, result);
                }
            }
        });
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
