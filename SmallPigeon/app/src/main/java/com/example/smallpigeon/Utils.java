package com.example.smallpigeon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @auther angel
 * @description 工具类
 * @date 2019/12/05 17:15
 */

public class Utils {

    private String result;
    private Bitmap bitmapResult;
    public static final String ip = "192.168.2.189";
    public static final String project = "smallpigeon";

    //不需要参数的数据传输
    public String getConnectionResult(String controller, String method){
        try {
            URL url = new URL("http://"+ip+":8080/"+project+"/"+controller+"/"+method);
            URLConnection conn = url.openConnection();
            Log.e("Utils发送数据in 之前","fasong shux ");
            InputStream in = conn.getInputStream();
            Log.e("Utils发送数据in 之后","fasong shux ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            result = reader.readLine();
            Log.e("Utils发送数据in 之前",result);
            reader.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //需要参数的数据传输
    public String getConnectionResult(String controller, String method, String params){
        try {
            URL url = new URL("http://"+ip+":8080/"+project+"/"+controller+"/"+method+"/?"+params);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            result = reader.readLine();
            reader.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //从网络上获取图片bitmap
    public Bitmap getWebPicture(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000);  //设置超时时间
            connection.setDoInput(true);
            connection.setRequestMethod("GET"); //设置方式为get
            connection.connect();
            InputStream stream = connection.getInputStream();
            bitmapResult = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapResult;
    }

    //从后台获取返回的图片
    public Bitmap getServerPicture(String controller, String method){

        try {
            URL url = new URL("http://"+ip+":8080/"+project+"/"+controller+"/"+method);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            /*其中一种解析数据流获取图片的方法
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            bitmapResult = BitmapFactory.decodeByteArray(data,0,data.length);
            outStream.close();*/

            //另外一种方法
            bitmapResult = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapResult;

        /*
         配套后台例子
         String path = PathKit.getWebRootPath();
         File file = new File(path+"\\WEB-INF\\avatar\\girl.jpg");
         renderFile(file);
         */

    }

    //向后台发送图片
    public String postPictureServer(String controller, String method,Bitmap bitmap){

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            byte[] bytes = outputStream.toByteArray();
            URL url = new URL("http://"+ip+":8080/"+project+"/"+controller+"/"+method);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(30000);
            connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("Origin", "http://"+ip+":8080");
            connection.setDoOutput(true);
            connection.getOutputStream().write(bytes);
            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            result = reader.readLine();
            reader.close();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

        /*
         配套后台例子
         String path = PathKit.getWebRootPath()+"\\WEB-INF\\avatar\\aaaaa.jpg";
         InputStream inputStream = getRequest().getInputStream();
         byte[] bytes = new byte[1024];
         int len = 0;
         FileOutputStream outputStream = new FileOutputStream(path);
         while ((len = inputStream.read(bytes)) != -1){
             outputStream.write(bytes,0,len);
         }
         inputStream.close();
         outputStream.close();
         renderText("true");
         */

    }

}
