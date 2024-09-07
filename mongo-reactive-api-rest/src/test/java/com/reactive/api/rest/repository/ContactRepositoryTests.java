package com.reactive.api.rest.repository;

import com.reactive.api.rest.documents.Contact;
import com.reactive.api.rest.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactRepositoryTests {
    @Autowired
    private ContactRepository contactRepository;

    @BeforeAll
    public void insertData(){
        Contact contact1 = Contact.builder()
                .name("Madara Uchiha")
                .email("madara@uchiha.com")
                .phone("111111").build();
        Contact contact2 = Contact.builder()
                .name("Sishui Uchiha")
                .email("sishui@uchiha.com")
                .phone("222225").build();
        Contact contact3 = Contact.builder()
                .name("Sasuke Uchiha")
                .email("sasuke@uchiha.com")
                .phone("333333").build();
        Contact contact4 = Contact.builder()
                .name("Itachi Uchiha")
                .email("itachi@uchiha.com")
                .phone("444444").build();

        StepVerifier.create(contactRepository.insert(contact1).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(contactRepository.insert(contact2).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(contactRepository.save(contact3).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(contactRepository.save(contact4).log())
                .expectSubscription()
                .expectNextMatches(contact -> (contact.getId() != null))
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void contactsListTest(){
        StepVerifier.create(contactRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void getContactByEmailTest(){
        StepVerifier.create(contactRepository.findFirstByEmail("itachi@uchiha.com").log())
                .expectSubscription()
                .expectNextMatches(contact -> contact.getEmail().equals("itachi@uchiha.com"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void updateContactTest(){
        Mono<Contact> updatedContact = contactRepository.findFirstByEmail("sasuke@uchiha.com")
                .map(contact -> {
                    contact.setPhone("123123");
                    return contact;
                }).flatMap(contactRepository::save);

        StepVerifier.create(updatedContact.log())
                .expectSubscription()
                .expectNextMatches(contact -> contact.getPhone().equals("123123"))
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void deleteContactByIdTest(){
        Mono<Void> deletedContact = contactRepository.findFirstByEmail("sishui@uchiha.com")
                .flatMap(contact -> {
                    return contactRepository.deleteById(contact.getId());
                }).log();

        StepVerifier.create(deletedContact)
                .expectSubscription().verifyComplete();
    }

    @Test
    public void deleteContactTest(){
        Mono<Void> deletedContact = contactRepository.findFirstByEmail("madara@uchiha.com")
                .flatMap(contactRepository::delete);
        StepVerifier.create(deletedContact)
                .expectSubscription()
                .verifyComplete();
    }

    @AfterAll
    public void cleanData(){
        Mono<Void> deleteContacts = contactRepository.deleteAll();
        StepVerifier.create(deleteContacts)
                .expectSubscription()
                .verifyComplete();
    }

}
