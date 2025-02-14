package vn.hoanggiang.jobhunter.service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.repository.CompanyRepository;
import vn.hoanggiang.jobhunter.repository.UserRepository;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

@Service
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String HISTORY_PREFIX = "history:";

    public CompanyService(
            CompanyRepository companyRepository,
            UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company handleCreateCompany(Company company) {
        log.info("Create job with {} successfully", company.getName());
        return this.companyRepository.save(company);
    }

    public Company handleUpdateCompany(Company c) {
        // check company exists or not
        Optional<Company> companyOptional = this.companyRepository.findById(c.getId());
        if (companyOptional.isPresent()) {
            // update company
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(c.getLogo());
            currentCompany.setName(c.getName());
            currentCompany.setIndustry(c.getIndustry());
            currentCompany.setDescription(c.getDescription());
            currentCompany.setAddress(c.getAddress());

            log.info("Update job with {} successfully", currentCompany.getName());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

    public void handleDeleteCompany(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();

            // fetch all users belong to a company
            List<User> users = com.getUsers();

            this.userRepository.deleteAll(users);
        }

        log.info("Delete job with {} successfully", id);
        this.companyRepository.deleteById(id);
    }

    public ResultPaginationDTO handleGetCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());

        log.info("Fetch all jobs with successfully");
        return rs;
    }

    public Optional<Company> findById(long id) {
        log.info("Fetch job with {} successfully", id);
        return this.companyRepository.findById(id);
    }

    public boolean existsByName(String name) {
        return this.companyRepository.existsByName(name);
    }

    // save viewed company's ID
    public void saveCompanyView(Long userId, Long companyId) {
        String key = HISTORY_PREFIX + userId;
        redisTemplate.opsForSet().add(key, companyId);
        redisTemplate.expire(key, 7, TimeUnit.DAYS); // Lưu trong 7 ngày
    }

    // get viewed companies
    public Set<Object> getViewedCompanies(Long userId) {
        String key = HISTORY_PREFIX + userId;
        return redisTemplate.opsForSet().members(key);
    }

    // get suggested companies
    public ResultPaginationDTO suggestSimilarCompanies(Long userId, Pageable pageable) {
        Set<Object> viewedCompanyIds = getViewedCompanies(userId);

        if (viewedCompanyIds.isEmpty()) {
            // Return an empty DTO instead of a list
            return new ResultPaginationDTO();
        }

        // Retrieve the list of viewed companies
        List<Company> viewedCompanies = companyRepository.findAllById(viewedCompanyIds.stream()
                .map(id -> Long.parseLong(id.toString()))
                .collect(Collectors.toList()));

        Set<Company> suggestedCompanies = new HashSet<>();
        for (Company company : viewedCompanies) {
            // Suggest similar companies
            List<Company> similarCompanies = companyRepository.findByIndustryAndAddress(
                    company.getIndustry(), company.getAddress());
            suggestedCompanies.addAll(similarCompanies);
        }

        // Convert the list into ResultPaginationDTO
        List<Company> suggestedList = new ArrayList<>(suggestedCompanies);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), suggestedList.size());

        List<Company> pageContent = suggestedList.subList(start, end);
        Page<Company> pageCompany = new PageImpl<>(pageContent, pageable, suggestedList.size());

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());
        return rs;
    }
}