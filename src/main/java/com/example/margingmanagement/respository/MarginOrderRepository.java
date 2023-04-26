package com.example.margingmanagement.respository;

import com.example.margingmanagement.entity.MarginOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarginOrderRepository extends JpaRepository<MarginOrder, Long> {

    @Query("SELECT m FROM MarginOrder m WHERE m.instruction IN (:instructions) " +
            "AND m.baseCCY IN (:baseCCYs) " +
            "AND m.termCCY IN (:termCCYs) AND m.traderTier IN (:traderTiers)" +
            "AND ((m.fromAmount < :amount AND m.toAmount > :amount) OR (m.fromAmount = 0 AND m.toAmount = 0))")
    List<MarginOrder> findMarginOrders(@Param("instructions") List<String> instructions,
                                       @Param("baseCCYs") List<String> baseCCYs,
                                       @Param("termCCYs") List<String> termCCYs,
                                       @Param("traderTiers") List<Integer> traderTiers,
                                       @Param("amount") Integer amount);
}
