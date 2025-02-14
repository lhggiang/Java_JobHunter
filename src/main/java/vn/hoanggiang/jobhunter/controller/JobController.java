package vn.hoanggiang.jobhunter.controller;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.hoanggiang.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.hoanggiang.jobhunter.service.JobService;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @ApiMessage("create a job")
    public ResponseEntity<ResCreateJobDTO> createJob(@Valid @RequestBody Job job) {
        Job currentJob = this.jobService.createJob(job);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.jobService.convertToResCreateJobDTO(currentJob));
    }

    @PutMapping
    @ApiMessage("update a job")
    public ResponseEntity<ResUpdateJobDTO> updateJob(@Valid @RequestBody Job job) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(job.getId());
        if (currentJob.isEmpty()) {
            throw new IdInvalidException("Job with id = " + job.getId() + " does not exist.");
        }
        Job updatedJob = this.jobService.updateJob(job, currentJob.get());
        return ResponseEntity.ok()
                .body(this.jobService.convertToResUpdateJobDTO(updatedJob));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("delete a job by id")
    public ResponseEntity<Void> deleteJob(@PathVariable long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (currentJob.isEmpty()) {
            throw new IdInvalidException("Job with id = " + id + " does not exist.");
        }
        this.jobService.deleteJob(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}")
    @ApiMessage("fetch a job by id")
    public ResponseEntity<Job> getJob(@PathVariable long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (currentJob.isEmpty()) {
            throw new IdInvalidException("Job with id = " + id + " does not exist.");
        }
        return ResponseEntity.ok().body(currentJob.get());
    }

    @GetMapping
    @ApiMessage("fetch all jobs with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(
            @Filter Specification<Job> spec,
            Pageable pageable) {
        return ResponseEntity.ok().body(this.jobService.fetchAllJobs(spec, pageable));
    }

}
