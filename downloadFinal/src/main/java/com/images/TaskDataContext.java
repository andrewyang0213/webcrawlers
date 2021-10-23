package com.images;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskDataContext {
    public static ConcurrentLinkedQueue<ImageMateData> WaitDownloadQueue = new ConcurrentLinkedQueue<ImageMateData>();

    public static void Init() {
        List<ImageMateData> list = DBHelper.GetImageMateDatasFromDb(0);
        for (ImageMateData data : list) {
            TaskDataContext.Enqueue(data);
        }
    }

    // <summary>
    // 从任务队列 队尾 出队一条数据
    // </summary>
    // <returns></returns>
    public static ImageMateData Dequeue() {
        return WaitDownloadQueue.poll();
    }

    // <summary>
    // 向任务队列 添加一条数据
    // </summary>
    public static void Enqueue(ImageMateData mateData) {
        if (mateData == null) {
            return;
        }
        WaitDownloadQueue.offer(mateData);
    }

    public static boolean checkEmpty() {
        return WaitDownloadQueue.size() == 0;
    }

    public static int Size() {
        return WaitDownloadQueue.size();
    }

}
