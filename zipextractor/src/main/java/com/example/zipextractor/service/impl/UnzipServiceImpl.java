package com.example.zipextractor.service.impl;

import static com.example.zipextractor.utils.ApplicationConstants.DESTINATION;
import static com.example.zipextractor.utils.ApplicationConstants.FILE_PATH_SEPARATORS;

import com.example.zipextractor.service.UnzipService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.lingala.zip4j.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UnzipServiceImpl implements UnzipService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnzipServiceImpl.class);

  String folderDestination;

  @Override
  public List<File> unzipFile(MultipartFile zipFile) throws IOException {
    if (zipFile.isEmpty()) {
      throw new IOException("No File Selected");
    } else if (!getFileExtension(zipFile.getOriginalFilename()).equals("zip")) {
      throw new IOException("Not a zip file");
    } else {
      return unzip(zipFile);
    }
  }

  @Override
  public Map<String, List<File>> unzipWithMap(MultipartFile zipFile) throws IOException {
    unzip(zipFile);
    String destinationPath = folderDestination;
    File file = new File(destinationPath);

    Map<String, List<File>> map = new HashMap<>();
    for (File fileIterator : Objects.requireNonNull(file.listFiles())
    ) {
      if (fileIterator.isDirectory()) {
        map.put(fileIterator.getName(),
            Arrays.asList(Objects.requireNonNull(fileIterator.listFiles())));
      }
    }
    return map;
  }

  public static String getFileExtension(String fullName) {
    String fileName = new File(fullName).getName();
    int dotIndex = fileName.lastIndexOf('.');
    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
  }

  private List<File> unzip(MultipartFile srcFile) throws IOException {
    String destinationPath = DESTINATION;
    File newFile = convertMultiPartToFile(srcFile);
    ZipFile zipFile = new ZipFile(newFile);
    String zipFolderName = zipFile.toString();
    if (zipFolderName.indexOf(".") > 0) {
      zipFolderName = zipFolderName.substring(0, zipFolderName.lastIndexOf("."));
    }
    folderDestination = destinationPath + FILE_PATH_SEPARATORS + zipFolderName;
    File directoryName = new File(folderDestination);
    boolean bool = directoryName.mkdir();
    if (bool) {
      LOGGER.info("Directory created successfully");
    } else {
      LOGGER.warn("Directory already exists or error creating directory");
    }
    try {
      zipFile.extractAll(folderDestination);
    } catch (Exception e) {
      LOGGER.error(String.valueOf(e));
    }
    File[] fileLists = directoryName.listFiles();
    assert fileLists != null;
    List<File> newFileList = new ArrayList<>(Arrays.asList(fileLists));
    List<File> pdfFile = new ArrayList<>();
    for (File fileIterator : newFileList) {
      if (fileIterator.isDirectory()) {
        File[] files = fileIterator.listFiles();
        pdfFile.addAll(Arrays.asList(files));
      }
    }
    return pdfFile;
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}
