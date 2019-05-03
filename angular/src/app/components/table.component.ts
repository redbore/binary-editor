import {Component} from '@angular/core';
import {ApiService} from '../services/api.service';
import {Editor, Paths} from '../dto/Editor';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.css']
})
export class TableComponent {
    paths: Paths;
    view: Editor;
    apiService: ApiService;
    active: boolean = true;

    constructor(apiService: ApiService) {
        this.apiService = apiService;
        this.init();
        this.getPaths();
    }

    public openTable(uuid: string) {
        this.apiService.openTable(uuid).subscribe(() => this.updateView());
    }

    public getPaths() {
        this.apiService.getPaths().subscribe(paths => this.paths = paths);
    }

    public openBinaryFile() {
        this.apiService.openBinaryFile(this.paths).subscribe(() => this.updateView());
    }

    public saveBinaryFile() {
        this.apiService.saveBinaryFile(this.paths).subscribe(() => this.updateView());
    }

    public editField(tableId: string, rowId: string, fieldId: string, event: any) {
        this.apiService.editField(tableId, rowId, fieldId, event.target.value).subscribe(() => this.updateView());
    }

    public updateView() {
        this.apiService.getView().subscribe(view => this.view = view);
    }

    public init() {
        this.paths = new Paths('', '');
    }

    public logout() {
        this.active = false;
        this.apiService.suicide().subscribe();
    }

    public isText(value: any) {
        return isNaN(value);
    }
}
