package uz.pdp.appspringrealauditinghrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Email
    private String email;


    private String password;


    private UUID EmployeeId;
}
