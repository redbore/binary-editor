import {Component} from '@angular/core';
import {PanelService} from "../../core/services/panel.service";
import {TableService} from "../../core/services/table.service";

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.css']
})
export class TableComponent {

    private readonly panelService: PanelService;
    private readonly tableService: TableService;

    constructor(panelService: PanelService, tableService: TableService) {
        this.panelService = panelService;
        this.tableService = tableService;
    }
}
