package com.osrs.suite.cache;

/**
 * Created by Allen Kinzalow on 3/21/2015.
 */
public class FileRef {

    private int indexId;
    private int archiveId;
    private int fileId;

    public FileRef(int indexId, int archiveId, int fileId) {
        this.indexId = indexId;
        this.archiveId = archiveId;
        this.fileId = fileId;
    }

    public int getIndexId() {
        return indexId;
    }

    public int getArchiveId() {
        return archiveId;
    }

    public int getFileId() {
        return fileId;
    }

    @Override
    public String toString() {
        return "[index=" + this.getIndexId() + ";archive=" + this.getArchiveId() + ";file=" + this.getFileId() + "]";
    }

}
