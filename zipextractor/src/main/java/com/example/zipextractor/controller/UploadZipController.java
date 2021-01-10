package com.example.zipextractor.controller;

import com.example.zipextractor.service.impl.UnzipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class UploadZipController {

  @Autowired
  private UnzipServiceImpl unzipServiceImpl;

  @PostMapping("/unzipFile")
  public List<File> unzip(@RequestBody MultipartFile zipFile) throws IOException {
    return unzipServiceImpl.unzipFile(zipFile);
  }

  @PostMapping("/unzipMap")
  public Map<String, List<File>> unzipMap(@RequestBody MultipartFile zipFile) throws IOException {
    return unzipServiceImpl.unzipWithMap(zipFile);
  }
}
