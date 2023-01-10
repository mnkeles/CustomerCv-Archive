package com.customercvarchive.mapper;

import com.customercvarchive.model.File;
import com.customercvarchive.model.dto.FileDto;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public File toFile(FileDto fileDto){
        return File.builder().fileId(fileDto.getFileId())
                .fileName(fileDto.getFileName()).fileType(fileDto.getFileType())
                .build();
    }

    public FileDto toFileDto(File file){
        return FileDto.builder().fileId(file.getFileId())
                .fileName(file.getFileName()).fileType(file.getFileType())
                .customerId(file.getCustomer().getCustomerId()).build();
    }
}
