package resources.bean;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import resources.model.Client;
import resources.repository.ClientDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ClientBean implements Serializable {
    @Inject
    private ClientDAO clientDAO;

    private Client selectedClient;

    public List<Client> getClients() {
        List<Client> asd = clientDAO.getAllClients();
        System.out.println(asd);
        return asd;
    }

    @Transactional
    public void saveClient(Client client) {
        if (client == null) {
            return;
        }
        if (client.getId() == 0)
            clientDAO.createClient(client);
        else
            clientDAO.updateClient(client);
    }

    @Transactional
    public void deleteClient(Client client) {
        clientDAO.deleteClientById(client.getId());
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void prepareNewClient() {
        selectedClient = new Client();
    }
}
