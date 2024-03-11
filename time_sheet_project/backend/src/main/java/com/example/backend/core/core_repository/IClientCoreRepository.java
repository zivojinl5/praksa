package com.example.backend.core.core_repository;

import java.util.HashMap;

import org.springframework.data.domain.Page;

import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;

public interface IClientCoreRepository {
    Page<Client> searchClients(ClientSearchModel clientSearchModel);

    HashMap<Long, String> findClientNamesByUserId(Long userId);

    Client findById(Long id);

    Client save(ClientCreateModel clientCreateModel);

    Client update(Long id, Client client);

    void deleteById(Long id);

}