package vn.hoanggiang.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.dto.Meta;
import vn.hoanggiang.jobhunter.domain.dto.ResultPaginationDTO;
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

  public Company updateCompany(Company company){
    Optional<Company> existingCompany = this.companyRepository.findById(company.getId());
    if(existingCompany.isPresent()){
      return this.companyRepository.save(company);
    }
    return null;
  }

  public ResultPaginationDTO getAllCompanies(Pageable pageable){
    Page<Company> pCompany = this.companyRepository.findAll(pageable);

    Meta meta = new Meta();
    meta.setPage(pCompany.getNumber() + 1);
    meta.setPageSize(pCompany.getSize());
    meta.setPages(pCompany.getTotalPages());
    meta.setTotal(pCompany.getTotalElements());

    ResultPaginationDTO result = new ResultPaginationDTO();
    result.setMeta(meta);
    result.setData(pCompany.getContent());

    return result;
  }

  public void deleteCompany(long id){
     this.companyRepository.deleteById(id);
  }
}
