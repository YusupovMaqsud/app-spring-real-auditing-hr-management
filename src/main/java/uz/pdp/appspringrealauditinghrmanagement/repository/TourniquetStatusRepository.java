package uz.pdp.appspringrealauditinghrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetCard;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetStatus;
import uz.pdp.appspringrealauditinghrmanagement.enums.TourniquetStatusEnum;

import java.util.Optional;
import java.util.UUID;

public interface TourniquetStatusRepository extends JpaRepository<TourniquetStatus, Integer> {
    TourniquetStatus findByName(TourniquetStatusEnum name);
}
