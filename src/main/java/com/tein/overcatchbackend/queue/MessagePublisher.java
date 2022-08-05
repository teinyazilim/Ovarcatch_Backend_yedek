package com.tein.overcatchbackend.queue;

public interface MessagePublisher {

    void publish(final String message);
}
