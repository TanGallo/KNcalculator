package ca.gotchasomething.knitfits.data;
//Database Description

public class ProjectsDb {
    private String name;
    private byte[] image;
    private String unit;
    private String pws;
    private String pwi;
    private String plr;
    private String pli;
    private String gwi;
    private String gli;
    private long id;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUnit(String unit) { this.unit = unit; }

    public void setPws(String pws) {
        this.pws = pws;
    }

    public void setPwi(String pwi) {
        this.pwi = pwi;
    }

    public void setPlr(String plr) {
        this.plr = plr;
    }

    public void setPli(String pli) {
        this.pli = pli;
    }

    public void setGwi(String gwi) {
        this.gwi = gwi;
    }

    public void setGli(String gli) {
        this.gli = gli;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProjectsDb(
            String name,
            byte[] image,
            String unit,
            String pws,
            String pwi,
            String plr,
            String pli,
            String gwi,
            String gli,
            long id) {

        this.name = name;
        this.image = image;
        this.unit = unit;
        this.pws = pws;
        this.pwi = pwi;
        this.plr = plr;
        this.pli = pli;
        this.gwi = gwi;
        this.gli = gli;
        this.id = id;
    }

    public String getName() { return name; }
    public byte[] getImage() { return image; }
    public String getUnit() { return unit; }
    public String getPws() { return pws; }
    public String getPwi() { return pwi; }
    public String getPlr() { return plr; }
    public String getPli() { return pli; }
    public String getGwi() { return gwi; }
    public String getGli() { return gli; }
    public long getId() { return id; }


    @Override
    public String toString() {
        return getName();
    }
}
