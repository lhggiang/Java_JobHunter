package vn.hoanggiang.jobhunter.domain.dto;

import java.time.Instant;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.hoanggiang.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;

    private String name;
    private String email;

    private int age;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String address;
    private Instant createdAt;
}
