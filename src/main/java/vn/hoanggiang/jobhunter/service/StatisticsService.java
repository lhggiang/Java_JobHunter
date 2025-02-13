package vn.hoanggiang.jobhunter.service;

import org.springframework.stereotype.Service;
import vn.hoanggiang.jobhunter.repository.*;
import vn.hoanggiang.jobhunter.util.constant.ResumeStateEnum;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final SubscriberRepository subscriberRepository;
    private final CompanyRepository companyRepository;

    public StatisticsService(JobRepository jobRepository,
                             ResumeRepository resumeRepository,
                             SubscriberRepository subscriberRepository,
                             CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.resumeRepository = resumeRepository;
        this.subscriberRepository = subscriberRepository;
        this.companyRepository = companyRepository;
    }

    public Map<String, Long> getDailyStats(LocalDate date) {
        //Ensures to fetch createdAt in the day without being affected by time zone or data type errors
        Instant start = date.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().minusNanos(1);

        return Map.of(
                "jobs", jobRepository.countByCreatedAtBetween(start, end),
                "resumes", resumeRepository.countByCreatedAtBetween(start, end),
                "subscribers", subscriberRepository.countByCreatedAtBetween(start, end)
        );
    }

    public Map<String, Long> getMonthlyStats(int year, int month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());

        Instant start = firstDay.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = lastDay.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().minusNanos(1);

        return Map.of(
                "jobs", jobRepository.countByCreatedAtBetween(start, end),
                "resumes", resumeRepository.countByCreatedAtBetween(start, end),
                "subscribers", subscriberRepository.countByCreatedAtBetween(start, end)
        );
    }

    public Map<String, Long> getYearlyStats(int year) {
        LocalDate firstDay = LocalDate.of(year, 1, 1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfYear());

        Instant start = firstDay.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = lastDay.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant().minusNanos(1);

        return Map.of(
                "jobs", jobRepository.countByCreatedAtBetween(start, end),
                "resumes", resumeRepository.countByCreatedAtBetween(start, end),
                "subscribers", subscriberRepository.countByCreatedAtBetween(start, end)
        );
    }

    public Map<String, Long> getSubscribersBySkill() {
        return subscriberRepository.countSubscribersBySkill().stream()
                .collect(Collectors.toMap(
                        e -> "subscriber_" + (String) e[0],
                        e -> (Long) e[1]
                ));
    }

    public Map<String, Long> getOverallStatistics() {
        Map<String, Long> statistics = new LinkedHashMap<>();

        statistics.put("totalCompanies", companyRepository.count());
        statistics.put("totalJobs", jobRepository.count());
        statistics.put("totalSubscribers", subscriberRepository.count());

        // Initialize all CV states with value 0
        for (ResumeStateEnum status : ResumeStateEnum.values()) {
            statistics.put("cv_" + status.name(), 0L);
        }

        // Get actual data from database
        List<Object[]> resumeStats = resumeRepository.countByAllStatuses();
        for (Object[] result : resumeStats) {
            ResumeStateEnum status = (ResumeStateEnum) result[0];
            Long count = (Long) result[1];
            statistics.put("cv_" + status.name(), count);
        }

        return statistics;
    }


}
