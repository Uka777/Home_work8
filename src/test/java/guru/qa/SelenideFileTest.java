package guru.qa;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SelenideFileTest {
    //static {
        //когда есть кнопка "скачать", но нет атрибута "href"
        //Configuration.fileDownload = FileDownloadMode.PROXY;}
    @Test
    void selenideFileDownloadTest() throws Exception { //конструкция для исключения "download"
        open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = $("#raw-url").download();
        //Исключения - это объекты, с помощью которых java сигнализирует об ошибке
        try (InputStream is = new FileInputStream(downloadedFile)) {// Класс "file" - определяет путь к файлу,
            byte[] fileSource = is.readAllBytes();// а "stream" - позволяет читать эти файлы
            String fileContent = new String(fileSource, StandardCharsets.UTF_8);
            assertThat(fileContent).contains("This repository is the home of the next generation of JUnit");
            //is.close(); метод "close" закрывает за собой файловый скриптер
        }
    }
    @Test
    void uploadFile() throws Exception {
        open("https://fineuploader.com/demos.html");
        $("input[type='file']").uploadFromClasspath("folder/Screenshot_2.png");
        System.out.println("");
        $("div.qq-file-info").shouldHave(text("folder/Screenshot_2.png"));
    }
}
