package com.example.rest.Helpers;

import com.example.rest.entity.CertificateEntity;
import com.example.rest.entity.CurierEntity;
import com.example.rest.entity.GoodsEntity;
import com.example.rest.entity.OrderEntity;
import com.example.rest.model.Goods;
import com.example.rest.model.GoodsWithCount;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class Utilities {
    static public int ceil(int a) {
        return (int)Math.ceil((double)a);
    }

    static public SimpleDateFormat getDefaultDateFormat() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", new Locale("ru"));
        format.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return format;
    }

    static public List<CertificateEntity> sortCertificates(List<CertificateEntity> list) {
        SimpleDateFormat simpleDateFormat = getDefaultDateFormat();
        Collections.sort(list, (CertificateEntity o1, CertificateEntity o2) -> {
            try {
                return simpleDateFormat.parse(o2.getDateCreate()).compareTo(simpleDateFormat.parse(o1.getDateCreate()));
            } catch (ParseException e) {
                return 0;
            }
        });
        return list;
    }
    static public List<GoodsWithCount> getGoodsWithCount(List<GoodsEntity> list) {
        return GoodsWithCount.toModel(list);
    }
    static public Integer getLastOrder(CurierEntity curier) {
        if(curier.getOrders().size() == 0) {
            return -1;
        }
        OrderEntity order = curier.getOrders().get(curier.getOrders().size() - 1);
        return order.getId();
    }
}
