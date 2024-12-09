package com.kadir.common.controller.impl;

import com.amazonaws.services.s3.model.Bucket;
import com.kadir.common.controller.IBucketController;
import com.kadir.common.service.IBucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/bucket")
public class BucketController implements IBucketController {

    @Autowired
    IBucketService bucketService;

    @GetMapping("/list")
    @Override
    public List<Bucket> getBucketList() {
        return bucketService.getBucketList();
    }

    @Override
    public boolean validateBucket(String bucketName) {
        return bucketService.validateBucket(bucketName);
    }

    @GetMapping("/download/{fileName}")
    @Override
    public byte[] downloadFile(@PathVariable(name = "fileName") String fileName) {
        return bucketService.downloadFile(fileName);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Override
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return bucketService.uploadFile(file);
    }

    @Override
    public void createBucket(String bucket) {
        bucketService.createBucket(bucket);
    }
}
