import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class smpTest
{
    static WebDriver driver;
    String TAB_favourites = "//span[@id='favorite']";

    @BeforeAll
    public static void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void addObject() throws InterruptedException
    {
        login();

        //Кликаем на иконку  добавить в избранное
        click(TAB_favourites);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Кликаем на кнопку сохранить
        driver.findElement(By.xpath("//div[@id='gwt-debug-apply']")).click();
        Thread.sleep(2000);

        //Открываем вкладу избранное (почему-то в селениуме она открыты априори при запуске сайта)
        driver.findElement(By.xpath("//div[@id='gwt-debug-adminArea']/div[2]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Проверки
        WebElement element = driver.findElement(By.xpath("//a[@id='gwt-debug-title']/div"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "employee1 \"Столбов Антон\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("employee1 \"Столбов Антон\"/Карточка сотрудника", textElement, msg);

        //Очистка

        //Открываем редактирование избранного
        driver.findElement(By.xpath("//span[@id='gwt-debug-editFavorites']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Нажимамем на крестик
        driver.findElement(By.xpath("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Подтверждаем удаление
        driver.findElement(By.xpath("//div[@id='gwt-debug-YES']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        //Сохраняем изменения
        driver.findElement(By.xpath("//div[@id='gwt-debug-apply']")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.findElement(By.xpath("//a[contains(text(),'Выйти')]")).click();

    }


    @Test
    public void deleteObject() throws InterruptedException {

        login();
        //Кликаем на иконку  добавить в избранное
        click(TAB_favourites);
        Thread.sleep(2000);

        //Кликаем на кнопку сохранить
        driver.findElement(By.xpath("//div[@id='gwt-debug-apply']")).click();
        Thread.sleep(2000);

        //Открываем вкладу избранное (почему-то в селениуме она открыты априори при запуске сайта)
        driver.findElement(By.xpath("//div[@id='gwt-debug-adminArea']/div[2]")).click();
        Thread.sleep(2000);

        //Открываем редактирование избранного
        driver.findElement(By.xpath("//span[@id='gwt-debug-editFavorites']")).click();
        Thread.sleep(2000);

        //Нажимамем на крестик
        driver.findElement(By.xpath("//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span")).click();
        Thread.sleep(2000);

        //Подтверждаем удаление
        driver.findElement(By.xpath("//div[@id='gwt-debug-YES']")).click();
        Thread.sleep(2000);

        //Сохраняем изменения
        driver.findElement(By.xpath("//div[@id='gwt-debug-apply']")).click();
        Thread.sleep(2000);

        //Проверки
        List<WebElement> element = driver.findElements(By.xpath("//a[@id='gwt-debug-title']/div"));
        Assertions.assertTrue(element.isEmpty(), "Объект не удалился");

        driver.findElement(By.xpath("//div[2]/div[2]/div")).click();

        //Разлогиниться
        driver.findElement(By.xpath("//a[contains(text(),'Выйти')]")).click();

    }

    private void login()
    {
        driver.get("https://test-m.sd.nau.run/sd/");

        //Залогиниться
        driver.findElement(By.id("username")).sendKeys("Antonst");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.xpath("//input[@id='submit-button']")).click();
    }
    public WebElement waitElement(String xpath)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return element;
    }

    public void click(String xpath)
    {
        waitElement(xpath).click();
    }

    @AfterAll
    public static void close()
    {
        driver.close();
    }
}
