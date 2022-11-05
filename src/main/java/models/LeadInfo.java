package models;

public class LeadInfo {

    private double salaireMinimum;
    private double salaireMaximum;
    private String dateStart;
    private String dateEnd;
    private String etat;

    public LeadInfo(double salaireMinimum, double salaireMaximum, String etat, String dateStart, String dateEnd) {
        this.salaireMinimum = salaireMinimum;
        this.salaireMaximum = salaireMaximum;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.etat = etat;
    }

    public LeadInfo() {
        this.salaireMinimum = 5000;
        this.salaireMaximum = 10000;
        this.dateStart = "1945-01-01";
        this.dateEnd = "2022-12-31";
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

}
