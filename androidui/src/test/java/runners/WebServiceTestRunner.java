package runners;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class WebServiceTestRunner {
	
    @Test
    public void testApi() {
    	
        // 1. Servise istek yapılır
        Response response = given()
                  .header("Accept-Encoding", "gzip, deflate")
        	      .when()
        	      .get("https://dummyapi.online/api/movies");

        // 2. Status kodunun 200 olduğunu doğrular
        assert response.getStatusCode() == 200;

        // 3. 100 kayıt geldiğini kontrol eder
        JsonPath json = new JsonPath(response.asString());
        int kayitSayisi = json.getInt("data.size()");
        assert kayitSayisi == 100;

        // 4. id değeri 84 olan kaydın movie değerini doğrular
        String arananDeger = json.getString("find { it.id == 84 }.movie");
        assert "Jumanji: Welcome to the Jungle".equals(arananDeger);
    }
    
}
