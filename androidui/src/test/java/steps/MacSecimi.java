package steps;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MacSecimi {

    private AndroidDriver driver;
    private boolean ms1SecildiMi;
    private String evSahibi;
    private String deplasman;

    @Given("Uygulamayi ac")
    public void uygulamayiAc() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "15");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("appPackage", "com.bilyoner.app");
        caps.setCapability("appActivity", "com.bilyoner.ui.main.MainActivity");
        caps.setCapability("autoGrantPermissions", "true");
        caps.setCapability("noReset", "true");
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
    }

    @When("Giris ekrani acilinca")
    public void girisEkraniAc() {
    	WebElement girisButonu = driver.findElement(By.id("com.bilyoner.app:id/buttonToolbarLogin"));
    	girisButonu.click();
    }

    @And("Kullanici bilgileri girilince")
    public void kullaniciBilgileriGir() throws InterruptedException {

    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.bilyoner.app:id/editTextIdentity")));

		WebElement kullaniciAdiAlani = driver.findElement(By.id("com.bilyoner.app:id/editTextIdentity"));
		kullaniciAdiAlani.sendKeys("userid");
		
		WebElement sifreHintAlani = driver.findElement(By.xpath("(//android.widget.RelativeLayout[@resource-id=\"com.bilyoner.app:id/hintBoxes\"])[2]"));
		sifreHintAlani.click();
		
		WebElement sifreAlani = driver.findElement(By.id("com.bilyoner.app:id/editTextPassword"));
		sifreAlani.sendKeys("password");
    }

    @And("Giris yap butonuna tiklaninca")
    public void girisYapButonunaTikla() {
    	
    	WebElement girisButonu = driver.findElement(By.id("com.bilyoner.app:id/buttonSignIn"));
    	girisButonu.click();
    }

    @Then("Giris yap butonu kaybolur")
    public void girisYapButonunuKontrolEt() throws InterruptedException, TimeoutException {
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.bilyoner.app:id/drawerLayoutMain")));
        
        List<WebElement> girisButonuListesi = driver.findElements(By.id("com.bilyoner.app:id/buttonToolbarLogin"));
        assert girisButonuListesi.size() == 0;
    }
    
    @And("Kullanici adi gorulur")
    public void kullaniciAdiniKontrolEt() throws InterruptedException, TimeoutException {
    	
        List<WebElement> kullaniciAdiAlaniListesi = driver.findElements(By.id("com.bilyoner.app:id/textViewToolbarUsername"));
        assert kullaniciAdiAlaniListesi.size() > 0;
    }
    
    @And("Bakiye gorulur")
    public void bakiyeyiKontrolEt() {
    	
        List<WebElement> bakiyeAlaniListesi = driver.findElements(By.id("com.bilyoner.app:id/textViewToolbarBalanceValue1"));
        assert bakiyeAlaniListesi.size() > 0;
    }
    
    @When("Tum sporlara tiklaninca")
    public void tumSporlaraTikla() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    	WebElement tumSporlarButonu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Tüm Sporlar']")));
    	tumSporlarButonu.click();
    }
    
    @And("Voleybol secilince")
    public void voleybolSec() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    
    	WebElement voleybolButonu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Voleybol']")));
    	voleybolButonu.click();
}

    @And("İlk macin ms alani secilince")
    public void ilkMsSec() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	
    	WebElement ms1Alani = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.bilyoner.app:id/oddBoxesContainer\"])[1]/android.view.View[1]")));
    	WebElement ms2Alani = driver.findElement(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.bilyoner.app:id/oddBoxesContainer\"])[1]/android.view.View[2]"));
    	
    	if (ms1Alani.isEnabled()) {
    		ms1SecildiMi = true;
    		ms1Alani.click();
    	}
    	else {
    		ms1SecildiMi = false;
    		ms2Alani.click();
    	}
    }

    @Then("Oran bilgileri kontrol edilir")
    public void oranBilgileriniKontrolEt() {

    	// Maç bilgileri burada alınıyor.
    	WebElement evSahibiAlani = driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.bilyoner.app:id/textViewEventHomName\"])[1]"));
    	evSahibi = evSahibiAlani.getText();
    	
    	WebElement deplasmanAlani = driver.findElement(By.xpath("(//android.widget.TextView[@resource-id=\"com.bilyoner.app:id/textViewEventAwayName\"])[1]"));
    	deplasman = deplasmanAlani.getText();
    	
        String msOrani = null;
        if (ms1SecildiMi) {
            WebElement ms1Alani = driver.findElement(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.bilyoner.app:id/oddBoxesContainer\"])[1]/android.view.View[1]"));
            msOrani = ms1Alani.getText(); // "1,38"; // TODO: Appium inspector'da yaşadığım sorun sebebi ile web element'in locator'unu elde edemedim.
        } else {
            WebElement ms2Alani = driver.findElement(By.xpath("(//android.widget.LinearLayout[@resource-id=\"com.bilyoner.app:id/oddBoxesContainer\"])[1]/android.view.View[2]"));
            msOrani = ms2Alani.getText(); // "1,38"; // TODO: Appium inspector'da yaşadığım sorun sebebi ile web element'in locator'unu elde edemedim.
        }

        WebElement toplamOranAlani = driver.findElement(By.id("com.bilyoner.app:id/textViewTotalOdds"));
        String toplamOran = toplamOranAlani.getText();

        assert toplamOran.equals(msOrani);
    }

    @And("Mac sayisi kontrol edilir")
    public void macSayisiniKontrolEt() {

        WebElement macSayisiAlani = driver.findElement(By.id("com.bilyoner.app:id/textViewBetEventCount"));
        String macSayisi = macSayisiAlani.getText();

        assert "1 Maç".equals(macSayisi);
    }

    @When("Kupon detayi acilinca")
    public void kuponDetayiAc() {

    	WebElement kuponDetayButonu = driver.findElement(By.id("com.bilyoner.app:id/betFabButton"));
    	kuponDetayButonu.click();

    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.bilyoner.app:id/textViewEventName")));
    }

    @Then("Mac adi bilgileri kontrol edilir")
    public void macAdiniKontrolEt() {

        WebElement macAdiAlani = driver.findElement(By.id("com.bilyoner.app:id/textViewEventName"));
        String macAdi = macAdiAlani.getText();
        
        if (evSahibi.endsWith("..."))
        	evSahibi = evSahibi.substring(0, evSahibi.length() - 3);
        if (deplasman.endsWith("..."))
        	deplasman = deplasman.substring(0, deplasman.length() - 3);

        assert macAdi.startsWith(evSahibi) && macAdi.contains(deplasman);
    }

}