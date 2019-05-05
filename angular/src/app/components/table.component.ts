import {Component} from '@angular/core';
import {ApiService} from '../services/api.service';
import {Editor} from '../dto/Editor';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.css']
})
export class TableComponent {
    private view: Editor;
    private apiService: ApiService;
    private active: boolean = true;

    constructor(apiService: ApiService) {
        this.apiService = apiService;
        this.updateView();
    }

    public openTable(uuid: string) {
        this.apiService.openTable(uuid).subscribe(() => this.updateView());
    }

    public editField(tableId: string, rowId: string, fieldId: string, event: any) {
        this.apiService.editField(tableId, rowId, fieldId, event.target.value).subscribe(() => this.updateView());
    }

    public updateView() {
        this.apiService.getView().subscribe(view => this.view = view);
    }

    public logout() {
        this.active = false;
    }

}
