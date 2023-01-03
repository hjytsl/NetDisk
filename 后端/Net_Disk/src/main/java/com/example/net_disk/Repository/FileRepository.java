package com.example.net_disk.Repository;

import com.example.net_disk.Entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File getFileById(Long id);
    List<File> getAllByBelongs(Long belongs);
    List<File> findAllByNameContainingAndBelongs(String filename, Long userId);
    List<File> findAllByPath(String path);
    List<File> findAllByPathAndAndBelongs(String path, Long belongs);
    List<File> findAllByTypeAndBelongs(String type, Long belongs);
}
