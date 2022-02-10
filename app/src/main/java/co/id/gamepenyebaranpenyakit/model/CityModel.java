package co.id.gamepenyebaranpenyakit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {

    @Expose
    @SerializedName("result")
    private List<ResultEntity> result;

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public class ResultEntity {
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("prov_id")
        private String prov_id;
        @Expose
        @SerializedName("id")
        private String id;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProv_id() {
            return prov_id;
        }

        public void setProv_id(String prov_id) {
            this.prov_id = prov_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
