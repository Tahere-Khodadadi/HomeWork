package ir.maktabHW13.dto;

import ir.maktabHW13.model.Roles;
import ir.maktabHW13.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class UserSignUpDTO {

    @Setter
    @Getter
    @NotBlank(message = "name cannot be empty ")
    private String firstName;
    @Setter
    @Getter
    @NotBlank(message = " family cannot be empty")
    private String lastName;
    @Setter
    @Getter
    @NotBlank(message = "password cannot be empty")
    private String password;
    @Setter
    @Getter
    @NotNull(message = "role cannot be empty")
    private Roles roles;

    @Getter
    @Setter
    @NotBlank(message = "Identity code cannot be empty")
    private String SpecialCode;

    @Getter
    @Setter
    private UserStatus userStatus = UserStatus.Pending;


}
