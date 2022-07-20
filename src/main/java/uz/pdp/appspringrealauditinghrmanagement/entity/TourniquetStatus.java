package uz.pdp.appspringrealauditinghrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appspringrealauditinghrmanagement.enums.TourniquetStatusEnum;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TourniquetStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TourniquetStatusEnum name;

    public TourniquetStatus(TourniquetStatusEnum name) {
        this.name = name;
    }
}
