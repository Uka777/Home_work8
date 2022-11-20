package guru.qa;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import static org.assertj.core.api.Assertions.assertThat;
public class ParsTestHW {
    ClassLoader cl = FileParsTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        InputStream is = cl.getResourceAsStream("folder/HW.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile("src/test/resources/folder/HW.zip");
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String entryName = entry.getName();
            if (entryName.contains("BIO.csv")) {
                try (InputStream inputStream = zipFile.getInputStream(entry);
                     CSVReader reader = new CSVReader(new InputStreamReader(inputStream));) {
                    List<String[]> content = reader.readAll();
                    String[] row = content.get(1);
                    assertThat(row[0]).isEqualTo("unit of measurement");
                }
            }
        }
    }

    @Test
    void zipXls() throws Exception {
        InputStream is = cl.getResourceAsStream("folder/HW.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile("src/test/resources/folder/HW.zip");
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains("OAK.xls")) {
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(inputStream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(1)
                            .getCell(1)
                            .getStringCellValue())
                            .isEqualTo("general blood test");
                }
            }
        }
    }

    @Test
    void zipPdf() throws Exception {
        InputStream is = cl.getResourceAsStream("folder/HW.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipFile zipFile = new ZipFile("src/test/resources/folder/HW.zip");
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains("SOE.pdf")) {
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(inputStream);
                    assertThat(pdf.text).isEqualTo("erythrocyte sedimentation rate");
                }
            }
        }
    }

    @Test
    void jsonTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("folder/Analyzes.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Analyzes analyzes = objectMapper.readValue(is, Analyzes.class);
            assertThat(analyzes.name).isEqualTo("Veta");
            assertThat(analyzes.referenceValues).isEqualTo(120);
            assertThat(analyzes.isStudent).isEqualTo(true);
            assertThat(analyzes.subjects[1]).isEqualTo("Art");
            assertThat(analyzes.studentID.get("number")).isEqualTo("123456");

        }
    }
}



                /*if (entryName.contains("OAK.xlsx")) {
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    XLS xls = new XLS(inputStream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(1).getCell(1)
                            .getStringCellValue())
                            .isEqualTo("general blood test");
                }
            }
            if (entryName.contains("SOE.pdf")) {
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    PDF pdf = new PDF(inputStream);
                    assertThat(pdf.text).isEqualTo("erythrocyte sedimentation rate");
                }
            }
        }
    }
}*/
