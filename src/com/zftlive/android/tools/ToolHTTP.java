package com.zftlive.android.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.zftlive.android.MApplication;

/**
 * HTTP客户端操作工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class ToolHTTP {

	/**异步的HTTP客户端实例**/
	public static AsyncHttpClient client = new AsyncHttpClient();
	
	/**默认字符集**/
	public static String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 模拟GET表单（无参数）
	 * @param url 请求URL
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void get(String url, ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.get(url, responseHandler);
		}
	}
	
	/**
	 * 模拟GET表单（有参数）
	 * @param url 请求URL
	 * @param parmsMap 参数
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void get(String url, Map<String,?> parmsMap, ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.get(url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * 模拟GET表单（有参数）
	 * @param context 上下文
	 * @param url 请求URL
	 * @param parmsMap 参数
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void get(Context context,String url,Map<String,?> parmsMap,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.get(context, url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * 模拟POST表单（无参数）
	 * @param url 请求URL
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void post(String url, ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.post(url, responseHandler);
		}
	}
	
	/**
	 * 模拟POST表单（有参数）
	 * @param url 请求URL
	 * @param parmsMap 参数
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void post(String url, Map<String,?> parmsMap, ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * 模拟POST表单（有参数）
	 * @param context 上下文
	 * @param url 请求URL
	 * @param parmsMap 参数
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void post(Context context,String url,Map<String,?> parmsMap,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(context, url, fillParms(parmsMap,DEFAULT_CHARSET), responseHandler);
		}
	}
	
	/**
	 * 模拟提交POST表单（无参数）
	 * @param context 上下文
	 * @param url 请求URL
	 * @param entity 请求实体,可以null
	 * @param contentType 表单contentType （"multipart/form-data"）
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void post(Context context,String url,HttpEntity entity, String contentType,ResponseHandlerInterface responseHandler) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
			client.post(context, url, entity, contentType, responseHandler);
		}
	}
	
	/**
	 * 模拟提交POST表单（有参数）
	 * @param context 上下文
	 * @param url 请求URL
	 * @param headers 请求Header
	 * @param parmsMap 参数
	 * @param contentType 表单contentType （"multipart/form-data"）
	 * @param responseHandler 回调Handler（BinaryHandler/JSONHandler/JSONArrayHandler/TextHttpResponseHandler）
	 */
	public static void post(Context context,String url,Header[] headers, Map<String,?> parmsMap, String contentType,ResponseHandlerInterface responseHandler,String... charset) {
		if(checkNetwork()){
			//关闭过期连接.
		    client.getHttpClient().getConnectionManager().closeExpiredConnections();
		    if(null != charset && charset.length > 0){
		    	DEFAULT_CHARSET = charset[0];
		    }
			client.post(context, url, headers, fillParms(parmsMap,DEFAULT_CHARSET), contentType, responseHandler);
		}
	}

	/**
	 * 装填参数
	 * @param map 参数
	 * @return 
	 */
	public static RequestParams fillParms(Map<String,?> map,String charset) {
		RequestParams params = new RequestParams();
		if(null != map && map.entrySet().size() > 0)
		{
			//设置字符集,防止参数提交乱码
			if(!"".equals(charset)){
				params.setContentEncoding(charset);
			}
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) 
			{
				Entry entity = (Entry) iterator.next();
				Object key = entity.getKey();
				Object value = entity.getValue();
				if (value instanceof File) {
					try {
						//params.put((String) key, (File) value);用不了 AsyncHttp算错字节少了
						params.put((String) key, new FileInputStream((File) value),((File) value).getName());
					} catch (FileNotFoundException e) {
						throw new RuntimeException("文件不存在！", e);
					}
				} else if (value instanceof InputStream) {
					params.put((String) key, (InputStream) value);
				} else {
					params.put((String) key, value.toString());
				}
			}
		}
		return params;
	}
	
	/**
	 * 检测网络状态
	 * @return 网络是否连接
	 */
	public static boolean checkNetwork(){
		boolean isConnected = ToolNetwork.getInstance().init(MApplication.gainContext()).isConnected();
		if(isConnected){
			return true;
		}else{
			ToolAlert.showShort("网络连接失败");
			return false;
		}
	}
	
}
