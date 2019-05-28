package ar.edu.itba.sia.equipment;

public abstract class Item {

    private int id;
    private double strength;
    private double agility;
    private double expertise;
    private double resilience;
    private double vitality;

    /* package */ Item(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        this.id = id;
        this.strength = strength;
        this.agility = agility;
        this.expertise = expertise;
        this.resilience = resilience;
        this.vitality = vitality;
    }

    public static Boots newBoots(int  id, double strength, double agility, double expertise, double resilience, double vitality) {
        return new Boots(id, strength, agility, expertise, resilience, vitality);
    }

    public static BreastPlate newBreastPlate(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        return new BreastPlate(id, strength, agility, expertise, resilience, vitality);
    }

    public static Gloves newGloves(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        return new Gloves(id, strength, agility, expertise, resilience, vitality);
    }

    public static Helmet newHelmet(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        return new Helmet(id, strength, agility, expertise, resilience, vitality);
    }

    public static Weapon newWeapon(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        return new Weapon(id, strength, agility, expertise, resilience, vitality);
    }

    public int getId(){
        return id;
    }

    public double getStrength() {
        return strength;
    }

    public double getAgility() {
        return agility;
    }

    public double getExpertise() {
        return expertise;
    }

    public double getResilience() {
        return resilience;
    }

    public double getVitality() {
        return vitality;
    }
}
