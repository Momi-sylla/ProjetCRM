package models;

public class LeadInfo {
    private double salaireMinimum;
    private double salaireMaximum;
    private String etat;

    public LeadInfo(double salaireMinimum, double salaireMaximum, String etat) {
        this.salaireMinimum = salaireMinimum;
        this.salaireMaximum = salaireMaximum;
        this.etat = etat;
    }

    public LeadInfo() {
        this.salaireMinimum = 0;
        this.salaireMaximum = 100000000;
        this.etat = null;
    }

    public double getSalaireMinimum() {
        return salaireMinimum;
    }

    public void setSalaireMinimum(double salaireMinimum) {
        this.salaireMinimum = salaireMinimum;
    }

    public double getSalaireMaximum() {
        return salaireMaximum;
    }

    public void setSalaireMaximum(double salaireMaximum) {
        this.salaireMaximum = salaireMaximum;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
