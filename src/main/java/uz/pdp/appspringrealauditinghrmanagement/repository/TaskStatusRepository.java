package uz.pdp.appspringrealauditinghrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appspringrealauditinghrmanagement.entity.Task;
import uz.pdp.appspringrealauditinghrmanagement.entity.TaskStatus;
import uz.pdp.appspringrealauditinghrmanagement.enums.TaskStatusEnum;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    TaskStatus findByName(TaskStatusEnum name);
}
