package com.example.margingmanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
public class FindRequest {

    public String instruction;
    @Column(name = "baseCCY")
    public String baseCCY;
    @Column(name = "termCCY")
    public String termCCY;
    public Integer traderTier;
    public Integer amount;

}
