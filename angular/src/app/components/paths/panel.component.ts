import {Component} from "@angular/core";
import {AvailableFiles, EditorFile} from "../../dto/Editor";
import {ApiService} from "../../services/api.service";
import {TableComponent} from "../table.component";
import * as FileSaver from "file-saver";

@Component({
    selector: 'app-panel',
    templateUrl: './panel.component.html',
    styleUrls: ['./panel.component.css']
})
export class PanelComponent {

    private availableFiles: AvailableFiles;
    private apiService: ApiService;
    private tableComponent: TableComponent;
    private active: boolean = true;

    constructor(apiService: ApiService, tableComponent: TableComponent) {
        this.apiService = apiService;
        this.tableComponent = tableComponent;
        this.init();
        this.getAvailableFiles();
    }

    public getAvailableFiles() {
        this.apiService.getAvailableFiles().subscribe(availableFiles => this.availableFiles = availableFiles);
    }

    public openBinaryFile() {
        this.apiService.openBinaryFile().subscribe(() => this.tableComponent.updateView());
    }

    public saveBinaryFile() {
        this.apiService.saveBinaryFile().subscribe((editorFile) => {
            this.tableComponent.updateView();
            FileSaver
                .saveAs(new Blob([Int8Array.from(editorFile.body)], {type: "application/*"}),
                    editorFile.name, true);
        });
    }

    public init() {
        this.availableFiles = new AvailableFiles('', '');
    }

    public logout() {
        this.active = false;
        this.tableComponent.logout();
        this.apiService.suicide().subscribe();
    }

    public onXmlSelected(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            let editorFile = new EditorFile(file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result)));
            this.apiService.updateXml(editorFile).subscribe(() => this.getAvailableFiles());
        }
    }

    public onBinarySelected(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            let editorFile = new EditorFile(file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result)));
            this.apiService.updateBinary(editorFile).subscribe(() => this.getAvailableFiles());
        }
    }
}
