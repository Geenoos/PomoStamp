package io.openvidu.mvc.java;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class FrameController {

    @PostMapping("/capture")
    public ResponseEntity<String> upload( @RequestParam(value = "file",required = true) MultipartFile multipartFile
                                            ,@RequestParam(value="fileName") String fileName){

//        Boolean result = Boolean.FALSE;

        System.out.println(multipartFile);
        System.out.println(fileName);
        String dirPath="C:\\Users\\SSAFY\\Desktop\\imageTest"+"\\"+multipartFile.getOriginalFilename();
        try{
            File folder = new File(dirPath);
            if (!folder.exists()) folder.mkdirs();

            File destination = new File(dirPath + "\\" + fileName);
            multipartFile.transferTo(destination);

//            result = Boolean.TRUE;
        }catch (Exception e){
//            log.error("에러 : " + e.getMessage());
        }

        return new ResponseEntity<>("보내보겠습니다 ㅎㅎ", HttpStatus.OK);
    }
}
