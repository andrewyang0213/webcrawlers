package com.images;

import java.nio.file.Path;
import java.nio.file.Paths;

// <summary>
// 下载
// </summary>
public class DownloadTask extends Thread implements ITask {
    private static final Path sysPath = Paths.get("/Users", "andrewyang", "JAVAImages", "DownloadMaster");

    public void run() {
        ISpilder spilder = new DefaultSpilder(sysPath);
        ImageMateData task;
        Boolean stillRunning = true;
        int i = 0;
        do {
            task = TaskDataContext.Dequeue();
            if (task == null) {
                try {
                    Thread.sleep(3000);
                    // sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i++;
                if (i >= 3) {
                    if (TaskDataContext.checkEmpty()) {
                        stillRunning = false;
                        continue;
                    }
                }
                continue;
            }

            System.out.println("Thread：" + Thread.currentThread().getName() + " (" + TaskDataContext.Size() + ")Download Task:"
                    + task.getHash());
            Boolean downloadRes = spilder.Download(task);
            task.Status = downloadRes ? 1 : 2;
            DBHelper.Update(task);
        } while (stillRunning);
        System.out.println("Thread：" + Thread.currentThread().getName() + "Download Complete");
    }
}
