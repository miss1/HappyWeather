package com.yangll.bishe.happyweather.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try{
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					if(listener != null){
						listener.onFinish(response.toString());
					}
				}catch(Exception e){
					if(listener != null){
						listener.onError(e);
					}
				}finally{
					if(connection != null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	//获取网络图片
	public static void getUrlImg(final String address, final HttpImgCallbackListener listener){
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection=null;
                Bitmap bmp=null;
                try {
                    URL url = new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(6000);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    InputStream is = connection.getInputStream();
                    bmp = BitmapFactory.decodeStream(is);
                    is.close();
                    if (listener != null){
                        listener.onFinish(bmp);
                    }
                } catch (Exception e) {
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
