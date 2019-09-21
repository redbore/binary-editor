import {Injectable} from "@angular/core";
import {PaginationService} from "./pagination.service";
import {ApiService} from "./api.service";
import {TableDescription} from "../domain/TableDescription";
import {Row} from "../domain/Row";

@Injectable()
export class TableService {

    selectedTable: TableDescription;
    tablesDescriptions: Array<TableDescription>;
    rows: Array<Row>;

    private readonly paginationService: PaginationService;
    private readonly apiService: ApiService;

    constructor(paginationService: PaginationService, apiService: ApiService) {
        this.paginationService = paginationService;
        this.apiService = apiService;
    }

    public selectTable(id: string) {
        this.selectedTable = this.tablesDescriptions.find(td => td.id === id);
        this.paginationService.maxRowCount = this.selectedTable.row_count;
        this.paginationService.calculatePageNumbers();
        this.getRows();
    }

    public getRows() {
        console.log(this.selectedTable.id, this.paginationService.selectedPageNumber, this.paginationService.selectedRowCount);
        this.apiService.pagination(
            this.selectedTable.id, this.paginationService.selectedPageNumber, this.paginationService.selectedRowCount
        ).subscribe(resp => {
            console.log(resp);
            this.rows = resp
        });
    }
}
