import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {EditorFile} from "../domain/EditorFile";
import {View} from "../domain/View";
import {Row} from "../domain/Row";

@Injectable()
export class ApiService {
    host;

    constructor(private http: HttpClient) {
        this.host = environment.backendHost;
    }

    public save(): Observable<EditorFile> {
        return this.post("/save")
    }

    public open(binary: EditorFile, spec: EditorFile): Observable<any> {
        return this.post("/open", {
            specification: spec,
            binary_file: binary
        })
    }

    public pagination(tableId: string, pageNumber: number, rowCount: number): Observable<Array<Row>> {
        return this.get("/pagination/tables/" + tableId + "?page_number=" + pageNumber + "&row_count=" + rowCount)
    }

    public view(): Observable<View> {
        return this.get("/view");
    }

    public editField(id: string, fieldValue: string): Observable<any> {
        return this.put("/fields/" + id, {value: fieldValue})
    }

    private get<T>(path: string): Observable<any> {
        return this.http.get<T>(this.host + path);
    }

    private post<T>(path: string, body?: any, options?: any): Observable<any> {
        return this.http.post<T>(this.host + path, body, options);
    }

    private put<T>(path: string, body?: any, options?: any): Observable<any> {
        return this.http.put<T>(this.host + path, body, options);
    }
}
