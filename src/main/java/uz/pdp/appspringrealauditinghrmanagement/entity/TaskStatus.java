package uz.pdp.appspringrealauditinghrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appspringrealauditinghrmanagement.enums.RoleEnum;
import uz.pdp.appspringrealauditinghrmanagement.enums.TaskStatusEnum;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TaskStatusEnum name;

    public TaskStatus(TaskStatusEnum name) {
        this.name = name;
    }
}
