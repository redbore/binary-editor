import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {AvailableFiles, Editor, EditorFile} from "../dto/Editor";

@Injectable()
export class ApiService {
    host;

    constructor(private http: HttpClient) {
        this.host = environment.backendHost;
    }

    public openTable(tableId: string): Observable<any> {
        return this.post(`/tables/${tableId}`);
    }

    public getView(): Observable<Editor> {
        return this.get(`/files`);
    }

    public openBinaryFile(): Observable<any> {
        return this.post(`/files/open`);
    }

    public saveBinaryFile(): Observable<EditorFile> {
        return this.get<EditorFile>(`/files/save`);
    }

    public getAvailableFiles(): Observable<AvailableFiles> {
        return this.get(`/files/available`);
    }

    public editField(tableId: string, rowId: string, fieldId: string, value: any): Observable<any> {
        return this.post(`/tables/${tableId}/rows/${rowId}/fields/${fieldId}`, value)
    }

    public suicide(): Observable<any> {
        return this.post('/actuator/shutdown');
    }

    public updateXml(editorFile: EditorFile): Observable<any> {
        return this.post(`/files/xml/update`, editorFile);
    }

    public updateBinary(editorFile: EditorFile): Observable<any> {
        return this.post(`/files/binary/update`, editorFile);
    }

    private get<T>(path: string): Observable<T> {
        return this.http.get<T>(this.host + path);
    }

    private post<T>(path: string, body?: any, options?: any): Observable<any> {
        return this.http.post<T>(this.host + path, body, options);
    }
}
