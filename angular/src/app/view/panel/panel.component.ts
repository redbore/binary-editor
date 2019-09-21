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

    public readonly panelService: PanelService;
    public readonly errorHandler: ErrorHandler;
    public readonly paginationService: PaginationService;
    public readonly tableService: TableService;

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

    public goToStart() {
        this.paginationService.goToStart();
        this.tableService.getRows();
    }

    public goToEnd() {
        this.paginationService.goToEnd();
        this.tableService.getRows();
    }

    public selectTable(tableId: string) {
        this.tableService.selectTable(tableId);
    }

    public selectRowCount(selectedRowCount: number) {
        this.paginationService.selectRowCount(selectedRowCount);
        this.tableService.getRows();
    }

    public selectPageNumber(pageNumber: number) {
        this.paginationService.selectPageNumber(pageNumber);
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
