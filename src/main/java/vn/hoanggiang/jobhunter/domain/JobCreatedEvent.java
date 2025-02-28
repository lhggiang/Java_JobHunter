package vn.hoanggiang.jobhunter.domain;

import java.io.Serializable;
import java.time.Instant;

import java.io.Serializable;
import java.time.Instant;

public record JobCreatedEvent(
        Long jobId,
        String name,
        String companyName,
        String location,
        Double salary,
        String level,
        Instant createdAt
) implements Serializable {
    public JobCreatedEvent(Job job) {
        this(
                job.getId(),
                job.getName(),
                job.getCompany().getName(),
                job.getLocation(),
                job.getSalary(),
                job.getLevel().name(),
                job.getCreatedAt()
        );
    }
}
