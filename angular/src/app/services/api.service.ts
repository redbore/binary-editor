import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Editor, Paths} from "../dto/Editor";

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
    return this.get(`/file`);
  }

  public openBinaryFile(paths: Paths): Observable<any> {
    return this.post(`/file/open`, paths);
  }

  public saveBinaryFile(paths: Paths): Observable<any> {
    return this.post(`/file/save`, paths);
  }

  public getPaths(): Observable<Paths> {
    return this.get(`/paths`);
  }

  public editField(tableId: string, rowId: string, fieldId: string, value: any): Observable<any> {
    return this.post(`/tables/${tableId}/rows/${rowId}/fields/${fieldId}`, value)
  }

  suicide(): Observable<any> {
    return this.post('/actuator/shutdown');
  }

  get<T>(path: string): Observable<any> {
    return this.http.get<T>(this.host + path);
  }

  post<T>(path: string, body?: any, options?: any): Observable<any> {
    return this.http.post<T>(this.host + path, body, options);
  }
}
