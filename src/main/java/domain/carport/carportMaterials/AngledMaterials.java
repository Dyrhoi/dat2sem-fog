package domain.carport.carportMaterials;

import domain.carport.Carport;
import domain.material.dao.MaterialDAO;

import java.util.List;

import static domain.carport.carportMaterials.MaterialCalculations.*;

public class AngledMaterials {


    /*
    Vinklet tag uden skur:
    97 x 97 mm tryk : Stolper 4 + 2 pr 300cm længde
    45 x 195 mm 600 spærtræ : Rem 2 stk pr. 600cm
    45 x 195 mm 600 : Spær carport længde / med 55cm
    25 x 200 mm 540 : Under sternbræt side 2 stk pr 540 cm
    25 x 125 mm 540 : Over sternbræt side 2 stk pr 540 cm
    25 x 200 mm 360 : Under sternbræt front 2 stk pr 360 cm
    25 x 125 mm 360 : Over sternbræt front 2 stk pr 360 cm

    Hulbånd 2 stk.
    Spær beslag 1 højre & 1 venstre for hvert spær
    4,5 x 60 mm skruer 1 pakke
    4,0 x 50 mm beslagskruer 250 / (antal spær x 6)


     */
     public List calcAngled(MaterialDAO repo, Carport carport) {

         return null;
     }


    /*
    Vinklet tag med skur:
    97 x 97 mm tryk : Stolper 4 + 2 pr 300cm længde
    45 x 195 mm 600 spærtræ : Rem 2 stk pr. 600cm
    45 x 195 mm 600 : Spær carport længde / med 55cm
    25 x 200 mm 540 : Under sternbræt side 2 stk pr 540 cm
    25 x 125 mm 540 : Over sternbræt side 2 stk pr 540 cm
    25 x 200 mm 360 : Under sternbræt front 2 stk pr 360 cm
    25 x 125 mm 360 : Over sternbræt front 2 stk pr 360 cm
    45 x 95 mm 270 : Løsholter gavl skur bredde / med 270 x 2
    45 x 95 mm 240 : Løsholter skur sider længde / 240 x 2
    19 x 100 mm 210 : Beklædning af skur inder bræt bredde + længde x 2 / 44cm
    19 x 100 mm 210 : Beklædning af skur yder bræt ovennævnte antal / med 2
    38 x 73 mm 420 : Til z på dør 1 stk.

    Hulbånd 2 stk.
    Spær beslag 1 højre & 1 venstre for hvert spær
    Vinkelbeslag 35 : Sum af løsholter x 2
    4,5 x 60 mm skruer 1 pakke
    4,0 x 50 mm beslagskruer 250 / (antal spær x 6)
    4,5 x 50 mm skruer beklædning inderbræt antal brædder x 3 (300 pr pakke)
    4,5 x 70 mm skruer beklædning yderbræt antal brædder x 6 (400 pr pakke)
    stalddørsgreb 1 stk
    t hængsel 2 stk

    bræddebolt 2 pr stolpe + 2 ekstra pr ekstra rem
    firkantskive 2 pr stolpe + 2 ekstra pr ekstra rem


     */
}
