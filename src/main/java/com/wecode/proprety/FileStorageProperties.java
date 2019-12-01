package com.wecode.proprety;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public FileStorageProperties() {
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public FileStorageProperties(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
