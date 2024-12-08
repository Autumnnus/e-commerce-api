package com.kadir.modules.productimage.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.service.impl.AuthenticationServiceImpl;
import com.kadir.common.utils.merge.MergeUtils;
import com.kadir.modules.product.repository.ProductRepository;
import com.kadir.modules.productimage.dto.ProductImageCreateDto;
import com.kadir.modules.productimage.dto.ProductImageDto;
import com.kadir.modules.productimage.dto.ProductImageUpdateDto;
import com.kadir.modules.productimage.model.ProductImage;
import com.kadir.modules.productimage.repository.ProductImageRepository;
import com.kadir.modules.productimage.service.IProductImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println("Error converting multipartFile to file");
        }
        return convertedFile;
    }

    private String getFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    @Override
    public ProductImageDto createProductImage(MultipartFile file, ProductImageCreateDto productImageCreateDto) {
        productRepository.findById(productImageCreateDto.getProductId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        ProductImage productImage = modelMapper.map(productImageCreateDto, ProductImage.class);

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = getFileName(file.getOriginalFilename());

        s3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
        fileObj.delete();
        String imageUrl = s3Client.getUrl(bucket, fileName).toExternalForm();

        productImage.setImageUrl(imageUrl);
        ProductImage savedProductImage = productImageRepository.save(productImage);
        return modelMapper.map(savedProductImage, ProductImageDto.class);
    }

    @Override
    public ProductImageDto updateProductImage(MultipartFile file, ProductImageUpdateDto productImageUpdateDto) {
        ProductImage existingImage = productImageRepository.findById(productImageUpdateDto.getId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product Image not found")));

        if (file != null) {
            String oldFileName = existingImage.getImageUrl().substring(existingImage.getImageUrl().lastIndexOf("/") + 1);
            s3Client.deleteObject(bucket, oldFileName);

            File fileObj = convertMultiPartFileToFile(file);
            String fileName = getFileName(file.getOriginalFilename());
            s3Client.putObject(new PutObjectRequest(bucket, fileName, fileObj));
            fileObj.delete();
            String imageUrl = s3Client.getUrl(bucket, fileName).toExternalForm();
            existingImage.setImageUrl(imageUrl);
        }
        MergeUtils.copyNonNullProperties(productImageUpdateDto, existingImage);
        ProductImage updatedImage = productImageRepository.save(existingImage);

        return modelMapper.map(updatedImage, ProductImageDto.class);
    }


    @Override
    public ProductImageDto deleteProductImage(Long productImageId) {
        ProductImage existingImage = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product Image not found")));

        String fileName = existingImage.getImageUrl().substring(existingImage.getImageUrl().lastIndexOf("/") + 1);
        try {
            s3Client.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }

        productImageRepository.delete(existingImage);

        return modelMapper.map(existingImage, ProductImageDto.class);
    }


    @Override
    public List<ProductImageDto> getProductImages(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Product not found")));
        List<ProductImage> productImages = productImageRepository.findByProductId(productId);
        return modelMapper.map(productImages, List.class);
    }
}
