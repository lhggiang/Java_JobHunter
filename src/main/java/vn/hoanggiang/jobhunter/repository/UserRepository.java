package vn.hoanggiang.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoanggiang.jobhunter.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
}