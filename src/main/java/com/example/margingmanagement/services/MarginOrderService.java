package com.example.margingmanagement.services;

import com.example.margingmanagement.entity.FindRequest;
import com.example.margingmanagement.entity.MarginOrder;
import com.example.margingmanagement.respository.MarginOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MarginOrderService {

    @Autowired
    MarginOrderRepository marginOrderRepository;


    public List<MarginOrder> getAllMarginOrders() {
        return marginOrderRepository.findAll();
    }

    public void clearAll() {
        marginOrderRepository.deleteAll();
    }

    public MarginOrder save(MarginOrder marginOrder) {
        return marginOrderRepository.save(marginOrder);
    }

    public List<MarginOrder> clearAndSaveAll(List<MarginOrder> marginOrders) {
        clearAll();
        return marginOrderRepository.saveAll(marginOrders);
    }

    public List<MarginOrder> search(FindRequest findRequest) {

        List<String> instructions = new ArrayList<>(Collections.singletonList("*"));
        instructions.add(findRequest.getInstruction());

        List<String> baseCCYs = new ArrayList<>(Collections.singletonList("*"));
        baseCCYs.add(findRequest.getBaseCCY());

        List<String> termCCYs = new ArrayList<>(Collections.singletonList("*"));
        termCCYs.add(findRequest.getTermCCY());

        List<Integer> traderTiers = new ArrayList<>(Collections.singletonList(0));
        traderTiers.add(findRequest.getTraderTier());


        return marginOrderRepository.findMarginOrders(instructions, baseCCYs, termCCYs, traderTiers,
                findRequest.getAmount());
    }
}
