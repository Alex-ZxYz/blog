package com.ZxYz.service;


import com.ZxYz.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseResult<?> uploadImg(MultipartFile img);
}
