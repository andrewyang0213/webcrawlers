package com.images;

import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// <summary>
// 基类
// </summary>
public abstract class SpilderBase implements ISpilder {

    protected Path _rootPath;

    public SpilderBase(Path path) {
        _rootPath = path;
        _rootPath.toFile().mkdir();
    }

    public Boolean Download(ImageMateData mate) {

        try {

            String fileFullName = Paths.get(_rootPath.toString(), mate.getHash()).toString();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet getImage = new HttpGet(mate.getUri());
            CloseableHttpResponse responseImg = httpClient.execute(getImage);
            HttpEntity entity = responseImg.getEntity();
            writeImgEntityToFile(entity, fileFullName);
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;

    }

    public List<ImageMateData> FetchMates(String stringUrl) {

        // 执行下载 Url
        // Map<ImageMateData, ImageMateData> imageInfo = new HashMap<ImageMateData,
        // ImageMateData>();
        Document doc;
        List<ImageMateData> images = new ArrayList<ImageMateData>();
        try {

            doc = FetchHtmlDocument(stringUrl);
            images = GetImageInfos(doc, stringUrl);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return images;
    }

    protected void writeImgEntityToFile(HttpEntity imgEntity, String fileAddress)
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

    protected Document FetchHtmlDocument(String pageUrl) throws IOException {
        Document document = Jsoup.connect(pageUrl).get();
        return document;
    }

    protected List<ImageMateData> GetImageInfos(Document insDocument, String pageUrl) throws IOException {
        List<ImageMateData> list = new ArrayList<ImageMateData>();
        Elements link = insDocument.select("img");
        // System.out.println(link);
        for (Element e : link) {

            String imageUri = e.attr("abs:data-original");
            // System.out.println((imageUri));

            String imageDesc = e.attr("alt");
            if (!imageUri.isEmpty()) {
                list.add(new ImageMateData(pageUrl, imageUri, imageDesc));
            }

        }
        return list;
        // Map<String, String> pics = new HashMap<String, String>();
        // Elements link = insDocument.select("img");
        // // System.out.println(link);
        // for (Element e : link) {
        // String imageUri = e.attr("abs:data-original");
        // // System.out.println((imageUri));

        // String imageDesc = e.attr("alt");
        // // System.out.println((imageDesc));
        // pics.put(imageUri, imageDesc);
        // }
        // return pics;
    }

    public boolean IsLastPage(String pageUrl) {
        Document doc;
        try {
            doc = FetchHtmlDocument(pageUrl);
            if (doc.getElementsByClass("pagination").select("li.disabled span").toString().indexOf("下一页") == -1) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
}
