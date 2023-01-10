package com.customercvarchive.service.impl;

import com.customercvarchive.exception.EntityNotFoundException;
import com.customercvarchive.mapper.FileMapper;
import com.customercvarchive.model.Customer;
import com.customercvarchive.model.File;
import com.customercvarchive.model.dto.FileDto;
import com.customercvarchive.repository.FileRepository;
import com.customercvarchive.service.CustomerService;
import com.customercvarchive.service.FileService;
import com.customercvarchive.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    private final CustomerService customerService;

    @Override
    public File uploadFile(MultipartFile multipartFile, Integer id) throws IOException {
        if (fileByIdOptional(id) == null || fileByIdOptional(id).equals(""))
            throw new EntityNotFoundException("File", id);
        if (customerService.customerByIdOptional(id) == null || customerService.customerByIdOptional(id).equals(""))
            throw new EntityNotFoundException("Customer", id);
        Customer customer = customerService.customerById(id);
        File file = fileRepository.save(File.builder().fileName(multipartFile.getOriginalFilename())
                .fileType(multipartFile.getContentType())
                .fileData(multipartFile.getBytes()).customer(customer).build());


        return fileRepository.save(file);
    }

    @Override
    public byte[] downloadFile(Integer id) {
        if (fileByIdOptional(id) == null || fileByIdOptional(id).equals(""))
            throw new EntityNotFoundException("File", id);
        File fileData = fileById(id);
        byte[] file = FileUtils.decompressFile(fileData.getFileData());

        return file;
    }

    @Override
    public List<FileDto> allFiles() {
        List<File> fileList = fileRepository.findAll();
        List<FileDto> fileDtoList = new ArrayList<>();
        for (File file : fileList) {
            fileDtoList.add(fileMapper.toFileDto(file));
        }

        return fileDtoList;
    }

    @Override
    public Optional<File> fileByIdOptional(Integer id) {
        return fileRepository.findById(id);
    }

    @Override
    public File fileById(Integer id) {
        return fileRepository.getById(id);
    }

    @Override
    public FileDto fileDtoById(Integer id) {
        return fileByIdOptional(id).map(fileMapper::toFileDto).orElseThrow();
    }


    @Override
    public FileDto updateFile(Integer id, FileDto fileDto) {
        if (fileByIdOptional(id) == null || fileByIdOptional(id).equals(""))
            throw new EntityNotFoundException("File", id);

        File file = fileById(id);

        if (!fileDto.getFileName().equals("") || !fileDto.getFileName().equals(null))
            file.setFileName(fileDto.getFileName());
        if (!fileDto.getCustomerId().equals("") || !fileDto.getCustomerId().equals(null))
            file.setCustomer(customerService.customerById(fileDto.getCustomerId()));

        fileRepository.save(file);

        return fileMapper.toFileDto(file);
    }

    @Override
    public void deleteFile(Integer id) {
        if (fileByIdOptional(id) == null || fileByIdOptional(id).equals(""))
            throw new EntityNotFoundException("File", id);
        else
            fileRepository.deleteById(id);
    }
}
