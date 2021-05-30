package com.example.connector.adapters.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/access")
class CardAccessEndpoint {

    private final CardAccessFacade cardAccessFacade;

    CardAccessEndpoint(final CardAccessFacade cardAccessFacade) {
        this.cardAccessFacade = cardAccessFacade;
    }


//    TODO custom response type
    @PostMapping
    ResponseEntity setCardAccess(@RequestBody SetCardAccessRequest setCardAccessRequest) {
        cardAccessFacade.setCardAccess(setCardAccessRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

}
