package vn.hoanggiang.jobhunter.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.hoanggiang.jobhunter.domain.Resume;

import java.time.Instant;
import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long>,
        JpaSpecificationExecutor<Resume> {
    @Query("SELECT COUNT(r) FROM Resume r WHERE r.createdAt BETWEEN :start AND :end")
    long countByCreatedAtBetween(@Param("start") Instant start, @Param("end") Instant end);

    @Query("SELECT r.status, COUNT(r) FROM Resume r GROUP BY r.status")
    List<Object[]> countByAllStatuses();
}