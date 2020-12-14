package domain.carport.carportMaterials;

import domain.carport.Carport;
import domain.material.dao.MaterialDAO;

import java.util.List;

import static domain.carport.carportMaterials.MaterialCalculations.*;

public class AngledMaterials {

     public List calcAngled(MaterialDAO repo, Carport carport) {
         MaterialCalculations.BaseCarport tmpBaseCarport = new BaseCarport(carport);
         return null;
     }

}
