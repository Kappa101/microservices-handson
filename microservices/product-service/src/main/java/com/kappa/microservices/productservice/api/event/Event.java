package com.kappa.microservices.productservice.api.event;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 *
 * what is an Event ?
 * @param <K> what is this for in Event?
 *
 * @param <T> what is this for in EVent ?
 */
public class Event<K, T> {


    public enum Type {CREATE, DELETE}

    private Event.Type eventType;
    private K key;
    private T data;
    private LocalDateTime eventCreatedAt;


    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }


    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = now();
    }


    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }

}
