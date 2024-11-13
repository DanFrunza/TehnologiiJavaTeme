package resources.bean;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import resources.model.GenericData;
import resources.repository.GenericDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class GenericBean implements Serializable {
    @Inject
    private GenericDAO genericDAO;

    private GenericData currentData = new GenericData();

    public void saveData() {
        genericDAO.saveData(currentData);
    }

    public GenericData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(GenericData currentData) {
        this.currentData = currentData;
    }

    public List<GenericData> getAllData() {
        return genericDAO.getAllData();
    }

    public String getGoHome() {
        return "page";
    }

    public String getGoToDataView() {
        return "dataView";
    }

    public String getGoToDataEdit() {
        return "dataEdit";
    }
}
