package com.example.projectdemo.service;

import com.example.projectdemo.entity.pojo.FileUploadPojo;
import org.springframework.web.multipart.MultipartFile;

public interface FileUpdateService {

  /**
   * 文件上传服务
   * 
   * @param subsystem 指代进行文件上传的子系统信息，子系统将单独生成一个文件夹。利于管理
   * @param file 文件系统
   * @return
   * @throws IllegalArgumentException
   */
   FileUploadPojo fileUpload(String subsystem, MultipartFile file)
      throws IllegalArgumentException;
}
