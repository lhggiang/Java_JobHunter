package vn.hoanggiang.jobhunter.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "confirmPassword cannot be blank")
    private String confirmPassword;
}
