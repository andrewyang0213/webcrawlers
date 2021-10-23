package com.images;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        TaskDataContext.Init();

        // ITask fetchTask = new FetchTask();
        // fetchTask.run();

        // ITask downloadTask = new DownloadTask();
        // downloadTask.run();

        int taskCount = 5;
        List<FetchTask> fetchList = new ArrayList<FetchTask>();
        FetchTask fetch = new FetchTask();
        fetch.start();
        fetchList.add(fetch);

        List<DownloadTask> taskList = new ArrayList<DownloadTask>();
        for (int i = 0; i < taskCount; i++) {
            DownloadTask task = new DownloadTask();
            task.setName("Download Thread" + i);
            System.out.println(task);
            task.start();
            taskList.add(task);
        }
    }
}