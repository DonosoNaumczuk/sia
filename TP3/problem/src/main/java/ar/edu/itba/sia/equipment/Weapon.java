package ar.edu.itba.sia.equipment;

public class Weapon extends Item {
    /* package */ Weapon(int id, double strength, double agility, double expertise, double resilience, double vitality) {
        super(id, strength, agility, expertise, resilience, vitality, "Weapon");
    }
}
