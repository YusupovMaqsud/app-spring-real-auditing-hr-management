package uz.pdp.appspringrealauditinghrmanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryDto {
    @ManyToOne
    private Employee employee;

    private Double salary;

    private String verifyingCode;

}
