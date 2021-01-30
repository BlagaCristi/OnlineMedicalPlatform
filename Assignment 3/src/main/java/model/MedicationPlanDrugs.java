package model;

public class MedicationPlanDrugs {
    private Integer id;
    private Integer medicationPlanId;
    private Integer medicationId;

    public MedicationPlanDrugs(Integer id, Integer medicationPlanId, Integer medicationId) {
        this.id = id;
        this.medicationPlanId = medicationPlanId;
        this.medicationId = medicationId;
    }

    public MedicationPlanDrugs() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMedicationPlanId() {
        return medicationPlanId;
    }

    public void setMedicationPlanId(Integer medicationPlanId) {
        this.medicationPlanId = medicationPlanId;
    }

    public Integer getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id + " ");
        stringBuilder.append(medicationId + " ");
        stringBuilder.append(medicationPlanId + " ");
        return stringBuilder.toString();
    }
}
