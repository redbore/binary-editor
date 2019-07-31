import {EditorFile} from "../domain/EditorFile";
import {ApiService} from "./api.service";
import {Injectable} from "@angular/core";
import {PaginationService} from "./pagination.service";
import {TableService} from "./table.service";

@Injectable()
export class PanelService {

    binary: EditorFile;
    spec: EditorFile;

    private readonly apiService: ApiService;
    private readonly paginationService: PaginationService;
    private readonly tableService: TableService;

    constructor(apiService: ApiService, paginationService: PaginationService, tableService: TableService) {
        this.apiService = apiService;
        this.paginationService = paginationService;
        this.tableService = tableService;
    }

    public openFiles() {
        if (this.binary == null || this.spec == null) {
            throw new Error("Not all files selected");
        }
        this.apiService.open(this.binary, this.spec).subscribe(() => this.view());
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
