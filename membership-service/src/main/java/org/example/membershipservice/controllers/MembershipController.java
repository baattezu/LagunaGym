package org.example.membershipservice.controllers;


import org.example.membershipservice.entities.Membership;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @PostMapping
    public ResponseEntity<Membership> signForMembership(@RequestBody){

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
