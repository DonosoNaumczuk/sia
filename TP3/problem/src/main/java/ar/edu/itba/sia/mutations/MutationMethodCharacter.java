package ar.edu.itba.sia.mutations;


import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.ItemStorage;
import ar.edu.itba.sia.equipment.*;
import ar.edu.itba.sia.interfaces.MutationMethod;

import java.util.ArrayList;
import java.util.Random;

public abstract class MutationMethodCharacter implements MutationMethod<CharacterChromosome> {
    double probability;

    public MutationMethodCharacter(double probability) {
        this.probability = probability;
    }

    /*package*/ ArrayList<Object> mutateAPosition(int position, ArrayList<Object> alleles) {
        Object newAllele;
        switch (position) {
            case 0:
                newAllele = 1.3 + Math.random() * (2 - 1.3);
                break;
            case 1:
                Boots[] boots = ItemStorage.getInstance().getBoots();
                int rndBoots  = new Random().nextInt(boots.length);
                newAllele     = boots[rndBoots];
                break;
            case 2:
                BreastPlate[] breastPlates = ItemStorage.getInstance().getBreastPlates();
                int rndBreastPlate         = new Random().nextInt(breastPlates.length);
                newAllele                  = breastPlates[rndBreastPlate];
                break;
            case 3:
                Gloves[] gloves = ItemStorage.getInstance().getGloves();
                int rndGloves   = new Random().nextInt(gloves.length);
                newAllele       = gloves[rndGloves];
                break;
            case 4:
                Helmet[] helmets = ItemStorage.getInstance().getHelmets();
                int rndHelmet    = new Random().nextInt(helmets.length);
                newAllele        = helmets[rndHelmet];
                break;
            case 5:
                Weapon[] weapons = ItemStorage.getInstance().getWeapons();
                int rndWeapon    = new Random().nextInt(weapons.length);
                newAllele        = weapons[rndWeapon];
                break;
            default:
                newAllele = null;//TODO: se pudre
        }
        alleles.set(position, newAllele);
        return alleles;
    }
}
