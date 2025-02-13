package vn.hoanggiang.jobhunter.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;

import jakarta.validation.Valid;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import vn.hoanggiang.jobhunter.config.CVProcessor;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.Job;
import vn.hoanggiang.jobhunter.domain.Resume;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.domain.response.resume.ResCreateResumeDTO;
import vn.hoanggiang.jobhunter.domain.response.resume.ResFetchResumeDTO;
import vn.hoanggiang.jobhunter.domain.response.resume.ResUpdateResumeDTO;
import vn.hoanggiang.jobhunter.service.ResumeService;
import vn.hoanggiang.jobhunter.service.UserService;
import vn.hoanggiang.jobhunter.util.SecurityUtil;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    private final FilterBuilder filterBuilder;
    private final FilterSpecificationConverter filterSpecificationConverter;

    public ResumeController(
            ResumeService resumeService,
            UserService userService,
            FilterBuilder filterBuilder,
            FilterSpecificationConverter filterSpecificationConverter) {
        this.resumeService = resumeService;
        this.userService = userService;
        this.filterBuilder = filterBuilder;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    // create a resume
    @PostMapping
    @ApiMessage("create a resume")
    public ResponseEntity<ResCreateResumeDTO> create(@Valid @RequestBody Resume resume) throws IdInvalidException {
        // check id exists
        boolean isIdExist = this.resumeService.checkResumeExistByUserAndJob(resume);
        if (!isIdExist) {
            throw new IdInvalidException("User id hoặc Job id không tồn tại");
        }

        // create new resume
        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.createResume(resume));
    }

    // update a resume
    @PutMapping
    @ApiMessage("update a resume")
    public ResponseEntity<ResUpdateResumeDTO> update(@RequestBody Resume resume) throws IdInvalidException {
        // check id exist or not
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(resume.getId());
        if (reqResumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume với id = " + resume.getId() + " không tồn tại");
        }

        Resume reqResume = reqResumeOptional.get();
        reqResume.setStatus(resume.getStatus());

        return ResponseEntity.ok().body(this.resumeService.updateResume(reqResume));
    }

    // delete a resume by id
    @DeleteMapping("/{id}")
    @ApiMessage("delete a resume by id")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(id);
        if (reqResumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume với id = " + id + " không tồn tại");
        }

        this.resumeService.deleteResume(id);
        return ResponseEntity.ok().body(null);
    }

    // fetch a resume by id
    @GetMapping("/{id}")
    @ApiMessage("fetch a resume by id")
    public ResponseEntity<ResFetchResumeDTO> fetchById(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> reqResumeOptional = this.resumeService.fetchById(id);
        if (reqResumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume với id = " + id + " không tồn tại");
        }

        return ResponseEntity.ok().body(this.resumeService.getResume(reqResumeOptional.get()));
    }

    // fetch all resumes (hrs only fetch resumes of their company)
    @GetMapping
    @ApiMessage("fetch all resume with paginate")
    public ResponseEntity<ResultPaginationDTO> fetchAll(
            @Filter Specification<Resume> spec,
            Pageable pageable) {

        List<Long> arrJobIds = null;
        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        User currentUser = this.userService.handleGetUserByUsername(email);
        if (currentUser != null) {
            Company userCompany = currentUser.getCompany();
            if (userCompany != null) {
                List<Job> companyJobs = userCompany.getJobs();
                if (companyJobs != null && companyJobs.size() > 0) {
                    arrJobIds = companyJobs.stream().map(x -> x.getId())
                            .collect(Collectors.toList());
                }
            }
        }

        Specification<Resume> jobInSpec = filterSpecificationConverter
                .convert(filterBuilder.field("job").in(filterBuilder.input(arrJobIds)).get());

        Specification<Resume> finalSpec = jobInSpec.and(spec);

        return ResponseEntity.ok().body(this.resumeService.fetchAllResume(finalSpec, pageable));
    }

    // get list resumes by user's login info (email)
    @PostMapping("/by-user")
    @ApiMessage("get list resumes by user")
    public ResponseEntity<ResultPaginationDTO> fetchResumeByUser(Pageable pageable) {
        return ResponseEntity.ok().body(this.resumeService.fetchResumeByUser(pageable));
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeCV(@RequestParam("file") MultipartFile file, @RequestParam String jobDesc) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/analyze";

        File tempFile = File.createTempFile("cv_", ".pdf");
        file.transferTo(tempFile);
        String content = CVProcessor.extractText(tempFile);

        // Tạo payload JSON
        Map<String, String> payload = new HashMap<>();
        payload.put("cv_text", content);
        payload.put("job_desc", jobDesc);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return ResponseEntity.ok().body(response);
    }
}