package uz.pdp.appspringrealauditinghrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appspringrealauditinghrmanagement.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmailAndEmailCode(String email, Integer emailCode);

    List<Employee> findAllByCompanyId(UUID company_id);
}
