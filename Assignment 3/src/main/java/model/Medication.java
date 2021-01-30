package model;

public class Medication {
    private Integer id;
    private String name;
    private String sideEffect;
    private String dosage;

    public Medication(Integer id, String name, String sideEffect, String dosage) {
        this.id = id;
        this.name = name;
        this.sideEffect = sideEffect;
        this.dosage = dosage;
    }

    public Medication() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id + " ");
        stringBuilder.append(name + " ");
        stringBuilder.append(sideEffect + " ");
        stringBuilder.append(dosage);
        return stringBuilder.toString();
    }
}
