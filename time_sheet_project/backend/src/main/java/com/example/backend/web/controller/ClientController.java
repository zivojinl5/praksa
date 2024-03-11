package com.example.backend.web.controller;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.backend.config.JwtService;
import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.core.service.IClientService;
import com.example.backend.mapper.ClientMapper;
import com.example.backend.web.create_dto.ClientCreateDTO;
import com.example.backend.web.dto.ClientDTO;
import com.example.backend.web.search_dto.ClientSearchDTO;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/clients")
@PreAuthorize("hasRole('ADMIN')")
public class ClientController {
    private final IClientService clientService;
    private final JwtService jwtService;

    private final ClientMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id) {
        Client foundClient = clientService.getClientById(id);
        ClientDTO foundClientDTO = (ClientDTO) mapper.mapModelDTOClient(foundClient);
        return ResponseEntity.ok(foundClientDTO);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientCreateDTO createClientDTO) {
        ClientCreateModel clientCreateModel = (ClientCreateModel) mapper.mapCreateDTOCreateModelClient(createClientDTO);
        Client createdClient = clientService.createClient(clientCreateModel);
        ClientDTO createdClientDTO = (ClientDTO) mapper.mapModelDTOClient(createdClient);
        return new ResponseEntity<>(createdClientDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable("id") Long id,
            @RequestBody ClientDTO details) {
        Client Client = (com.example.backend.core.model.Client) mapper.mapDTOModelClient(details);
        Client.setId(id);
        Client updatedClient = clientService.updateClient(id, Client);

        ClientDTO updatedClientDTO = (ClientDTO) mapper.mapModelDTOClient(updatedClient);
        return ResponseEntity.ok(updatedClientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client deleted");
    }

    private Page<ClientDTO> mapModelsPageDTOsPage(Page<Client> models) {
        List<ClientDTO> clientDTOList = models.getContent().stream()
                .map(model -> (ClientDTO) mapper.mapModelDTOClient(model))
                .collect(Collectors.toList());

        return new PageImpl<>(clientDTOList, models.getPageable(), models.getTotalElements());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ClientDTO>> searchClients(
            @RequestBody ClientSearchDTO clientSearchDTO) {

        ClientSearchModel clientSearchModel = (ClientSearchModel) mapper.mapSearchDTOSearchModelClient(clientSearchDTO);
        Page<Client> clientsPage = clientService.searchClients(clientSearchModel);
        Page<ClientDTO> clientDTOsPage = mapModelsPageDTOsPage(clientsPage);

        return ResponseEntity.ok(clientDTOsPage);
    }

    @GetMapping("/names")
    public ResponseEntity<HashMap<Long, String>> getClientNamesByUserId(
            @RequestHeader("Authorization") String token) {
        Long userId = jwtService.extractId(token);
        HashMap<Long, String> clientNames = clientService.getClientNamesByUserId(userId);
        return ResponseEntity.ok(clientNames);
    }

}