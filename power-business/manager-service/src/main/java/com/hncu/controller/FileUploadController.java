package com.hncu.controller;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.hncu.config.AliyunOSSConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;


/**
 * @Author caimeisahng
 * @Date 2026/3/1 20:37
 * @Version 1.0
 * 文件上传控制层
 */
@Api(tags = "文件上传接口管理")
@RequestMapping("admin/file")
@RestController
public class FileUploadController {

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;


    /**
     * 文件上传:
     * 1.接口要求亲求方式必须是post请求
     * 2.接受文件的类型是MultipartFile:该类型由SpringMVC框架提供
     *
     * @return String类型地址
     * //第三方产品接口调用
     * 读懂文档-->知道直接想要什么-->改造第三方代码
     */
    @ApiOperation("上传单个文件")
    @PostMapping("upload/element")
    public String uploadFIle(MultipartFile file){

        // 填写Bucket名称，例如examplebucket。
        String bucketName = aliyunOSSConfig.getBucketName();
        //以天为单位的名称，作为文件夹名称
        String newFolderName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //以时间戳作为文件的新名称
        String newFileName = DateUtil.format(new Date(), "HHmmssSSS");
        //获取原文件的后缀
        String originalFilename = file.getOriginalFilename();
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = newFolderName + "/" + newFileName + fileSuffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
        URL url = null;
        try {

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,file.getInputStream());
            // 上传
            ossClient.putObject(putObjectRequest);
            //创建文件上传访问的url地址
            url = ossClient.generatePresignedUrl(bucketName, objectName, DateUtil.offsetDay(new Date(),365*10));

        }  catch (Exception ce) {
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url.toString();
    }
}
