package com.example.margingmanagement.utility;

import com.example.margingmanagement.entity.MarginOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class MarginOrderValidator {

    ObjectMapper mapper = new ObjectMapper();

    public void validate(Object target) {
        MarginOrder marginOrder = (MarginOrder) target;
        Map<String, Object> marginOrderMap = mapper.convertValue(marginOrder, Map.class);
        Set<String> keys = marginOrderMap.keySet();

        //Null checks
        for (String key : keys) {
            ValidationUtils.checkIfNotNull("MarginOrder_+" + key, marginOrderMap.get(key));
        }

        //Length checks
        ValidationUtils.checkStringLength("MarginOrder_baseCCY", marginOrderMap.get("baseCCY"), 3);
        ValidationUtils.checkStringLength("MarginOrder_baseCCY", marginOrderMap.get("termCCY"), 3);

        //Integer checks
        ValidationUtils.isInteger("MarginOrder_traderTier", marginOrderMap.get("traderTier"));
        ValidationUtils.isInteger("MarginOrder_fromAmount", marginOrderMap.get("fromAmount"));
        ValidationUtils.isInteger("MarginOrder_toAmount", marginOrderMap.get("toAmount"));


        ValidationUtils.isDouble("MarginOrder_bidModifier", marginOrderMap.get("bidModifier"));
        ValidationUtils.isDouble("MarginOrder_askModifier", marginOrderMap.get("askModifier"));
    }


}
