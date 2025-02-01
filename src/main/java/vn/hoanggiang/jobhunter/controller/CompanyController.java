package vn.hoanggiang.jobhunter.controller;

import java.util.Optional;

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
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.service.CompanyService;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  // create company
  @PostMapping("/companies")
  @ApiMessage("create a new company")
  public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) throws IdInvalidException {
    // check name exists or not
    if (companyService.existsByName(company.getName())) {
      throw new IdInvalidException(
          "Công ty " + company.getName() + " đã tồn tại, vui lòng sử dụng tên khác.");
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(company));
  }

  // get all companies
  @GetMapping("/companies")
  @ApiMessage("fetch all companies")
  public ResponseEntity<ResultPaginationDTO> getAllCompanies(
      @Filter Specification<Company> specification, Pageable pageable) {
    return ResponseEntity.ok(this.companyService.handleGetCompany(specification, pageable));
  }

  // update company
  @PutMapping("/companies")
  @ApiMessage("update a company")
  public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
    return ResponseEntity.ok(this.companyService.handleUpdateCompany(company));
  }

  // delete a company
  @DeleteMapping("/companies/{id}")
  @ApiMessage("delete a company")
  public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
    this.companyService.handleDeleteCompany(id);
    return ResponseEntity.ok(null);
  }

  // fetch company by id
  @GetMapping("/companies/{id}")
  @ApiMessage("fetch company by id")
  public ResponseEntity<Company> fetchCompanyById(@PathVariable long id) {
    Optional<Company> companyOptional = this.companyService.findById(id);
    return ResponseEntity.ok().body(companyOptional.get());
  }
}
