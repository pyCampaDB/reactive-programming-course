package com.reactive.api.rest.repositories;

import com.reactive.api.rest.documents.Contact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ContactRepository extends ReactiveMongoRepository<Contact, String> {
    Mono<Contact> findFirstByEmail(String email);

    Mono<Contact> findAllByPhoneOrName (String phoneOrName);
}
