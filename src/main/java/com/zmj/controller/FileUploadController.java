package com.zmj.controller;

import com.zmj.domain.Result;
import com.zmj.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
   public Result<String> upload(MultipartFile file) throws IOException {
//        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
//        //保证文件名唯一性，防止文件被覆盖
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("C:\\Users\\Lenovo\\Desktop\\testFiles\\"+filename));
        String fileUrl = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(fileUrl);
   }
}
