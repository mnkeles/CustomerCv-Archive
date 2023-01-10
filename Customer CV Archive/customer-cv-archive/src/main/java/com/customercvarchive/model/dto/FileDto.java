package com.customercvarchive.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    private Integer fileId;

    private String fileName;
    private String fileType;

    private Integer customerId;


}
