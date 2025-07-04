package sarinxo.service.mdmservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import sarinxo.service.mdmservice.entity.base.AuditableEntity;

import java.util.Objects;

/**
 * Статус отправки MDM события во внешний сервис
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mdm_message_outbox", schema = "mdm")
public class MdmMessageOutboxEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Cобытие из таблицы mdmMessage
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mdm_message_id")
    private MdmMessageEntity mdmMessageId;
    /**
     * Статус доставки сообщения
     */
    @Enumerated(EnumType.STRING)
    private MessageDeliveryStatus status;
    /**
     * Направление, куда должно быть доставлено сообщение
     */
    @Enumerated(EnumType.STRING)
    private MessageSendTarget target;
    /**
     * Ответ внешнего сервиса
     */
    @Column(name = "response_data", columnDefinition = "jsonb")
    private String responseData;

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
        MdmMessageOutboxEntity that = (MdmMessageOutboxEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    /**
     * Статус доставки сообщения
     */
    public enum MessageDeliveryStatus {
        NEW, DELIVERED, ERROR, FATAL_ERROR
    }

    /**
     * Направление, куда должно быть доставлено сообщение
     */
    public enum MessageSendTarget {
        USER_DATA_SERVICE_ONE, USER_DATA_SERVICE_TWO
    }

}
