package com.xq.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.xq.domain.ResponseResult;
import com.xq.enums.AppHttpCodeEnum;
import com.xq.exception.SystemException;
import com.xq.service.UploadService;
import com.xq.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Data
@Service("uploadService")
@ConfigurationProperties(prefix = "oss")
public class OssUploadService implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        String originalFilename = img.getOriginalFilename();


        // 对原始文件名进行判断
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith((".jpg"))&& !originalFilename.endsWith((".jpeg"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 如果判断通过，则上传文件到oss中
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = upLoadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    private String upLoadOss(MultipartFile imgFile, String filePath) {
        String objectName = filePath;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = imgFile.getInputStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            return "https://picturebed-dxq.oss-cn-hangzhou.aliyuncs.com/" + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "www";
    }
}
