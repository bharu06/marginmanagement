package com.example.margingmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "margin_orders")
@Getter @Setter
@AllArgsConstructor
public class MarginOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String instruction;

    @Column
    private String baseCCY;

    @Column
    private String termCCY;

    @Column
    private Integer traderTier;

    @Column
    private Integer fromAmount;

    @Column
    private Integer toAmount;

    @Enumerated(EnumType.STRING)
    @Column
    private Operator bidOperator;

    @Column
    private String amountCCY;

    @Column
    private Double bidModifier;

    @Enumerated(EnumType.STRING)
    @Column
    private Operator askOperator;

    @Column
    private Double askModifier;

    @Column
    private String remarks;

    public MarginOrder() {

    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", instruction='" + instruction + '\'' +
                ", baseCCY='" + baseCCY + '\'' +
                ", termCCY='" + termCCY + '\'' +
                ", traderTier=" + traderTier +
                ", fromAmount=" + fromAmount +
                ", toAmount=" + toAmount +
                ", bidOperator=" + bidOperator +
                ", amountCCY='" + amountCCY + '\'' +
                ", bidModifier=" + bidModifier +
                ", askOperator=" + askOperator +
                ", askModifier=" + askModifier +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
