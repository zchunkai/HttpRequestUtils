package cn.vko.eduorder.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    public static void main(String[] args) throws IOException {
//        System.out.println(URLDecoder.decode("中国", "utf-8"));
        String s = sendGet("https://101.aspoontech.com/ask/schmeal/addMealByCustomer",
                "userid=8443&date=2021-09-14&mealType=%E5%8D%88%E9%A4%90&mealname=%E5%8F%B0%E6%B9%BE%E5%8D%A4%E8%82%89%E9%A5%AD&weight=1200&mealId=0");
//        "userid=8443&date=2021-09-14&mealType="+ URLEncoder.encode("午餐", "UTF-8") +"&mealname="+ URLEncoder.encode("鱼香肉丝", "UTF-8") +"&weight=100&mealId=0");
        System.out.println(s);
    }

    /**
     * Description 发送带有中文的get请求
     * @author zck
     * @date 2021/9/1
     */
/*    public static  String goGet(String url, String param){
        String res = "";
        StringBuffer buffer = new StringBuffer();
        try {
            URL url1 = new URL(url+"?"+param);
            System.out.println("发送GET请求-->"+url1);
            HttpURLConnection urlCon = (HttpURLConnection) url1.openConnection();
            System.out.println("返回状态为:"+urlCon.getResponseCode());
            if (200 == urlCon.getResponseCode()) {
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String str = null;
                while ((str = br.readLine()) != null) {
                    buffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                res = buffer.toString();
                return res;
            }else {
                throw new Exception("连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
    }*/




    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url  发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url  发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//      conn.setRequestProperty("Pragma:", "no-cache");
//      conn.setRequestProperty("Cache-Control", "no-cache");
//      conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
