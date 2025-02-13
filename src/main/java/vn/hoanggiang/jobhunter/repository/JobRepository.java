package vn.hoanggiang.jobhunter.repository;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.Skill;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {
    List<Job> findBySkillsIn(List<Skill> skills);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.createdAt BETWEEN :start AND :end")
    long countByCreatedAtBetween(@Param("start") Instant start, @Param("end") Instant end);
}