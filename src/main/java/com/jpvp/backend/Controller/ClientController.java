package com.jpvp.backend.Controller;

import com.jpvp.backend.Model.Client;
import com.jpvp.backend.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    /*
    A simple test mapping to see if I haven't royally screwed up and that the server is actually able to respond with 200
     */
    @GetMapping
    public String testMapping() {
        return "Test";
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }
}
