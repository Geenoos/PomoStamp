package com.ssafy.rpomos.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/pomos/v1/frame")
public class FrameController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam(value = "file",required = true) MultipartFile multipartFile
            , @RequestParam(value="fileName") String fileName){


        System.out.println(multipartFile);
        System.out.println(fileName);
        String dirPath="C:\\Users\\SSAFY\\Desktop\\imageTest"+"\\"+multipartFile.getOriginalFilename();

        try{

            File folder = new File(dirPath);
            if (!folder.exists()) folder.mkdirs();

            File destination = new File(dirPath + "\\" + fileName);
            multipartFile.transferTo(destination);

        }catch (Exception e){
            return new ResponseEntity<>("에러가 발생했습니다",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("보내보겠습니다 ㅎㅎ", HttpStatus.OK);
    }

}
