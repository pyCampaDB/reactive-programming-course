package com.reactive.api.rest.controller;

import com.reactive.api.rest.documents.Contact;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactControllerTests {
    @Autowired
    private WebTestClient webTestClient;
    private Contact savedContact;

    @Test
    @Order(0)
    public void saveContactTest(){
        Flux<Contact> fluxContact = webTestClient.post()
                .uri("/api/v1/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new Contact("webtest", "w@w.t", "111")
                )).exchange()
                .expectStatus().isAccepted()
                .returnResult(Contact.class).getResponseBody()
                .log();
        fluxContact.next().subscribe(
                contact -> {
                    this.savedContact = contact;
                }
        );
        Assertions.assertNotNull(savedContact);
    }

    @Test
    @Order(1)
    public void getContactByEmailTest(){
        Flux<Contact> contactFlux = webTestClient.get()
                .uri("/api/v1/contact/email/{email}", "w@w.t")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Contact.class).getResponseBody()
                .log();

        StepVerifier.create(contactFlux)
                .expectSubscription()
                .expectNextMatches(contact -> contact.getEmail().equals("w@w.t"))
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void updateContactTest(){
        Flux<Contact> fluxContact = webTestClient.put()
                .uri("/api/v1/contact/{id}", savedContact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new Contact(savedContact.getId(), "webTestClient", "w@w.t", "112"))
                ).exchange()
                .returnResult(Contact.class).getResponseBody()
                .log();
        StepVerifier.create(fluxContact)
                .expectSubscription()
                .expectNextMatches(contact ->
                        contact.getName().equals("webTestClient") && contact.getPhone().equals("112")
                )
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void contactsListTest(){
        Flux<Contact> contactsFlux = webTestClient.get()
                .uri("/api/v1/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Contact.class).getResponseBody()
                .log();

        StepVerifier.create(contactsFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void deleteTestContact(){
        Flux<Void> flux = webTestClient.delete()
                .uri("/api/v1/contact/{id}", savedContact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Void.class).getResponseBody();
        StepVerifier.create(flux)
                .expectSubscription()
                .verifyComplete();
    }

}
