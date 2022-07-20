package uz.pdp.appspringrealauditinghrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetCard;
import uz.pdp.appspringrealauditinghrmanagement.entity.TourniquetHistory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourniquetCardRepository extends JpaRepository<TourniquetCard, UUID> {
}
