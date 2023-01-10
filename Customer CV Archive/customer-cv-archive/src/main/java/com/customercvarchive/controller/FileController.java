package com.customercvarchive.controller;

import com.customercvarchive.model.Customer;
import com.customercvarchive.model.File;
import com.customercvarchive.model.dto.FileDto;
import com.customercvarchive.service.CustomerService;
import com.customercvarchive.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    @GetMapping("/getAll")
    public ResponseEntity allFiles() {
        log.info("get file list");
        return new ResponseEntity(fileService.allFiles(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getFileById(@PathVariable Integer id) {
        log.info("get file by id: {}", id);
        return new ResponseEntity(fileService.fileDtoById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateFile(@PathVariable Integer id, @RequestBody FileDto fileDto) {
        log.info("update file by id:{}",id);
        return new ResponseEntity<>(fileService.updateFile(id, fileDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFile(@PathVariable Integer id) {
        log.info("delte file by id:{}",id);
        fileService.deleteFile(id);
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                        @RequestParam("id") Integer id) throws IOException {
        // Integer id = 1;
        File file = fileService.uploadFile(multipartFile, id);

        log.info("upload file ");
        return new ResponseEntity(file.getFileName(), HttpStatus.OK);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) {
        File file = fileService.fileById(id);
        byte[] fileData = fileService.downloadFile(id);

        log.info("download file by id:{}",id);
        return ResponseEntity.status(HttpStatus.OK)
                // .contentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                // Bu formatta kullanıldığında sadece bir dosya tipi için geçerli oluyordu,aşağıdaki gibi kullanılırsa bütün dosya türleri
                //için geçerli olacak
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .body(fileData);
    }


}
