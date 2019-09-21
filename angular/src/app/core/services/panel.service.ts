import {EditorFile} from "../domain/EditorFile";
import {ApiService} from "./api.service";
import {Injectable} from "@angular/core";
import {PaginationService} from "./pagination.service";
import {TableService} from "./table.service";
import * as FileSaver from "file-saver";
import {ErrorHandler} from "./error.handler";

@Injectable()
export class PanelService {

    binary: EditorFile;
    spec: EditorFile;

    private readonly apiService: ApiService;
    private readonly paginationService: PaginationService;
    private readonly tableService: TableService;
    private readonly errorHandler: ErrorHandler;

    constructor(apiService: ApiService, paginationService: PaginationService, tableService: TableService, errorHandler: ErrorHandler) {
        this.apiService = apiService;
        this.paginationService = paginationService;
        this.tableService = tableService;
        this.errorHandler = errorHandler;
    }

    public openFiles() {
        this.errorHandler.handle(() => {
            if (this.binary == null || this.spec == null) {
                throw new Error("Not all files selected");
            }
            this.apiService.open(this.binary, this.spec).subscribe(() => this.view());
        });
    }

    public save() {
        this.errorHandler.handle(() => {
            this.apiService.save().subscribe((editorFile) => {
                FileSaver
                    .saveAs(new Blob([Int8Array.from(editorFile.body)], {type: "application/*"}),
                        editorFile.name, true);
            });
        });
    }

    public view() {
        this.apiService.view().subscribe((resp) => {
            console.log(resp);
            this.binary = resp.binary_file;
            this.spec = resp.specification;
            this.tableService.tablesDescriptions = resp.tables_descriptions;
        });
    }
}
