package com.skhkim.instaclone.controller;

import com.skhkim.instaclone.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${instaclone.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile: uploadFiles){

            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("This file is not Image Type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try{
                uploadFile.transferTo(savePath);
                BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
                int imageWidth = bufferedImage.getWidth();
                int imageHeight = bufferedImage.getHeight();
                int cropSize = Math.min(imageWidth, imageHeight);
                BufferedImage croppedImage = Thumbnails.of(bufferedImage)
                        .crop(Positions.CENTER)
                        .size(cropSize, cropSize)
                        .asBufferedImage();
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                Path saveThumbnailPath = Paths.get(thumbnailSaveName);
                File saveThumbnailFile = saveThumbnailPath.toFile();
                ImageIO.write(croppedImage,"png" ,saveThumbnailFile);

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }
    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte  []> getFile(String fileName){
        ResponseEntity<byte[]> result = null;
        try{
            String srcFileName = fileName;//URLDecoder.decode(fileName, "UTF-8");

            File file = new File(uploadPath + File.separator +srcFileName);

            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @GetMapping("/getWidthAndHeight")
    public ResponseEntity<List<Integer>> getWidthAndHeight(String fileName){
        List<Integer> imageInfo = new ArrayList<>();
        try{
            File imageFile = new File(uploadPath + File.separator +fileName);
            BufferedImage image = ImageIO.read(imageFile);
            int width = image.getWidth();
            int height = image.getHeight();
            imageInfo.add(width);
            imageInfo.add(height);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(imageInfo ,HttpStatus.OK);
    }

}
