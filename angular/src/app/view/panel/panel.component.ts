import {Component} from "@angular/core";
import {EditorFile} from "../../core/domain/EditorFile";
import {FileService} from "../../core/services/file.service";

@Component({
    selector: 'app-panel',
    templateUrl: './panel.component.html',
    styleUrls: ['./panel.component.css']
})
export class PanelComponent {
    private fileService: FileService;

    constructor(fileService: FileService) {
        this.fileService = fileService;
    }

    public selectSpec(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            this.fileService.spec = new EditorFile(
                null, file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result))
            );
        }
    }

    public selectBinary(event) {
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.readAsArrayBuffer(file);

        reader.onload = () => {
            this.fileService.binary = new EditorFile(
                null, file.name, Array.from(new Int8Array(<ArrayBuffer>reader.result))
            );
        }
    }

}
