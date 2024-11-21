package vn.hoanggiang.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.service.CompanyService;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  } 

  @PostMapping
  public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.createCompany(company));
  }

  @GetMapping
  public ResponseEntity<ResultPaginationDTO> getAllCompanies(
    @Filter Specification<Company> specification, Pageable pageable
  ) {
    return ResponseEntity.ok(this.companyService.getAllCompanies(specification, pageable));
  }

  @PutMapping
  public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
    return ResponseEntity.ok(this.companyService.createCompany(company));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
    this.companyService.deleteCompany(id);
    return ResponseEntity.ok(null);
  }
}
