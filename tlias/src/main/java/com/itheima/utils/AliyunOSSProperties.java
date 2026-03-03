package com.itheima.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")//指定封装的是yml哪一个前缀的值
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}