import {Component, OnInit} from "@angular/core";
import {EditorFile} from "../../core/domain/EditorFile";
import {ErrorHandler} from "../../core/services/error.handler";
import {PanelService} from "../../core/services/panel.service";
import {PaginationService} from "../../core/services/pagination.service";
import {TableService} from "../../core/services/table.service";

@Component({
    selector: 'app-panel',
    templateUrl: './panel.component.html',
    styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {

    private readonly panelService: PanelService;
    private readonly errorHandler: ErrorHandler;
    private readonly paginationService: PaginationService;
    private readonly tableService: TableService;

    constructor(
        panelService: PanelService,
        errorHandler: ErrorHandler,
        paginationService: PaginationService,
        tableService: TableService
    ) {
        this.panelService = panelService;
        this.errorHandler = errorHandler;
        this.paginationService = paginationService;
        this.tableService = tableService;
    }

    public open() {
        this.errorHandler.handle(() => {
            this.panelService.openFiles();
        })
    }

    public selectTable(tableId: String) {
        this.tableService.selectTable(tableId);
    }

    public selectViewRowCount(viewRowCount: number) {
        this.paginationService.setViewRowCount(viewRowCount);
        this.tableService.getRows();
    }

    public selectPageNumber(pageNumber: number) {
        this.paginationService.setPageNumber(pageNumber);
        this.tableService.getRows();
    }

    public selectSpec(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            this.panelService.spec = new EditorFile(file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result)));
        }
    }

    public selectBinary(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            this.panelService.binary = new EditorFile(file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result)));
        }
    }

    ngOnInit(): void {
        this.panelService.view();
    }

}
