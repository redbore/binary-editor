import {Component} from '@angular/core';
import {PanelService} from "../../core/services/panel.service";
import {TableService} from "../../core/services/table.service";
import {Field} from "../../core/domain/Field";
import {ApiService} from "../../core/services/api.service";

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.css']
})
export class TableComponent {

    private readonly panelService: PanelService;
    private readonly tableService: TableService;
    private readonly apiService: ApiService;

    private isOpenModal: boolean = false;
    private openedField: Field;

    constructor(panelService: PanelService, tableService: TableService, apiService: ApiService) {
        this.panelService = panelService;
        this.tableService = tableService;
        this.apiService = apiService;
    }

    private openModal(field: Field) {
        this.openedField = field;
        this.isOpenModal = true;
    }

    editField() {
        this.apiService.editField(this.openedField.id, this.openedField.value).subscribe();
        this.isOpenModal = false;
        this.openedField = null;
    }

}
