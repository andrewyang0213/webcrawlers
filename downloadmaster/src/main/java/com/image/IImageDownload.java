package com.image;

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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.SourceDataLine;

/*
    接口
    基类
    实现类
    虚函数

    覆写

 */
public interface IImageDownload {
    // 执行下载
    public Boolean Run() throws IOException, Exception, UnsupportedOperationException;
}

class BaiduImageDownload extends BaseDownload {

    public BaiduImageDownload(Path rootPath) {
        super(Paths.get(rootPath.toString(), "Baidu"));
    }

    protected String GetSiteUrl() {
        return "https://www.baidu.com/";
    }

}

class SogouImageDownload extends BaseDownload {

    public SogouImageDownload(Path rootPath) {
        super(Paths.get(rootPath.toString(), "ACG"));
    }

    protected String GetSiteUrl() {
        return "https://acg.178.com/";
    }

    @Override
    protected List<String> GetImageUrls(Document insDocument) throws IOException {
        List<String> pics = new ArrayList<String>();
        Elements link = insDocument.select("img");
        // System.out.println(link);
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
        // System.out.println(pics);
        return pics;
    }
}

class BaseDownload implements IImageDownload {

    private Path _rootPath;

    public BaseDownload(Path rootPath) {
        // rootPath 要爬的地址
        _rootPath = rootPath;
        // 判断路径
        _rootPath.toFile().mkdir();
    }

    private void writeImgEntityToFile(HttpEntity imgEntity, String fileAddress)
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
            output.close();
        }
    }

    public Boolean Run() throws Exception, IOException, UnsupportedOperationException, IOException {
        // 执行下载 Url
        String url = GetSiteUrl();
        Document doc = FetchHtmlDocument(url);
        List<String> images = GetImageUrls(doc);
        for (String image : images) {
            try {
                Download(image);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return true;
    }

    protected String GetSiteUrl() throws Exception {
        throw new Exception("没有实现");
    }

    protected List<String> GetImageUrls(Document insDocument) throws IOException {
        List<String> pics = new ArrayList<String>();
        Elements link = insDocument.select("img");
        // System.out.println(link);
        for (Element e : link) {
            String strImage = e.attr("abs:src");
            pics.add(strImage);
        }
        // System.out.println(pics);
        return pics;
    }

    private Document FetchHtmlDocument(String pageUrl) throws IOException {
        Document document = Jsoup.connect(pageUrl).get();
        return document;
    }

    private void Download(String imageUrl) throws UnsupportedOperationException, IOException {

        String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        String fileFullName = Paths.get(_rootPath.toString(), fileName).toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet getImage = new HttpGet(imageUrl);
        CloseableHttpResponse responseImg = httpClient.execute(getImage);
        HttpEntity entity = responseImg.getEntity();

        writeImgEntityToFile(entity, fileFullName);
    }
}

class MainClass {
    private static final Path sysPath = Paths.get("/Users", "andrewyang", "JAVAImages", "DownloadMaster");

    // 入口函数类
    public static void main(String[] args) throws IOException {
        List<IImageDownload> downloaders = new ArrayList<IImageDownload>();
        downloaders.add(new BaiduImageDownload(sysPath));
        downloaders.add(new SogouImageDownload(sysPath));
        // downloaders.add(new SogouDownload(...));

        for (IImageDownload downloader : downloaders) {
            try {
                downloader.Run();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
