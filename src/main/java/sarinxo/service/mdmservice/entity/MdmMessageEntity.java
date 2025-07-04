package sarinxo.service.mdmservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;
import sarinxo.service.mdmservice.entity.base.AuditableEntity;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Событие из MDM системы
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mdm_message", schema = "mdm")
public class MdmMessageEntity extends AuditableEntity {

    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Уникальный идентификатор сообщения из внешней системы
     */
    @Column(name = "external_id")
    private UUID externalId;
    /**
     * Уникальный идентификатор клиента
     */
    private String guid;
    /**
     * Тип события
     */
    @Enumerated(EnumType.STRING)
    private MdmEventType type;
    /**
     * Содержимое сообщения
     */
    @Column(columnDefinition = "jsonb")
    private String payload;
    /**
     * Участие в доставки данного сообщения
     */
    @OneToMany(mappedBy = "mdmMessageId", cascade = CascadeType.ALL)
    private Set<MdmMessageOutboxEntity> outboxMessages;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MdmMessageEntity that = (MdmMessageEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    /**
     * Тип события
     */
    public enum MdmEventType {
        USER_PHONE_CHANGE
    }

}
