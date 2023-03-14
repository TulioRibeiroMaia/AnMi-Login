package anmi.login.dto;

import anmi.login.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFormDTO {

    @CPF
    @NotEmpty
    private String cpf;

    @NotEmpty
    private String fullName;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotNull
    private UserRole userRole;
}
