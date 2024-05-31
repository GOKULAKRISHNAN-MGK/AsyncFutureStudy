package io.mgk.threadpool.AsyncFutureStudy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_TBL_DTLS")
@AllArgsConstructor
@NoArgsConstructor
public class UserDtls {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String shift;
    private boolean reserved;
}
