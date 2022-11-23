package com.ZxYz.controller;

import com.ZxYz.domain.ResponseResult;
import com.ZxYz.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "上传接口",description = "上传相关接口")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "头像上传")
    public ResponseResult<?> uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }

}
