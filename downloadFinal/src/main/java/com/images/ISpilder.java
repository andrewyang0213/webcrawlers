package com.images;

import java.util.*;

interface ISpilder {
    // <summary>
    // 从网址抓取图片原信息
    // </summary>
    // <returns></returns>
    public List<ImageMateData> FetchMates(String pageUrl);

    // <summary>
    // 下载图片
    // </summary>
    // <param name="mateData"></param>
    // <returns>是否成功</returns>
    public Boolean Download(ImageMateData mateData);

    public boolean IsLastPage(String url);
}
