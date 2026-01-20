package com.zmj.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;

import java.io.FileInputStream;
import java.io.InputStream;

public class AliOssUtil {

    private static final String ENDPOINT="";
    private static final String ACCESS_KEY_ID="";
    private static final String ACCESS_KEY_SECRET="";
    private static final String REGION="cn-beijing";
    private static final String BUCKET="";
    private static String execute(
            String key,
            InputStream in) {

        CredentialsProvider provider = new StaticCredentialsProvider(ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(REGION);

        if (ENDPOINT != null) {
            clientBuilder.endpoint(ENDPOINT);
        }

        String url="";

        try (OSSClient client = clientBuilder.build()) {
            // 核心修改：从本地文件创建BinaryData（自动处理文件流）
            BinaryData fileData = BinaryData.fromStream(in);

            PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(BUCKET)
                    .key(key)
                    .body(fileData)
                    .build());
            //url组成：https://bucket名称.区域节点/key
            url="https://"+BUCKET+"."+ENDPOINT+"/"+key;
            System.out.printf("status code:%d, request id:%s, eTag:%s\n",
                    result.statusCode(), result.requestId(), result.eTag());

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //    System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
        return url;
    }

    public static String uploadFile(String objectName, InputStream in) {
        String fileUrl=execute(objectName,in);
        return fileUrl;
    }
}
