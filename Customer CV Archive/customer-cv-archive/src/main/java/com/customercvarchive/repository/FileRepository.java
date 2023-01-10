package com.customercvarchive.repository;

import com.customercvarchive.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface FileRepository extends JpaRepository<File,Integer> {
    File getById(Integer id);
    Optional<File> findByFileName(String fileName);

}
