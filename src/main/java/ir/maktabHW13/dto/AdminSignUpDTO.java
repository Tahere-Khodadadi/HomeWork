package ir.maktabHW13.dto;

import ir.maktabHW13.model.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AdminSignUpDTO {

    @Setter
    @Getter
    @NotBlank(message = "name cannot be empty ")
    private String firstName;


    @Setter
    @Getter
    @NotBlank(message = "password cannot be empty")
    private String password;


    @Setter
    @Getter
    @NotNull(message = "role cannot be empty")
    private Roles roles;

}
