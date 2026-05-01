package com.jieun.hourlog.dto;

public class HourSlot {

    private final int hour;
    private final Long id;
    private final String content;

    public HourSlot(int hour, Long id, String content) {
        this.hour = hour;
        this.id = id;
        this.content = content;
    }

    public int getHour() { return hour; }
    public Long getId() { return id; }
    public String getContent() { return content; }

    public boolean isEmpty() {
        return content == null || content.isBlank();
    }

    public String getHourLabel() {
        return String.format("%02d시", hour);
    }
}
