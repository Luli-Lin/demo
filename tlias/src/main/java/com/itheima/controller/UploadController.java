package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    private static final String UPLOAD_DIR = "D:/web_ai/";
    /**
     * 上传文件 & 本地存储 - MultipartFile 形参名要和表单名一致，若不一致使用@RequestParam注解进行参数绑定
     */
//    @PostMapping("/upload")
//    public Result upload(String username, Integer age , MultipartFile file) throws Exception {
//        //MultipartFile file用于接收到上传的文件，存放在临时目录
//        log.info("上传文件：{}, {}, {}", username, age, file);
//        //保存接收到的文件
//        if(!file.isEmpty()){
//            // 生成唯一文件名
//            String originalFilename = file.getOriginalFilename();
//            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
//            // 拼接完整的文件路径
//            File targetFile = new File(UPLOAD_DIR + uniqueFileName);
//
//            // 如果目标目录不存在，则创建它
//            if (!targetFile.getParentFile().exists()) {
//                targetFile.getParentFile().mkdirs();
//            }
//            // 保存文件
//            file.transferTo(targetFile);
//        }
//        return Result.success();
//    }


    /**
      云存储
    */
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("上传文件：{}", file);
        if (!file.isEmpty()) {
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + extName;
            // 上传文件
            String url = aliyunOSSOperator.upload(file.getBytes(), uniqueFileName);
            return Result.success(url);
        }
        return Result.error("上传失败");
    }



}