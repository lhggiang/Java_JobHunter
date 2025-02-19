package vn.hoanggiang.jobhunter.domain.response;

import java.time.Instant;

import lombok.*;
import vn.hoanggiang.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUserDTO {
    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private Instant createdAt;

    private CompanyUser company;

    private RoleUser role;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompanyUser {
        private long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser {
        private long id;
        private String name;
    }
}