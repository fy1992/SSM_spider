package github.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * post get请求
 */
public class HttpRequestProxy {
	
	private static Log logger = LogFactory.getLog(HttpRequestProxy.class);
	
	// 连接超时时间
	private  final  static  int CONNECT_TIMEOUT=8000;
	// 读取超时时间
	private final static int SOCKET_TIMEOUT=8000;
	
	private static CloseableHttpClient httpClient;
	
	private static final String CHARSET ="UTF-8";
	
	static {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
				.build();
	}
	
	
	public static String doGet(String url,Map<String,String> params){
		return doGet(url,params,null,CHARSET);
	}
	
	public static String doGet(String url,Map<String,String> params,Map<String,String> headers){
		return doGet(url,params,headers,CHARSET);
	}
	
	public static String doGet(String url, Map<String, String> params, String charset){
		return doGet(url, null, null, charset);
	}
	
	public static String doPost(String url, Map<String, String> params){
		return doPost(url, params, null, CHARSET);
	}
	
	public static String doGet(String url,Map<String,String> params,Map<String,String> headers,String charset){
		if(StringUtils.isBlank(url)){
			return null;
		}
		try {
			if(params!=null&&!params.isEmpty()){
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for(Map.Entry<String, String> entry:params.entrySet()){
					String value = entry.getValue();
					if(value!=null){
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url+="?"+ EntityUtils.toString(new UrlEncodedFormEntity(pairs,charset));
			}
			
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
			if(headers!=null&&!headers.isEmpty()){
				for(Map.Entry<String, String> entry:headers.entrySet()){
					String value = entry.getValue();
					if(value!=null){
						httpGet.addHeader(entry.getKey(),value);
					}
				}
			}
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode!=200){
				httpGet.abort();
				logger.info("--http code---"+statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if(entity!=null){
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String doPost(String url, Map<String, String> params, Map<String,String> headers, String charset){
		logger.info("--- POST BEGIN ---");
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			if(headers!=null && !headers.isEmpty()){
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						httpPost.addHeader(entry.getKey(), value);
					}
				}
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
//				throw new RuntimeException("HttpClient,error status code :"
//						+ statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			logger.info("--- POST END ---");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("--- POST END ---");
		return null;
	}
	
	public static String doPost(String url, String entity){
		logger.info("--- POST BEGIN ---");
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(entity, CHARSET));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :"
						+ statusCode);
			}
			HttpEntity rspEntity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(rspEntity, "utf-8");
			}
			EntityUtils.consume(rspEntity);
			response.close();
			logger.info("--- POST END ---");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("--- POST END ---");
		return null;
	}
	
}
