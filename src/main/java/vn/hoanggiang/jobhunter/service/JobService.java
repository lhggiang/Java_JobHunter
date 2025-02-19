package vn.hoanggiang.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.Skill;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.hoanggiang.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.hoanggiang.jobhunter.repository.CompanyRepository;
import vn.hoanggiang.jobhunter.repository.JobRepository;
import vn.hoanggiang.jobhunter.repository.SkillRepository;

@Service
@Slf4j
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository,
            SkillRepository skillRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.companyRepository = companyRepository;
    }

    public Optional<Job> fetchJobById(long id) {
        log.info("Fetch job {} successfully", id);
        return this.jobRepository.findById(id);
    }

    public Job createJob(Job job) {
        // check skills
        if (job.getSkills() != null) {
            List<Long> reqSkills = job.getSkills()
                    .stream().map(Skill::getId)
                    .collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        // check company
        if (job.getCompany() != null) {
            Optional<Company> cOptional = this.companyRepository.findById(job.getCompany().getId());
            if (cOptional.isPresent()) {
                job.setCompany(cOptional.get());
            }
        }

        // create job
        Job currentJob = this.jobRepository.save(job);
        log.info("Create job with {} successfully", job.getName());
        return currentJob;
    }

    public ResCreateJobDTO convertToResCreateJobDTO(Job job) {
        ResCreateJobDTO dto = new ResCreateJobDTO();
        dto.setId(job.getId());
        dto.setName(job.getName());
        dto.setSalary(job.getSalary());
        dto.setQuantity(job.getQuantity());
        dto.setLocation(job.getLocation());
        dto.setLevel(job.getLevel());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setActive(job.isActive());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setCreatedBy(job.getCreatedBy());

        if (job.getSkills() != null) {
            List<String> skills = job.getSkills()
                    .stream().map(Skill::getName)
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }
        return dto;
    }

    public Job updateJob(Job job, Job jobInDB) {

        // check skills
        if (job.getSkills() != null) {
            List<Long> reqSkills = job.getSkills()
                    .stream().map(Skill::getId)
                    .collect(Collectors.toList());

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            jobInDB.setSkills(dbSkills);
        }

        // check company
        if (job.getCompany() != null) {
            Optional<Company> companyOptional = this.companyRepository.findById(job.getCompany().getId());
            companyOptional.ifPresent(jobInDB::setCompany);
        }

        // update correct info
        jobInDB.setName(job.getName());
        jobInDB.setSalary(job.getSalary());
        jobInDB.setQuantity(job.getQuantity());
        jobInDB.setLocation(job.getLocation());
        jobInDB.setLevel(job.getLevel());
        jobInDB.setStartDate(job.getStartDate());
        jobInDB.setEndDate(job.getEndDate());
        jobInDB.setActive(job.isActive());
        jobInDB.setDescription(job.getDescription());

        // update job
        Job currentJob = this.jobRepository.save(jobInDB);
        log.info("Update job with {} successfully", job.getName());
        return currentJob;
    }

    public ResUpdateJobDTO convertToResUpdateJobDTO(Job job) {
        ResUpdateJobDTO dto = new ResUpdateJobDTO();

        dto.setId(job.getId());
        dto.setName(job.getName());
        dto.setSalary(job.getSalary());
        dto.setQuantity(job.getQuantity());
        dto.setLocation(job.getLocation());
        dto.setLevel(job.getLevel());
        dto.setStartDate(job.getStartDate());
        dto.setEndDate(job.getEndDate());
        dto.setActive(job.isActive());
        dto.setUpdatedAt(job.getUpdatedAt());
        dto.setUpdatedBy(job.getUpdatedBy());

        if (job.getSkills() != null) {
            List<String> skills = job.getSkills()
                    .stream().map(Skill::getName)
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }

        return dto;
    }

    public void deleteJob(long id) {
        log.info("Delete job with {} successfully", id);
        this.jobRepository.deleteById(id);
    }

    public ResultPaginationDTO fetchAllJobs(Specification<Job> spec, Pageable pageable) {
        Page<Job> pageUser = this.jobRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageUser.getTotalPages());
        mt.setTotal(pageUser.getTotalElements());
        rs.setMeta(mt);

        rs.setResult(pageUser.getContent());

        log.info("Fetch all jobs successfully");
        return rs;
    }
}
