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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class smpTest
{
    static WebDriver driver;
    String TAB_add_favourites = "//span[@id='favorite']";
    String TAB_save = "//div[@id='gwt-debug-apply']";
    String Tab_favourites = "//div[@id='gwt-debug-adminArea']/div[2]";
    String Tab_edit_favorites = "//span[@id='gwt-debug-editFavorites']";
    String TAB_cross_icon = "//table[@id='gwt-debug-favoritesEditTable']/tbody/tr/td[6]/div/span";
    String TAB_confirm_delete = "//div[@id='gwt-debug-YES']";
    String TAB_save_changes = "//div[@id='gwt-debug-apply']";
    String TAB_exit = "//a[contains(text(),'Выйти')]";


    @BeforeAll
    public static void setUp() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    @Test
    public void addObject() throws InterruptedException {

        login();

        //Кликаем на иконку  добавить в избранное
        click(TAB_add_favourites);

        //Кликаем на кнопку сохранить
        click(TAB_save);
        Thread.sleep(2000);

        //Открываем вкладу избранное (почему-то в селениуме она открыты априори при запуске сайта)
        click(Tab_favourites);

        //Проверки
        WebElement element = driver.findElement(By.xpath("//a[@id='gwt-debug-title']/div"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "employee1 \"Столбов Антон\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("employee1 \"Столбов Антон\"/Карточка сотрудника", textElement, msg);

        //Очистка

            //Открываем редактирование избранного
            click(Tab_edit_favorites);

            //Нажимамем на крестик
             click(TAB_cross_icon);

            //Подтверждаем удаление
            click(TAB_confirm_delete);

            //Сохраняем изменения
            click(TAB_save_changes);

        //Разлогиниться
        click(TAB_exit);
    }


    @Test
    public void deleteObject() throws InterruptedException {

        login();

        //Кликаем на иконку  добавить в избранное
        click(TAB_add_favourites);

        //Кликаем на кнопку сохранить
        click(TAB_save);
        Thread.sleep(2000);

        //Открываем вкладу избранное (почему-то в селениуме она открыты априори при запуске сайта)
        click(Tab_favourites);

        //Открываем редактирование избранного
        click(Tab_edit_favorites);

        //Нажимамем на крестик
        click(TAB_cross_icon);

        //Подтверждаем удаление
        click(TAB_confirm_delete);

        //Сохраняем изменения
        click(TAB_save_changes);
        Thread.sleep(2000);

        //Проверки
        List<WebElement> element = driver.findElements(By.xpath("//a[@id='gwt-debug-title']/div"));
        Assertions.assertTrue(element.isEmpty(), "Объект не удалился");

        //Закрываем вкладу избранного
        click("//div[2]/div[2]/div");

        //Разлогиниться
        click(TAB_exit);

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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
