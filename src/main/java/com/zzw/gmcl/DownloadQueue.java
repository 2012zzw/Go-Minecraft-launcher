package com.zzw.gmcl;

import java.util.ArrayList;
import java.util.List;

public class DownloadQueue {
    private static DownloadQueue instance;
    private List<DownloadItem> queue;

    private DownloadQueue() {
        queue = new ArrayList<>();
    }

    public static synchronized DownloadQueue getInstance() {
        if (instance == null) {
            instance = new DownloadQueue();
        }

        return instance;
    }
    public void addItem(String url,String fileAddress,String fileType, String sha1Value, String version) {
        DownloadItem item = new DownloadItem(url,fileAddress, fileType, sha1Value, version);

        queue.add(item);
    }

    public List<DownloadItem> getQueue() {
        return queue;
    }

    static class DownloadItem {
        private String url;
        private String fileAddress;
        private String fileType;
        private String sha1Value;
        private String version;

        public DownloadItem(String url,String fileAddress, String fileType, String sha1Value, String version) {
            this.url = url;
            this.fileAddress = fileAddress;
            this.fileType = fileType;
            this.sha1Value = sha1Value;
            this.version = version;
        }
        public String geturl() {
            return url;
        }
        public String getFileAddress() {
            return fileAddress;
        }

        public String getFileType() {
            return fileType;
        }

        public String getSha1Value() {
            return sha1Value;
        }

        public String getVersion() {
            return version;
        }
    }
}
