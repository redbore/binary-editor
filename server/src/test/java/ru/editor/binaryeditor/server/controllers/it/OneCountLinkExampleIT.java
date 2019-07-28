package ru.editor.binaryeditor.server.controllers.it;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.TableDescription;
import ru.editor.binaryeditor.server.controllers.EditorOperationsTest;
import ru.editor.binaryeditor.server.controllers.EditorOperationsVerifier;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static rest.TableDescription.tableDescription;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public class OneCountLinkExampleIT extends AbstractTestNGSpringContextTests implements EditorOperationsTest {

    @Value("classpath:examples/one_count_link_example")
    private Resource binaryFile;

    @Value("classpath:examples/one_count_link_example.xml")
    private Resource specification;

    @Autowired
    private EditorOperationsVerifier verifier;

    @BeforeClass
    public void init() {
        verifier.workFiles(binaryFile, specification);
    }

    @Test
    @Override
    public void testOpenFile() throws IOException {
        verifier.verifyOpenFile();
    }

    @Test
    @Override
    public void testReplaceOneFile() throws IOException {
        verifier.verifyReplaceOneFile();
    }

    @Test
    @Override
    public void testViewFile() throws IOException {
        List<TableDescription> expectedTableDescriptions = newArrayList(
                tableDescription()
                        .name("header")
                        .columnNames(newArrayList("version", "segment_count", "file_type"))
                        .rowCount(1)
                        .build(),

                tableDescription()
                        .name("segment")
                        .columnNames(newArrayList(
                                "id", "Chance_0", "Chance_1", "Chance_2", "Chance_3",
                                "Type_0", "Type_1", "Type_2", "Type_3", "Value_0",
                                "Value_1", "Value_2", "Value_3",
                                "Amount_0", "Amount_1", "Amount_2", "Amount_3",
                                "DataTo_0", "DataTo_1", "DataTo_2", "DataTo_3",
                                "IconName_0", "IconName_1", "IconName_2", "IconName_3"
                        ))
                        .rowCount(10)
                        .build());
        verifier.verifyView(expectedTableDescriptions);
    }

    @Test(dataProvider = "paginationData")
    @Override
    public void testPagination(
            String tableName, Long rowCount, Long pageNumber, Long expectedRowCount
    ) throws IOException {
        verifier.verifyPagination(tableName, rowCount, pageNumber, expectedRowCount);
    }

    @Test(dataProvider = "fieldEditData")
    @Override
    public void testFieldEdit(
            String tableName, Long rowCount, Long pageNumber, String columnName, Long rowNumber, String newValue
    ) throws IOException {
        verifier.verifyFieldEdit(tableName, rowCount, pageNumber, columnName, rowNumber, newValue);
    }

    @Test
    @Override
    public void testSaveFile() throws IOException {
        verifier.verifySaveFile();
    }

    @DataProvider(name = "fieldEditData")
    public Object[][] fieldEditData() {
        return new Object[][]{
                new Object[]{"header", 1L, 1L, "version", 1L, "10"},
                new Object[]{"header", 1L, 1L, "file_type", 1L, "BIN"},
                new Object[]{"segment", 10L, 1L, "id", 1L, "999"},
                new Object[]{"segment", 5L, 2L, "IconName_0", 3L, "IconName_0_test_value"}
        };
    }

    @DataProvider(name = "paginationData")
    public Object[][] paginationData() {
        return new Object[][]{
                new Object[]{"header", 1L, 1L, 1L},
                new Object[]{"header", 1L, 2L, 0L},
                new Object[]{"segment", 4L, 3L, 2L},
                new Object[]{"segment", 3L, 4L, 1L},
                new Object[]{"segment", 3L, 5L, 0L},
                new Object[]{"segment", 5L, 2L, 5L},
                new Object[]{"segment", 1L, 7L, 1L}
        };
    }
}
