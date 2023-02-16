package com.antask.client;

import com.antask.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDTO> findAll() {
        final List<Client> clients = clientRepository.findAll(Sort.by("id"));
        return clients.stream().map(client -> mapToDTO(client, new ClientDTO())).collect(Collectors.toList());
    }

    public ClientDTO get(final UUID id) {
        return clientRepository
            .findById(id)
            .map(client -> mapToDTO(client, new ClientDTO()))
            .orElseThrow(() -> new NotFoundException());
    }

    public UUID create(final ClientDTO clientDTO) {
        final Client client = new Client();
        mapToEntity(clientDTO, client);
        return clientRepository.save(client).getId();
    }

    public void update(final UUID id, final ClientDTO clientDTO) {
        final Client client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException());
        mapToEntity(clientDTO, client);
        clientRepository.save(client);
    }

    public void delete(final UUID id) {
        clientRepository.deleteById(id);
    }

    private ClientDTO mapToDTO(final Client client, final ClientDTO clientDTO) {
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        return clientDTO;
    }

    private Client mapToEntity(final ClientDTO clientDTO, final Client client) {
        client.setName(clientDTO.getName());
        return client;
    }
}
