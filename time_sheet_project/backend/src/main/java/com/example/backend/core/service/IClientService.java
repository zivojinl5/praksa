package com.example.backend.core.service;

import java.util.HashMap;

import org.springframework.data.domain.Page;

import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;

public interface IClientService {

    Client getClientById(Long id);

    Client createClient(ClientCreateModel clientCreateModel);

    Client updateClient(Long id, Client client);

    void deleteClient(Long id);

    Page<Client> searchClients(ClientSearchModel clientSearchModel);

    HashMap<Long, String> getClientNamesByUserId(Long id);

}