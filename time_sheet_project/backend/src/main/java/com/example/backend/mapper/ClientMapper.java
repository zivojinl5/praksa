package com.example.backend.mapper;

import com.example.backend.core.create_model.ClientCreateModel;
import com.example.backend.core.model.Client;
import com.example.backend.core.search_model.ClientSearchModel;
import com.example.backend.data.entity.ClientEntity;
import com.example.backend.web.create_dto.ClientCreateDTO;
import com.example.backend.web.dto.ClientDTO;
import com.example.backend.web.search_dto.ClientSearchDTO;

import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ClientMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ClientDTO mapModelDTOClient(Client Client) {
        return modelMapper.map(Client, ClientDTO.class);

    }

    public Client mapDTOModelClient(ClientDTO dto) {
        return modelMapper.map(dto, Client.class);
    }

    public ClientEntity mapModelEntityClient(Client Client) {
        return modelMapper.map(Client, ClientEntity.class);
    }

    public Client mapEntityModelClient(ClientEntity entity) {
        return modelMapper.map(entity, Client.class);
    }

    public ClientCreateModel mapCreateDTOCreateModelClient(ClientCreateDTO createDTO) {
        return modelMapper.map(createDTO, ClientCreateModel.class);
    }

    public ClientSearchModel mapSearchDTOSearchModelClient(ClientSearchDTO clientSearchDTO) {
        return modelMapper.map(clientSearchDTO, ClientSearchModel.class);

    }

    public ClientEntity mapCreateModelEntityClient(ClientCreateModel clientCreateModel) {
        return modelMapper.map(clientCreateModel, ClientEntity.class);

    }

    public ClientEntity mapModelEntityPatchClient(Client source, ClientEntity destination) {
        // Configure ModelMapper to skip null properties for this specific mapping
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

}
