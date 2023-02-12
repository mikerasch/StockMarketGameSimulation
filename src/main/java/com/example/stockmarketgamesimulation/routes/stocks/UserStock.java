package com.example.stockmarketgamesimulation.routes.stocks;

import com.example.stockmarketgamesimulation.routes.users.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "basic_stock_information_id")
    private BasicStockInformation basicStockInformation;

    @NonNull
    private Integer quantity;
}
