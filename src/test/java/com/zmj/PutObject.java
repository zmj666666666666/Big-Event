package com.zmj;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.FileInputStream;
import java.nio.file.Paths;

public class PutObject{

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key) {
        String ACCESS_KEY_ID="";
        String ACCESS_KEY_SECRET="";

        CredentialsProvider provider = new StaticCredentialsProvider(ACCESS_KEY_ID,ACCESS_KEY_SECRET);
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // 核心修改：从本地文件创建BinaryData（自动处理文件流）
            BinaryData fileData = BinaryData.fromStream(new FileInputStream("C:\\Users\\Lenovo\\Pictures\\下载\\QQ截图20221026170642.png"));

            PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .body(fileData)
                    .build());

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
    }

    public static void main(String[] args) {
        execute("","","","001.png");
    }
}
