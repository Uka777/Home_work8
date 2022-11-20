package guru.qa;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.model.Teacher;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
public class FileParsTest {
    ClassLoader cl = FileParsTest.class.getClassLoader();//конструкция для хождения в ресурсы за файлами
    //classloader загружает классы и ищет файлы в папке resources ищет их по classpath - это src\test\java и resources
    //возваращет ответа в виде inputstream
    //inputstream - побайтовое чтение файлов, reader - посимвольное чтение файлов
    @Test
    void pdfTest() throws Exception{
        open("https://junit.org/junit5/docs/current/user-guide/");
        File downloadedFile = $("a[href*='junit-user-guide-5.9.1.pdf']").download();//*= - означает, что ищет вхождение слова"junit-user-guide-5.9.1.pdf"
        PDF pdf = new PDF(downloadedFile);
        assertThat(pdf.author).contains("Sam Brannen");
    }
    @Test
    void xlsTest() throws Exception{
        InputStream is = cl.getResourceAsStream("folder/example.xlsx");
        XLS xls = new XLS(is);
        assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue())
                .isEqualTo("Общий анализ крови СОКРАЩЁННЫЙ");
        //какая таблица по счету, какая строка, какой столбец
            }
            @Test
    void csvTest() throws Exception{
                InputStream is = cl.getResourceAsStream("folder/qa_guru.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(is));
                List<String[]> content = reader.readAll();
                String[] row = content.get(1);
                assertThat(row[0]).isEqualTo("Tuchs");
                assertThat(row[1]).isEqualTo("Junit 5");
            }
            @Test
    void zipTest() throws IOException {
                InputStream is = cl.getResourceAsStream("folder/test.zip");
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) { //Цикл по поиску файла зип иначе null
                    System.out.println(entry.getName());
                }
            }
            @Test
    void jsonTest(){
                InputStream is = cl.getResourceAsStream("folder/Teacher.json");
                Gson gson = new Gson();
                JsonObject   jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
                assertThat(jsonObject.get("name").getAsString()).isEqualTo("Dmitrii");
                assertThat(jsonObject.get("isGoodTeacher").getAsBoolean()).isTrue();
                assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(1234567);
            }
    @Test//тест с моделю в классе "teacher"
    void jsonTestWithModel(){
        InputStream is = cl.getResourceAsStream("folder/Teacher.json");
        Gson gson = new Gson();
        Teacher  teacher = gson.fromJson(new InputStreamReader(is), Teacher.class);
        assertThat(teacher.name).isEqualTo("Dmitrii");
        assertThat(teacher.isGoodTeacher).isTrue();
        assertThat(teacher.passport.number).isEqualTo(1234567);
    }
}
