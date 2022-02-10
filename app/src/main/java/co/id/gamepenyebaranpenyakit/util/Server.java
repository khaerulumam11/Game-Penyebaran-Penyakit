package co.id.gamepenyebaranpenyakit.util;

public class Server {
    public static final String BASE_IP= "http://192.168.1.14/game/";
    public static final String ENDPOINT_LOGIN = "http://192.168.1.26/dengue_buster/login.php";
    public static final String ENDPOINT_REGISTER = "http://192.168.1.26/dengue_buster/registrasi.php";
    public static final String ENDPOINT_GET_RANKING_USER = "http://192.168.1.26/dengue_buster/getAllUserByScore.php";
    public static final String ENDPOINT_GET_DETAIL_USER = "http://192.168.1.26/dengue_buster/getUserDetail.php?id=";
    public static final String ENDPOINT_GET_CASE = "http://192.168.1.26/dengue_buster/case.php";
    public static final String ENDPOINT_GET_CITIES = "http://192.168.1.26/dengue_buster/getCities.php";
    public static final String ENDPOINT_GET_CITIES_BY_NAME = "http://192.168.1.26/dengue_buster/getCitiesByName.php?name=";
    public static final String ENDPOINT_UPDATE_SCORE = "http://192.168.1.26/dengue_buster/updateScoreUser.php?id=";
    public static final String ENDPOINT_GET_SOLUTION = "http://192.168.43.76/game/getSolution.php";
    public static final String ENDPOINT_ADD_SOLUTION = "http://192.168.43.76/game/addSolution.php";

}
