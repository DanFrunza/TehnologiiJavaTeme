package resources.repository;

import jakarta.enterprise.inject.Model;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import resources.model.GenericData;

import java.util.List;

@Model
public class GenericDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<GenericData> getAllData() {
        return entityManager.createQuery("SELECT d FROM GenericData d", GenericData.class).getResultList();
    }

    public void saveData(GenericData d) {
        entityManager.persist(d);
    }
}
