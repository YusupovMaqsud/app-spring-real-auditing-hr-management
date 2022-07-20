package uz.pdp.appspringrealauditinghrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TourniquetHistory {
    @Id
    @GeneratedValue
    private UUID id;

    private String status;

    @ManyToOne
    private TourniquetCard tourniquetCard;

    private Timestamp enteredAt; //kirgan vaqt

    private Timestamp exitedAt; //chiqgan vaqt
}
