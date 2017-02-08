package structures;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by loke09 on 10/01/17.
 */
public class Name_Master implements Serializable {
    @SerializedName("SNO")
    private int SNO;
    @SerializedName("NAME")
    private String NAME;
    @SerializedName("MOBILE_NUMBER")
    private String MOBILE_NUMBER;
    @SerializedName("DATE")
    private String DATE;
    @SerializedName("GENDER")
    private String GENDER;

    public int getSNO() {
        return SNO;
    }

    public void setSNO(int SNO) {
        this.SNO = SNO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }
}
