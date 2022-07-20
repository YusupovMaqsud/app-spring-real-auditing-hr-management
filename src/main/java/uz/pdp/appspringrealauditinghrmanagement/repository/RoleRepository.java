package uz.pdp.appspringrealauditinghrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringrealauditinghrmanagement.entity.Role;
import uz.pdp.appspringrealauditinghrmanagement.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Role findByRoleEnum(RoleEnum roleEnum);
}
