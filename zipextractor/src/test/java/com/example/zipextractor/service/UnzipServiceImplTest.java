package com.example.zipextractor.service;

import com.example.zipextractor.service.impl.UnzipServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

class UnzipServiceImplTest {

    @InjectMocks
    UnzipServiceImpl unzipServiceImpl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    public void setup(){
        MockitoAnnotations.openMocks(this);
        unzipServiceImpl = new UnzipServiceImpl();
    }

    @Test
    void unzip() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("zipextractor.zip", new FileInputStream(new File("zipextractor.zip")));

//        MultipartFile multipartFile = new MockMultipartFile("zipextractor.zip", );
        //given
//        ResponseEntity unzipFile = unzipServiceImpl.unzipFile(multipartFile, " /home/ttn/Documents");

        //when


        //then
//        assertEquals(unzipFile.getMessage(), "File extracted at: /home/ttn/Documents");


    }

    @Test
    void whenFileUpload_getMessage() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("zipextractor.zip", new FileInputStream(new File("zipextractor.zip")));

        File file = new File("/home/ttn/IdeaProjects/zipextractor/src/test/java/com/pwc/ttn/zipextractor/service/zipextractor.zip");

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }

//        assertEquals();

    }

    @Test
    void getFileExtension() {
    }

    @Test
    void getParent() {
    }
}