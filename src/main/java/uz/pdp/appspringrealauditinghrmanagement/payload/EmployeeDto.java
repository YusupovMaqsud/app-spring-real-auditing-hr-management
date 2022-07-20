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
public class EmployeeDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Double salary;

    @Email
    @NotNull
    private String email;

    @NotNull
    private UUID companyId;

}
