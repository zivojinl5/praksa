package com.example.backend.data.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IClientCoreRepository;
import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.data.repository.IClientJPARepository;
import com.example.backend.data.repository.ICountryJPARepository;
import com.example.backend.mapper.ClientMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ClientAdapter implements IClientCoreRepository {
    private final ICountryJPARepository countryJPARepository;
    private final IClientJPARepository clientJPARepository;
    private final ClientMapper mapper;

    @Override
    public Page<Client> searchClients(ClientSearchModel clientSearchModel) {
        Sort sort = Sort.by(
                clientSearchModel.getSortOrder().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC,
                clientSearchModel.getSortField());
        Pageable pageable = PageRequest.of(clientSearchModel.getPage(), clientSearchModel.getSize(), sort);
        Page<ClientEntity> entitiesPage = clientJPARepository.searchClients(clientSearchModel, pageable);

        if (entitiesPage.isEmpty()) {
            Page<ClientEntity> allEntities = clientJPARepository.findAll(pageable);
            return mapEntitiesPageToModelsPage(allEntities);

        }

        return mapEntitiesPageToModelsPage(entitiesPage);
    }

    @Override
    public HashMap<Long, String> findClientNamesByUserId(Long id) {
        return clientJPARepository.findClientNamesByUserId(id);

    }

    @Override
    public Client findById(Long id) {
        ClientEntity entity = clientJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        return (Client) mapper.mapEntityModelClient(entity);

    }

    @Override
    public Client save(ClientCreateModel clientCreateModel) {
        ClientEntity entity = mapAndPopulateClientEntity(clientCreateModel);

        ClientEntity createdEntity = clientJPARepository.save(entity);
        return (Client) mapper.mapEntityModelClient(createdEntity);
    }

    /*
     * @Override
     * public Client update(Long id, Client model) {
     * ClientEntity targetClientEntity = clientJPARepository.findById(id)
     * .orElseThrow(() -> new IllegalArgumentException("Client not found"));
     * targetClientEntity = (ClientEntity) mapper.mapModelEntityPatchClient(model,
     * targetClientEntity);
     * ClientEntity updatedEntity = clientJPARepository.save(targetClientEntity);
     * return (Client) mapper.mapEntityModelClient(updatedEntity);
     * }
     */

    @Override
    public Client update(Long id, Client model) {
        // Check if the model contains the countryName field
        if (model.getCountryName() != null) {
            // If the countryName field is present, update the client's country
            updateClientCountry(id, model.getCountryName());
        }

        // Retrieve the target client entity
        ClientEntity targetClientEntity = clientJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Map other fields of the model to the target client entity
        targetClientEntity = (ClientEntity) mapper.mapModelEntityPatchClient(model, targetClientEntity);

        // Save and return the updated client entity
        ClientEntity updatedEntity = clientJPARepository.save(targetClientEntity);
        return (Client) mapper.mapEntityModelClient(updatedEntity);
    }

    private ClientEntity updateClientCountry(Long clientId, String countryName) {
        ClientEntity clientEntity = clientJPARepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

        CountryEntity countryEntity = countryJPARepository.findByName(countryName)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with name: " + countryName));

        clientEntity.setCountry(countryEntity);
        return clientJPARepository.save(clientEntity);
    }

    @Override
    public void deleteById(Long id) {
        clientJPARepository.deleteById(id);
    }

    private Page<Client> mapEntitiesPageToModelsPage(Page<ClientEntity> entities) {
        List<Client> clientList = entities.getContent().stream()
                .map(entity -> (Client) mapper.mapEntityModelClient(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(clientList, entities.getPageable(), entities.getTotalElements());
    }

    private ClientEntity mapAndPopulateClientEntity(ClientCreateModel clientCreateModel) {
        // Create a new ClientEntity instance
        ClientEntity clientEntity = new ClientEntity();

        // Populate fields from ClientCreateModel
        clientEntity.setName(clientCreateModel.getName());
        clientEntity.setAddress(clientCreateModel.getAddress());
        clientEntity.setCity(clientCreateModel.getCity());
        clientEntity.setPostalCode(clientCreateModel.getPostalCode());

        // Fetch CountryEntity from the database using countryId
        CountryEntity countryEntity = countryJPARepository.findByName(clientCreateModel.getCountryName())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        // Set the country for the client
        clientEntity.setCountry(countryEntity);

        return clientEntity;
    }
}
