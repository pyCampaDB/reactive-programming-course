package com.reactive.api.rest.controllers;

import com.reactive.api.rest.documents.Contact;
import com.reactive.api.rest.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContactController {
    private final ContactRepository contactRepository;
    @GetMapping("/contacts")
    public Flux<Contact> contactsList(){
        return contactRepository.findAll();
    }

    @GetMapping("/contact/id/{id}")
    public Mono<ResponseEntity<Contact>> getContact(@PathVariable String id){
        return contactRepository.findById(id)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/contact/email/{email}")
    public Mono<ResponseEntity<Contact>> getContactByEmail(@PathVariable String email){
        return contactRepository.findFirstByEmail(email)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contacts")
    public Mono<ResponseEntity<Contact>> saveContact(@RequestBody Contact contact){
        return contactRepository.insert(contact)
                .map(savedContact -> new ResponseEntity<>(savedContact, HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(contact, HttpStatus.NOT_ACCEPTABLE));
    }

    @PutMapping("/contact/{id}")
    public Mono<ResponseEntity<Contact>> updateContact(@PathVariable String id,@RequestBody Contact contact){
        return contactRepository.findById(id)
                .flatMap(updatedContact -> {
                    contact.setId(id);
                    return contactRepository.save(contact)
                            .map(contact1 -> new ResponseEntity<>(contact1, HttpStatus.ACCEPTED));
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/contact/{id}")
    public Mono<Void> deleteContact (@PathVariable String id){
        return contactRepository.deleteById(id);
    }
}
