package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import rest.*;
import ru.editor.binaryeditor.core.dao.FieldDescriptionDao;
import ru.editor.binaryeditor.core.domain.FieldDescription;
import ru.editor.binaryeditor.core.services.Editor;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static ru.editor.binaryeditor.server.controllers.EditorConverter.*;
import static ru.editor.binaryeditor.server.controllers.Endpoint.*;

@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EditorController {

    private final Editor editor;
    private final FieldDescriptionDao fieldDescriptionDao;

    @PostMapping(OPEN)
    @ResponseStatus(OK)
    public void open(@RequestBody OpenFile openFile) throws Exception {
        editor.open(
                toDomainBinaryFile(openFile.getBinaryFile()),
                toDomainSpecification(openFile.getSpecification())
        );
    }

    @GetMapping(VIEW)
    @ResponseStatus(OK)
    public View view() {
        ru.editor.binaryeditor.core.domain.View view = editor.view();
        return toView(view);
    }

    @PutMapping(FIELD_EDIT)
    @ResponseStatus(OK)
    public void fieldEdit(@PathVariable("field_id") UUID fieldId, @RequestBody FieldEdit fieldEdit) {
        editor.fieldEdit(fieldId, fieldEdit.getValue());
    }

    @PostMapping(SAVE)
    @ResponseStatus(OK)
    public File save() {
        return EditorConverter.toFile(editor.save());
    }

    @GetMapping(PAGINATION)
    @ResponseStatus(OK)
    public List<Row> pagination(
            @PathVariable(value = "table_id") UUID segmentId,
            @RequestParam(value = "page_number") Long pageNumber,
            @RequestParam(value = "row_count") Long rowCount
    ) {
        Long count = fieldDescriptionDao.getCount(segmentId);
        Long limit = rowCount * count;
        Long offset = limit * (pageNumber - 1);

        List<FieldDescription> fieldDescriptions = fieldDescriptionDao.getAllBySegmentId(segmentId);
        return toRows(editor.pagination(segmentId, limit, offset), fieldDescriptions.size());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(BAD_REQUEST)
    public String exception(Exception e) {
        log.error(e);
        return e.getMessage();
    }
}
