package vn.hoanggiang.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.service.CompanyService;

@RestController
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  } 

  @PostMapping("/companies")
  public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.createCompany(company));
  }
}
