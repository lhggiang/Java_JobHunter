package vn.hoanggiang.jobhunter.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoanggiang.jobhunter.domain.Company;
import vn.hoanggiang.jobhunter.domain.Role;
import vn.hoanggiang.jobhunter.domain.User;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.repository.UserRepository;
import vn.hoanggiang.jobhunter.util.constant.GenderEnum;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private RoleService roleService;

    private User request;

    private Company company;
    private User user;
    private Role role;

    @BeforeEach
    void initData() {
        company = new Company();
        company.setId(4L);

        role = new Role();
        role.setId(1L);

        request = User.builder()
                .name("Hoàng Giang")
                .email("hg128@gmail.com")
                .gender(GenderEnum.FEMALE)
                .password("123456")
                .address("FEMALE")
                .age(20)
                .company(company)
                .role(role)
                .build();

        user = User.builder()
                .id(10)
                .name("Hoàng Giang")
                .email("hg128@gmail.com")
                .gender(GenderEnum.FEMALE)
                .password("123456")
                .address("FEMALE")
                .age(20)
                .company(company)
                .role(role)
                .build();
    }

    // ----------------- Test cho CREATE User ----------------- //

    @Test
    void createUser_validRequest_success(){
        // GIVEN
        when(companyService.findById(company.getId())).thenReturn(Optional.of(company));
        when(roleService.fetchById(role.getId())).thenReturn(role);

        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var result = userService.handleCreateUser(request);

        // THEN
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10L);
        Assertions.assertThat(result.getCompany()).isEqualTo(company);
        Assertions.assertThat(result.getRole()).isEqualTo(role);
    }

    @Test
    void createUser_roleNotFoundById_thenFetchByName_success() {
        // GIVEN: When the role is not found by id, the system will use fetchByName
        Role roleRequest = new Role();
        roleRequest.setId(2L);
        roleRequest.setName("USER");

        User req = User.builder()
                .name("Hoang Giang")
                .email("hoanggiang@gmail.com")
                .company(company)
                .role(roleRequest)
                .build();

        when(companyService.findById(company.getId())).thenReturn(Optional.of(company));
        // Emulator cannot find role by id
        when(roleService.fetchById(roleRequest.getId())).thenReturn(null);
        // Search by name returns valid roles
        when(roleService.fetchByName(roleRequest.getName())).thenReturn(roleRequest);

        User savedUser = User.builder()
                .id(11L)
                .name("Hoang Giang")
                .email("hoanggiang@gmail.com")
                .company(company)
                .role(roleRequest)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // WHEN:
        User result = userService.handleCreateUser(req);

        // THEN:
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(11L);
        Assertions.assertThat(result.getCompany()).isEqualTo(company);
        Assertions.assertThat(result.getRole()).isEqualTo(roleRequest);
    }

    @Test
    void createUser_companyNotFound_success() {
        // GIVEN: User has company but not found in system (Optional.empty)
        Company notFoundCompany = new Company();
        notFoundCompany.setId(100L);

        User req = User.builder()
                .name("Hoàng Giang")
                .email("hoanggiang@gmail.com")
                .company(notFoundCompany)
                .role(role)
                .build();

        // Company not found
        when(companyService.findById(notFoundCompany.getId())).thenReturn(Optional.empty());
        // Role found by id
        when(roleService.fetchById(role.getId())).thenReturn(role);

        User savedUser = User.builder()
                .id(13L)
                .name("Hoàng Giang")
                .email("hoanggiang@gmail.com")
                .company(null) // Company not found so set to null
                .role(role)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // WHEN:
        User result = userService.handleCreateUser(req);

        // THEN:
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(13L);
        Assertions.assertThat(result.getCompany()).isNull();
        Assertions.assertThat(result.getRole()).isEqualTo(role);
    }

    @Test
    void createUser_nullCompanyAndRole_success() {
        // GIVEN: User has no company and role
        User req = User.builder()
                .name("Hoàng Giang")
                .email("hoanggiang@gmail.com")
                .build();

        User savedUser = User.builder()
                .id(12L)
                .name("Hoàng Giang")
                .email("hoanggiang@gmail.com")
                .company(null)
                .role(null)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // WHEN:
        User result = userService.handleCreateUser(req);

        // THEN:
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(12L);
        Assertions.assertThat(result.getCompany()).isNull();
        Assertions.assertThat(result.getRole()).isNull();
    }

    // ----------------- Test cho Update User ----------------- //

    @Test
    void updateUser_validRequest_success() {
        // GIVEN: Create a request with an existing id and update the new information
        User updateRequest = User.builder()
                .id(10)
                .name("Giang Hoang")
                .email("hg128@gmail.com")
                .gender(GenderEnum.MALE)
                .password("123456")
                .address("HN")
                .age(25)
                .company(company)
                .role(role)
                .build();

        // Stub: Simulate the fetchUserById function that returns the current user
        when(userRepository.findById(updateRequest.getId())).thenReturn(Optional.of(user));
        // Stub: Simulate successful company and role search
        when(companyService.findById(company.getId())).thenReturn(Optional.of(company));
        when(roleService.fetchById(role.getId())).thenReturn(role);

        //On save, repository returns updated (simulated) object
        User updatedUser = User.builder()
                .id(10)
                .name("Giang Hoang")
                .email("hg128@gmail.com")
                .gender(GenderEnum.MALE)
                .password("123456")
                .address("HN")
                .age(25)
                .company(company)
                .role(role)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // WHEN
        User result = userService.handleUpdateUser(updateRequest);

        // THEN
        Assertions.assertThat(result.getName()).isEqualTo("Giang Hoang");
        Assertions.assertThat(result.getGender()).isEqualTo(GenderEnum.MALE);
        Assertions.assertThat(result.getAddress()).isEqualTo("HN");
        Assertions.assertThat(result.getAge()).isEqualTo(25);
        Assertions.assertThat(result.getCompany()).isEqualTo(company);
        Assertions.assertThat(result.getRole()).isEqualTo(role);
    }

    @Test
    void updateUser_invalidUser_fail() {
        // GIVEN: Update request with non-existent id
        User updateRequest = User.builder()
                .id(999)
                .name("Hoang Giang")
                .build();
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // WHEN
        User result = userService.handleUpdateUser(updateRequest);

        // THEN
        Assertions.assertThat(result).isNull();
    }

    // ----------------- Test cho Delete User ----------------- //

    @Test
    void deleteUser_valid_success() {
        // GIVEN
        long userId = 10L;

        // WHEN
        userService.handleDeleteUser(userId);

        // THEN
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_exception() {
        // GIVEN
        long userId = 999L;
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(userId);

        // WHEN & THEN
        Assertions.assertThatThrownBy(() -> userService.handleDeleteUser(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    // ----------------- Test cho Fetch User By Id ----------------- //

    @Test
    void fetchUserById_success() {
        // GIVEN:
        long userId = 10L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // WHEN:
        User result = userService.fetchUserById(userId);

        // THEN:
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(10);
    }

    @Test
    void fetchUserById_notFound() {
        // GIVEN:
        long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // WHEN:
        User result = userService.fetchUserById(userId);

        // THEN:
        Assertions.assertThat(result).isNull();
    }

    // ----------------- Test cho Fetch All User ----------------- //

    @Test
    void fetchAllUser_withData_success() {
        // GIVEN:
        Pageable pageable = PageRequest.of(0, 10);

        // Use a simple Specification (return all)
        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        List<User> users = List.of(user);
        Page<User> pageUser = new PageImpl<>(users, pageable, users.size());
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageUser);

        // WHEN:
        ResultPaginationDTO result = userService.fetchAllUser(spec, pageable);

        // THEN
        Assertions.assertThat(result.getMeta().getPage()).isEqualTo(1);
        Assertions.assertThat(result.getMeta().getPageSize()).isEqualTo(10);
        Assertions.assertThat(result.getMeta().getTotal()).isEqualTo(1);
        Assertions.assertThat(result.getMeta().getPages()).isEqualTo(1);
        Assertions.assertThat(result.getResult()).isNotNull();
    }

    @Test
    void fetchAllUser_empty_success() {
        // GIVEN:
        Pageable pageable = PageRequest.of(0, 10);
        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        List<User> users = Collections.emptyList();
        Page<User> pageUser = new PageImpl<>(users, pageable, 0);
        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(pageUser);

        // WHEN:
        ResultPaginationDTO result = userService.fetchAllUser(spec, pageable);

        // THEN: Check when there are no users (total = 0, empty list)
        Assertions.assertThat(result.getMeta().getTotal()).isEqualTo(0);
        Assertions.assertThat(result.getResult()).isNotNull();
    }
}
