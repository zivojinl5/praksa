package com.example.backend.core.service_Implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IClientCoreRepository;
import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.core.service.IClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private final IClientCoreRepository coreClientRepository;

    @Override
    public Page<Client> searchClients(ClientSearchModel clientSearchModel) {
        return coreClientRepository.searchClients(clientSearchModel);

    }

    @Override
    public HashMap<Long, String> getClientNamesByUserId(Long id) {
        return coreClientRepository.findClientNamesByUserId(id);

    }

    @Override
    public Client getClientById(Long id) {
        return coreClientRepository.findById(id);
    }

    @Override
    public Client createClient(ClientCreateModel clientCreateModel) {
        // Validate client
        // Save the client
        return coreClientRepository.save(clientCreateModel);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        // Validate client
        // Update the client
        return coreClientRepository.update(id, client);
    }

    @Override
    public void deleteClient(Long id) {
        coreClientRepository.deleteById(id);
    }

}
