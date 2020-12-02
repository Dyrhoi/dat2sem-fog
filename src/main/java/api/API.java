package api;

import domain.material.MaterialRepository;

import java.util.List;

public class API {
    private final MaterialRepository materialRepository;

    public API(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public List<String> getRoofTypes() {
        return materialRepository.getRootTypes();
    }
}
