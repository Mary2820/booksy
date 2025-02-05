package com.solvd.jaxbdemo.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String value) {
        return value != null ? LocalDateTime.parse(value) : null;
    }

    @Override
    public String marshal(LocalDateTime value) {
        return value != null ? value.toString() : null;
    }
} 