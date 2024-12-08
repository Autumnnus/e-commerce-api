package com.kadir.common.controller;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBucketController {

    List<Bucket> getBucketList();

    boolean validateBucket(String bucketName);

    byte[] downloadFile(String fileName);

    String uploadFile(MultipartFile file);

    void createBucket(String bucket);
}
