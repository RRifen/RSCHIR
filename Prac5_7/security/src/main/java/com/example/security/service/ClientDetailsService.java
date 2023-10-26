package com.example.security.service;

import com.example.security.models.Client;
import com.example.security.repositories.ClientsRepository;
import com.example.security.security.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientDetailsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Client> person = clientsRepository.findById(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new ClientDetails(person.get());
    }

}
