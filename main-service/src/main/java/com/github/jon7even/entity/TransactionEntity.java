package com.github.jon7even.entity;

import com.github.jon7even.enums.transaction.TransactionStatus;
import com.github.jon7even.enums.transaction.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс описывающий транзакцию, совершаемую со счетом пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction", schema = "application")
public class TransactionEntity {
    @Id
    @SequenceGenerator(name = "TransactionGenId", sequenceName = "application.transaction_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TransactionGenId")
    @Column(name = "id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccountEntity target;

    @Column(name = "from", nullable = false)
    private String from;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransactionEntity that)) {
            return false;
        }
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, that.id);
        eb.append(from, that.from);
        eb.append(amount, that.amount);
        eb.append(status, that.status);
        eb.append(type, that.type);
        eb.append(createdOn, that.createdOn);
        eb.append(updatedOn, that.updatedOn);
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        hcb.append(from);
        hcb.append(amount);
        hcb.append(status);
        hcb.append(type);
        hcb.append(createdOn);
        hcb.append(updatedOn);
        return hcb.toHashCode();
    }
}
