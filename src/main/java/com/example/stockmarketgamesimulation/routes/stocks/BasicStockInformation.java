package com.example.stockmarketgamesimulation.routes.stocks;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
public class BasicStockInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String symbol;
    private String name;
    private String exchange;
    private String assetType;
    private String ipoDate;
    private String delistingDate;
    private String status;
    @OneToMany(mappedBy = "basicStockInformation")
    private List<UserStock> users;
}
