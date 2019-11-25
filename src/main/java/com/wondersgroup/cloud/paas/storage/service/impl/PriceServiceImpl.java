package com.wondersgroup.cloud.paas.storage.service.impl;

import com.wondersgroup.cloud.paas.common.utils.CollectionUtils;
import com.wondersgroup.cloud.paas.storage.enums.FormatTypeEnum;
import com.wondersgroup.cloud.paas.storage.enums.PriceItemEnum;
import com.wondersgroup.cloud.paas.storage.mapper.PriceMapper;
import com.wondersgroup.cloud.paas.storage.model.Price;
import com.wondersgroup.cloud.paas.storage.model.PriceExample;
import com.wondersgroup.cloud.paas.storage.pojo.PricePojo;
import com.wondersgroup.cloud.paas.storage.pojo.PriceSubPojo;
import com.wondersgroup.cloud.paas.storage.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenlong
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PriceServiceImpl implements PriceService {
    @Autowired(required = false)
    private PriceMapper priceMapper;


    @Override
    public List<PricePojo> list() {
        PriceExample example = new PriceExample();
        List<Price> list = priceMapper.selectByExample(example);
        Map<String, List<Price>> priceMap = new HashMap<>();
        for (Price price : list) {
            String code = price.getCode();
            List<Price> prices;
            if (priceMap.containsKey(code)) {
                prices = priceMap.get(code);
                prices.add(price);
            } else {
                prices = new ArrayList<>();
                prices.add(price);
                priceMap.put(code, prices);
            }
        }

        List<PricePojo> pricePojoList = buildPriceInfo(priceMap);
        return pricePojoList;
    }

    private List<PricePojo> buildPriceInfo(Map<String, List<Price>> map) {
        List<PricePojo> list = new ArrayList<>();
        for (String code : map.keySet()) {
            List<Price> prices = map.get(code);
            PricePojo pricePojo = new PricePojo();
            pricePojo.setKey(code);
            if (prices.size() > 1) {
                pricePojo.setKey(code);
                pricePojo.setFormatType(FormatTypeEnum.TABLE.value());
                pricePojo.setLabel(PriceItemEnum.valueOf(code).getRender());
                List<PriceSubPojo> priceSubPojos = new ArrayList<>();
                for (Price price : prices) {
                    PriceSubPojo priceSubPojo = new PriceSubPojo();
                    priceSubPojo.setSubkey(price.getSubcode());
                    priceSubPojo.setPrefixLabel(price.getPrefixlabel());
                    priceSubPojo.setSuffixLabel(price.getSuffixlabel());
                    priceSubPojo.setValue(String.valueOf(price.getValue()));
                    priceSubPojo.setScopeBegin(price.getScopeBegin());
                    priceSubPojo.setScopeEnd(price.getScopeEnd());
                    priceSubPojos.add(priceSubPojo);
                }
                pricePojo.setTable(priceSubPojos);
            } else {
                Price price = prices.get(0);
                pricePojo.setKey(code);
                pricePojo.setLabel(PriceItemEnum.valueOf(code).getRender());
                pricePojo.setFormatType(FormatTypeEnum.ROW.value());
                pricePojo.setPrefixLabel(price.getPrefixlabel());
                pricePojo.setSuffixLabel(price.getSuffixlabel());
                pricePojo.setValue(String.valueOf(price.getValue()));
                pricePojo.setScopeBegin(price.getScopeBegin());
                pricePojo.setScopeEnd(price.getScopeEnd());
            }

            list.add(pricePojo);
        }
        return list;
    }

    @Override
    public void update(List<PricePojo> list) {
        for (PricePojo pricePojo : list) {
            Price price;
            PriceExample example;
            if (pricePojo.getTable() != null) {
                for (PriceSubPojo priceSubPojo : pricePojo.getTable()) {
                    example = new PriceExample();
                    example.createCriteria().andSubcodeEqualTo(priceSubPojo.getSubkey());
                    List<Price> prices = priceMapper.selectByExample(example);
                    if (CollectionUtils.isNotEmpty(prices)) {
                        price = prices.get(0);
                        price.setValue(new BigDecimal((priceSubPojo.getValue())));
                        priceMapper.updateByPrimaryKey(price);
                    }
                }
            } else {
                example = new PriceExample();
                example.createCriteria().andSubcodeEqualTo(pricePojo.getKey());
                List<Price> prices = priceMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(prices)) {
                    {
                        price = prices.get(0);
                        price.setValue(new BigDecimal((pricePojo.getValue())));
                        priceMapper.updateByPrimaryKey(price);
                    }
                }
            }
        }
    }
}
