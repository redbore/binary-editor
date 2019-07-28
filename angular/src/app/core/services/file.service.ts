import {EditorFile} from "../domain/EditorFile";
import {ApiService} from "./api.service";
import {Injectable} from "@angular/core";

@Injectable()
export class FileService {
    private _binary: EditorFile;
    private _spec: EditorFile;

    constructor(apiService: ApiService) {

    }


    get binary(): EditorFile {
        return this._binary;
    }

    get spec(): EditorFile {
        return this._spec;
    }


    set binary(value: EditorFile) {
        this._binary = value;
    }

    set spec(value: EditorFile) {
        this._spec = value;
    }
}
