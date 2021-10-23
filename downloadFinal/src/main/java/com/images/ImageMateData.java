package com.images;

public class ImageMateData {

    public ImageMateData(String sourceUri, String uri, String desc) {
        this.SourceUri = sourceUri;
        this.Uri = uri;
        this.Desc = desc;
    }

    private String ID;
    private String SourceUri;
    private String Uri;
    private String Desc;
    private String Hash;
    int Status;

    public String getID() {
        return ID;
    }

    public String getSourceUri() {
        return SourceUri;
    }

    public String getUri() {
        return Uri;
    }

    public String getDesc() {
        return Desc;
    }

    public String getHash() {
        if (Hash == null || Hash == "") {
            Hash = MD5Utils.stringToMD5(SourceUri + Uri + Desc);
        }
        return Hash;
    }

    public int getStatus() {
        return Status;
    }

    public void setID(String newID) {
        this.ID = newID;
    }

    public void setSourceUri(String newSourceUri) {
        this.SourceUri = newSourceUri;
    }

    public void setUri(String newUri) {
        this.Uri = newUri;
    }

    public void setDesc(String newDesc) {
        this.Desc = newDesc;
    }

    public void setStatus(int newStatus) {
        this.Status = newStatus;
    }
}
