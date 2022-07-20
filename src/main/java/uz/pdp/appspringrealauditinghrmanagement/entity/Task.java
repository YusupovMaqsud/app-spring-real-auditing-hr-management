package uz.pdp.appspringrealauditinghrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;//tavsifi

    private String deadline; //topshirish muddati, tugatish muddati

    private String status; //yangi jarayonda bajarilgan

    private String taskCode;

    @ManyToOne
    private Employee employee;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdAt; //qachon yaratildi

    @UpdateTimestamp
    private Timestamp updatedAt; //qachon tahrirlandi

    @CreatedBy
    private UUID createdBy; //kim yaratdi

    @LastModifiedBy
    private UUID updatedBY; //kim tahrirladi

    private Timestamp completedAt; //yakunlandi
}
