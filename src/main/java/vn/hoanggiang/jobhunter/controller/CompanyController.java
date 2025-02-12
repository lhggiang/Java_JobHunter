package vn.hoanggiang.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.service.CompanyService;
import vn.hoanggiang.jobhunter.util.annotation.ApiMessage;
import vn.hoanggiang.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  // create company
  @PostMapping
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
  @GetMapping
  @ApiMessage("fetch all companies")
  public ResponseEntity<ResultPaginationDTO> getAllCompanies(
      @Filter Specification<Company> specification, Pageable pageable) {
    return ResponseEntity.ok(this.companyService.handleGetCompany(specification, pageable));
  }

  // update company
  @PutMapping
  @ApiMessage("update a company")
  public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
    return ResponseEntity.ok(this.companyService.handleUpdateCompany(company));
  }

  // delete a company
  @DeleteMapping("/{id}")
  @ApiMessage("delete a company")
  public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
    this.companyService.handleDeleteCompany(id);
    return ResponseEntity.ok(null);
  }

  // fetch company by id
  @GetMapping("/{id}")
  @ApiMessage("fetch company by id")
  public ResponseEntity<Company> fetchCompanyById(@PathVariable long id) {
    Optional<Company> companyOptional = this.companyService.findById(id);
    return ResponseEntity.ok().body(companyOptional.get());
  }

  // save viewed company
  @PostMapping("/view/{companyId}")
  @ApiMessage("save viewed company")
  public ResponseEntity<Void> saveViewedCompany(@RequestParam Long userId, @PathVariable Long companyId) {
    companyService.saveCompanyView(userId, companyId);
    return ResponseEntity.ok(null);
  }

  // get suggested companies
  @GetMapping("/suggestions")
  @ApiMessage("get suggested companies")
  public ResponseEntity<ResultPaginationDTO> getCompanySuggestions(@RequestParam Long userId, Pageable pageable) {
    return ResponseEntity.ok(companyService.suggestSimilarCompanies(userId, pageable));
  }
}
