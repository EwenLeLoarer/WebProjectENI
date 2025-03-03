package fr.eni.ENI_enchere.bo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    @NotBlank
    private String oldPassword;
    
    @NotBlank
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
    
    @NotBlank
    @Size(min = 8)
    private String confirmPassword;
}
