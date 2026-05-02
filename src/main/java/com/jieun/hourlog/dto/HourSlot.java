package com.jieun.hourlog.dto;

public class HourSlot {

    private final int hour;
    private final Long id;
    private final String content;
    private final String mood;

    public HourSlot(int hour, Long id, String content, String mood) {
        this.hour = hour;
        this.id = id;
        this.content = content;
        this.mood = mood;
    }

    public int getHour() { return hour; }
    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getMood() { return mood; }

    public boolean isEmpty() {
        return content == null || content.isBlank();
    }

    public String getHourLabel() {
        return String.format("%02d시", hour);
    }
}
