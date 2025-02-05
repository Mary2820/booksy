package com.solvd.jaxbdemo.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String value) {
        return value != null ? LocalTime.parse(value) : null;
    }

    @Override
    public String marshal(LocalTime value) {
        return value != null ? value.toString() : null;
    }
} 