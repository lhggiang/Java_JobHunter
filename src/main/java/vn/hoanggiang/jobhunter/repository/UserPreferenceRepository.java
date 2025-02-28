package vn.hoanggiang.jobhunter.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.UserPreference;
import vn.hoanggiang.jobhunter.util.constant.LevelEnum;

import java.util.List;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    @Query("SELECT u FROM UserPreference u WHERE u.minSalary <= :salary AND u.maxSalary >= :salary " +
            "AND u.jobName LIKE %:jobName% " +
            "AND u.level = :level " +
            "AND u.location LIKE %:location%")
    List<UserPreference> findBySalaryBetweenAndJobNameLikeAndLevelAndLocationLike(
            @Param("salary") double salary,
            @Param("jobName") String jobName,
            @Param("level") String level,
            @Param("location") String location);
}
