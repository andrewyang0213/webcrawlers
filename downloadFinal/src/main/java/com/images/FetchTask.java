package com.images;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;

// <summary>
// 从网页抓取原数据
// </summary>
public class FetchTask extends Thread implements ITask {
    static String url = "http://www.bizhi88.com/c1/";
    private static final Path sysPath = Paths.get("/Users", "andrewyang", "JAVAImages", "DownloadMaster");
    String baseUrl = url;

    public void run() {
        ISpilder spilder = new DefaultSpilder(sysPath);
        int i = DBHelper.ProgressChecker();
        do {
            baseUrl = url + i + ".html";
            System.out.println("正在下载网页： " + baseUrl);
            List<ImageMateData> list = spilder.FetchMates(baseUrl);
            for (ImageMateData item : list) {
                if (DBHelper.Exists(item)) {
                    continue;
                } else {
                    DBHelper.Insert(item);
                    TaskDataContext.Enqueue(item);
                }
            }
            i++;
        } while (spilder.IsLastPage(baseUrl));
        // 从db判断有没有记录
        // 有 return
        // 没有 入库，添加到 TaskDataContext
    }
}
