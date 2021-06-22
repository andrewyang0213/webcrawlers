package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.SourceDataLine;

public class DownloadImg {

    public static void writeImgEntityToFile(HttpEntity imgEntity, String fileAddress)
            throws UnsupportedOperationException, IOException {
        File storeFile = new File(fileAddress);
        FileOutputStream output = new FileOutputStream(storeFile);

        if (imgEntity != null) {
            InputStream instream;
            instream = imgEntity.getContent();
            byte b[] = new byte[8 * 1024];
            int count;
            while ((count = instream.read(b)) != -1) {
                output.write(b, 0, count);
            }

        }
        // try {

        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // output.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
    }

    public static List<String> getHrefStr(String htmlStr) throws IOException {
        Document document = Jsoup.connect(htmlStr).get();

        Elements liBoxLink = document.getElementsByClass("imgbox");
        // System.out.println(liBoxLink);
        Elements hrefLink = liBoxLink.select("a");
        // System.out.println(hrefLink);
        List<String> hrefpics = new ArrayList<String>();
        for (Element elem : hrefLink) {
            String dataStrPage = elem.attr("abs:href");
            hrefpics.add(dataStrPage);
            // System.out.println("hrefList: " + dataStrPage);
        }
        return hrefpics;
    }

    public static LinkedHashSet<String> getImgStr(String htmlStr) throws IOException {
        Document document = Jsoup.connect(htmlStr).get();

        Elements link = document.select("img");
        // System.out.println(link);
        LinkedHashSet<String> pics = new LinkedHashSet<String>();
        for (Element e : link) {
            String dataStrImage = e.attr("abs:data-src");
            if (dataStrImage != "") {
                pics.add(dataStrImage);
                // System.out.println("DE: " + dataStrImage);
            } else {
                String strImage = e.attr("abs:src");
                pics.add(strImage);
                // System.out.println("E: " + strImage);
            }

        }
        // System.out.println("LINK: " + link);

        /*
         * p_image = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)"); m_image =
         * p_image.matcher(htmlStr); while (m_image.find()) { String img =
         * m_image.group(); Matcher m =
         * Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')").matcher(img); while
         * (m.find()) { pics.add(m.group(3)); } }
         */
        return pics;
    }

    public static void main(String[] args) {
        System.out.println("获取图片地址中……");
        String url = "https://acg.178.com/";
        // DateTimeFormatter dateFormat =
        // DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // Pattern p = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
            // String str = "<img alt=\"\" src=\"0.jpg\"/><img alt=\"\" src=\"1.jpg\"/><img
            // alt=\"\" src=\"2.jpg\"/><img alt=\"\" src=\"3.jpg\"/>"; //
            // EntityUtils.toString(response.getEntity());
            // String str = EntityUtils.toString(response.getEntity()).toString();
            // System.out.println(str);
            List<String> hrefRes = getHrefStr(url);
            //System.out.println("HREF RES: " + hrefRes);
            LinkedHashSet<String> resSet = new LinkedHashSet<String>();
            for (int index = 0; index < hrefRes.size(); index++) {
                resSet = getImgStr(hrefRes.get(index));

                List<String> res = new ArrayList<String>(resSet);

                //System.out.println("RES: " + res + "\n" + res.size());

                String address = null;
                for (int i = 0; i < res.size(); i++) {
                    try {
                        // if (i == 0) {
                        // // address = res.get(i).substring(res.get(i).indexOf("js?") + 3);
                        // // address.trim();
                        // // res.set(i, address);
                        // // // address = address.substring(0, address.indexOf('"'));
                        // // // System.out.println("FULL ADDRESS: " + address);
                        // } else {
                        // i--;

                        // i++;
                        // }
                        address = res.get(i);
                        System.out.println("图片地址:" + address);
                        System.out.println("正在下载……");
                        // String pre = LocalDate.now().format(dateFormat) + ;
                        String fileName = address.substring(address.lastIndexOf('/') + 1);
                        File storeDir = new File("/Users/andrewyang/JAVAImages/Pics");
                        boolean dirCreated = storeDir.mkdir();
                        // System.out.println(dirCreated);
                        String fileFullName = Paths.get("/Users", "andrewyang", "JAVAImages", "Pics", fileName)
                                .toString();

                        HttpGet getImage = new HttpGet(address);
                        CloseableHttpResponse responseImg = httpClient.execute(getImage);
                        HttpEntity entity = responseImg.getEntity();

                        writeImgEntityToFile(entity, fileFullName);

                        System.out.println("下载完毕.");
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            /*
             * Matcher m = p.matcher(str); //String address = null; if (m.find()) {
             * //System.out.println("M GROUP: " + m.group()); address =
             * m.group().substring(m.group().indexOf("js?") + 3); address.trim(); address =
             * address.substring(0, address.indexOf('"'));
             * System.out.println("FULL ADDRESS: " + address); } else {
             * System.out.println("No match found"); System.exit(0); }
             */

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
