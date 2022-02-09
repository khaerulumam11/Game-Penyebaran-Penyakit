package co.id.gamepenyebaranpenyakit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaseModel {


    @Expose
    @SerializedName("result")
    private List<ResultEntity> result;

    public List<ResultEntity> getResult() {
        return result;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public class LocationInfoEntity {
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    public class ResultEntity {
        @Expose
        @SerializedName("requireRecuratif")
        private String requirerecuratif;
        @Expose
        @SerializedName("requirePreventif")
        private String requirepreventif;
        @Expose
        @SerializedName("requireEdukatif")
        private String requireedukatif;
        @Expose
        @SerializedName("rainfall")
        private String rainfall;
        @Expose
        @SerializedName("humidity")
        private String humidity;
        @Expose
        @SerializedName("temperature")
        private String temperature;
        @Expose
        @SerializedName("locationInfo")
        private List<LocationInfoEntity> locationinfo;
        @Expose
        @SerializedName("numberCase")
        private String numbercase;
        @Expose
        @SerializedName("subdistrict")
        private String subdistrict;
        @Expose
        @SerializedName("id_case")
        private String idCase;

        public String getRequirerecuratif() {
            return requirerecuratif;
        }

        public void setRequirerecuratif(String requirerecuratif) {
            this.requirerecuratif = requirerecuratif;
        }

        public String getRequirepreventif() {
            return requirepreventif;
        }

        public void setRequirepreventif(String requirepreventif) {
            this.requirepreventif = requirepreventif;
        }

        public String getRequireedukatif() {
            return requireedukatif;
        }

        public void setRequireedukatif(String requireedukatif) {
            this.requireedukatif = requireedukatif;
        }

        public String getRainfall() {
            return rainfall;
        }

        public void setRainfall(String rainfall) {
            this.rainfall = rainfall;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public List<LocationInfoEntity> getLocationinfo() {
            return locationinfo;
        }

        public void setLocationinfo(List<LocationInfoEntity> locationinfo) {
            this.locationinfo = locationinfo;
        }

        public String getNumbercase() {
            return numbercase;
        }

        public void setNumbercase(String numbercase) {
            this.numbercase = numbercase;
        }

        public String getSubdistrict() {
            return subdistrict;
        }

        public void setSubdistrict(String subdistrict) {
            this.subdistrict = subdistrict;
        }

        public String getIdCase() {
            return idCase;
        }

        public void setIdCase(String idCase) {
            this.idCase = idCase;
        }
    }
}
