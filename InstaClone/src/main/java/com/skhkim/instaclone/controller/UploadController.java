package com.skhkim.instaclone.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
@Log4j2
public class UploadController {

    @Value("/Users/gimseonghun/Desktop/")
    private String uploadPath;
    @GetMapping("/display")
    public ResponseEntity<byte  []> getFile(String fileName){
        ResponseEntity<byte[]> result = null;
        log.info("First Name : " + fileName);
        try{
            String srcFileName = fileName;//URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName : " + srcFileName);

            File file = new File(uploadPath + File.separator +srcFileName);

            log.info("file : " + file);

            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
