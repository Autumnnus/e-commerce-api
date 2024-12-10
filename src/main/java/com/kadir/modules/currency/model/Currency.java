package com.kadir.modules.currency.model;

import com.kadir.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseEntity {

    @Column(name = "currency_code", unique = true, nullable = false)
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "exchange_rate")
    private double exchangeRate;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "is_default")
    private Boolean isDefault = false;
}
