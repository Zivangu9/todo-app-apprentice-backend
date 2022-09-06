package apprentice.ivan.todoappbackend.models;

public class Metrics {
    private Long generalAvg;
    private Long lowAvg;
    private Long mediumAvg;
    private Long highAvg;

    public Metrics() {
    };

    public Metrics(Long generalAvg, Long lowAvg, Long mediumAvg, Long highAvg) {
        this.generalAvg = generalAvg;
        this.lowAvg = lowAvg;
        this.mediumAvg = mediumAvg;
        this.highAvg = highAvg;
    }

    public Long getGeneralAvg() {
        return generalAvg;
    }

    public void setGeneralAvg(Long generalAvg) {
        this.generalAvg = generalAvg;
    }

    public Long getLowAvg() {
        return lowAvg;
    }

    public void setLowAvg(Long lowAvg) {
        this.lowAvg = lowAvg;
    }

    public Long getMediumAvg() {
        return mediumAvg;
    }

    public void setMediumAvg(Long mediumAvg) {
        this.mediumAvg = mediumAvg;
    }

    public Long getHighAvg() {
        return highAvg;
    }

    public void setHighAvg(Long highAvg) {
        this.highAvg = highAvg;
    }

}
