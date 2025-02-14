package vn.hoanggiang.jobhunter.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.hoanggiang.jobhunter.domain.Subscriber;

import java.time.Instant;
import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>,
    JpaSpecificationExecutor<Subscriber> {
  Subscriber findByEmail(String email);
  boolean existsByEmail(String email);

  @Query("SELECT COUNT(s) FROM Subscriber s WHERE s.createdAt BETWEEN :start AND :end")
  long countByCreatedAtBetween(@Param("start") Instant start, @Param("end") Instant end);

  @Query("SELECT s.name, COUNT(sub) FROM Skill s JOIN s.subscribers sub GROUP BY s.name")
  List<Object[]> countSubscribersBySkill();
}
