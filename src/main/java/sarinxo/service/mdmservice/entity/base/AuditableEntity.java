package sarinxo.service.mdmservice.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import sarinxo.service.mdmservice.common.AppConstant;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {

    /**
     * Пользователь или приложение, которое создало запись
     */
    @Column(name = "create_user", nullable = false, updatable = false)
    private String createUser;
    /**
     * Пользователь или приложение, изменившее запись последним
     */
    @Column(name = "last_update_user", nullable = false)
    private String lastUpdateUser;
    /**
     * Дата создания записи
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    /**
     * Дата последнего обновления записи
     */
    @Column(name = "last_update_time", nullable = false)
    private LocalDateTime lastUpdateTime;

    @PrePersist
    public void prePersist() {
        createUser = AppConstant.APP_NAME;
        lastUpdateUser = AppConstant.APP_NAME;
        createTime = LocalDateTime.now();
        lastUpdateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdateTime = LocalDateTime.now();
        lastUpdateUser = AppConstant.APP_NAME;
    }

}
