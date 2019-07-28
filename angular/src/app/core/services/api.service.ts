import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {EditorFile} from "../domain/EditorFile";

@Injectable()
export class ApiService {
    host;

    constructor(private http: HttpClient) {
        this.host = environment.backendHost;
    }

    public open(binary: EditorFile, spec: EditorFile): Observable<any> {
        return this.post("/open", {
            specification: spec,
            binary_file: binary
        })
    }

    private get<T>(path: string): Observable<T> {
        return this.http.get<T>(this.host + path);
    }

    private post<T>(path: string, body?: any, options?: any): Observable<any> {
        return this.http.post<T>(this.host + path, body, options);
    }
}
