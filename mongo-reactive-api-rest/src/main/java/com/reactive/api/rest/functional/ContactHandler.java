package com.reactive.api.rest.functional;

import com.reactive.api.rest.documents.Contact;
import com.reactive.api.rest.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
@Component
@RequiredArgsConstructor
public class ContactHandler {
    private final ContactRepository contactRepository;

    private final Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private final Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).build();

    //Contacts List
    public Mono<ServerResponse> contactsList(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contactRepository.findAll(), Contact.class);

    }

    public Mono<ServerResponse> getContactById(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        return contactRepository.findById(id)
                .flatMap(contact ->
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(contact))
                ).switchIfEmpty(response404);
    }

    public Mono<ServerResponse> getContactByEmail(ServerRequest serverRequest){
        String email = serverRequest.pathVariable("email");
        return contactRepository.findFirstByEmail(email)
                .flatMap(contact ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(contact))
                ).switchIfEmpty(response404);
    }

    //insert a contact
    public Mono<ServerResponse> insertContact(ServerRequest serverRequest){
        Mono<Contact> monoContact = serverRequest.bodyToMono(Contact.class);
        //return monoContact.flatMap(contact -> contactRepository.save(contact))
        return monoContact.flatMap(contactRepository::save)
                .flatMap(savedContact -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(savedContact))
                ).switchIfEmpty(response406);
    }

    public Mono<ServerResponse> updateContact (ServerRequest serverRequest){
        Mono<Contact> monoContact = serverRequest.bodyToMono(Contact.class);
        String id = serverRequest.pathVariable("id");
        Mono<Contact> updatedContact = monoContact
                .flatMap(contact -> contactRepository.findById(id)
                        .flatMap(oldContact -> {
                            oldContact.setPhone(contact.getPhone());
                            oldContact.setName(contact.getName());
                            oldContact.setEmail(contact.getEmail());
                            return contactRepository.save(oldContact);
                        }));
        return updatedContact.flatMap(contact ->
                ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(contact)))
                .switchIfEmpty(response404);
    }

    public Mono<ServerResponse> deleteContact (ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Void> deletedContact = contactRepository.deleteById(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deletedContact, Void.class);
    }
}
