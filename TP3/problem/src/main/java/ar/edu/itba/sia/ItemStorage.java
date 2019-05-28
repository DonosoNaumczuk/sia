package ar.edu.itba.sia;

import ar.edu.itba.sia.equipment.*;

public class ItemStorage {

    private static ItemStorage instance;

    private Boots[] boots;
    private BreastPlate[] breastPlates;
    private Gloves[] gloves;
    private Helmet[] helmets;
    private Weapon[] weapons;

    private ItemStorage() { }

    public static ItemStorage getInstance() {
        if(instance == null)
            instance = new ItemStorage();
        return instance;
    }

    /* package */ void setBoots(Boots[] boots) {
        this.boots = boots;
    }

    /* package */ void setBreastPlates(BreastPlate[] breastPlates) {
        this.breastPlates = breastPlates;
    }

    /* package */ void setGloves(Gloves[] gloves) {
        this.gloves = gloves;
    }

    /* package */ void setHelmets(Helmet[] helmets) {
        this.helmets = helmets;
    }

    /* package */ void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    public Boots[] getBoots() {
        return boots;
    }

    public BreastPlate[] getBreastPlates() {
        return breastPlates;
    }

    public Gloves[] getGloves() {
        return gloves;
    }

    public Helmet[] getHelmets() {
        return helmets;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }
}
