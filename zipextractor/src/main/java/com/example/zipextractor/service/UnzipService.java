package com.example.zipextractor.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface UnzipService {

  List<File> unzipFile(MultipartFile zipFile) throws IOException;

  Map<String, List<File>> unzipWithMap(MultipartFile zipFile) throws IOException;

}
