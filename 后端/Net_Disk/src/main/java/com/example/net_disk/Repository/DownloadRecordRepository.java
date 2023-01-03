package com.example.net_disk.Repository;

import com.example.net_disk.Entity.DownloadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRecordRepository extends JpaRepository<DownloadRecord, Long> {
}
