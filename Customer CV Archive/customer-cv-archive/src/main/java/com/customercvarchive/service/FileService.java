package com.customercvarchive.service;

import com.customercvarchive.model.File;
import com.customercvarchive.model.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {

    File uploadFile(MultipartFile multipartFile, Integer id) throws IOException;

    byte[] downloadFile(Integer id);

    List<FileDto> allFiles();

    Optional<File> fileByIdOptional(Integer id);

    File fileById(Integer id);

    FileDto fileDtoById(Integer id);

    //FileDto createFile(FileDto fileDto);

    FileDto updateFile(Integer id, FileDto fileDto);

    void deleteFile(Integer id);
}
