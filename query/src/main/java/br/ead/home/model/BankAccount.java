package br.ead.home.model;

import br.ead.home.agregates.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntity {

    @Id
    String id;

    @NotBlank
    String accountHolder;

    @NotNull
    Date createdAt;

    Date closedAt;

    @NotNull
    AccountType accountType;

    @Min(0)
    double balance;
}
