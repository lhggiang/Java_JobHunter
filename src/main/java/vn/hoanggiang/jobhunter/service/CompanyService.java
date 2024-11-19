package vn.hoanggiang.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository){
    this.companyRepository = companyRepository;
  }

  public Company createCompany(Company company){
    return this.companyRepository.save(company);
  }
}
