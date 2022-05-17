package com.example.rest.Helpers;

import com.example.rest.entity.CertificateEntity;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

@Component
public class Utilities {
    static public int ceil(int a) {
        return (int)Math.ceil((double)a);
    }

    static public SimpleDateFormat getDefaultDateFormat() {
        return new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
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
}
