package com.rschir.prac.services;

import com.rschir.prac.model.Client;
import com.rschir.prac.repositories.ClientsRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClientsService {
    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Client findOne(long id) {
        return clientsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    @Transactional
    public Client saveOne(Client newClient) {
        return clientsRepository.save(newClient);
    }

    @Transactional
    public Client updateOne(Client updatedClient, long id) {
        Client client = clientsRepository.findById(id).orElseThrow(NotFoundException::new);
        client.setName(updatedClient.getName());
        client.setLogin(updatedClient.getLogin());
        client.setEmail(updatedClient.getEmail());
        client.setPassword(updatedClient.getPassword());
        return clientsRepository.save(client);
    }

    @Transactional
    public void deleteOne(long id) {
        clientsRepository.deleteById(id);
    }
}
