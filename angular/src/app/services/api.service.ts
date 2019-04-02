import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Paths} from '../dto/Paths';
import {BinaryFile} from "../dto/BinaryFile";
import {SaveBinaryFile} from "../dto/SaveBinaryFile";

@Injectable()
export class ApiService {
  host;

  constructor(private http: HttpClient) {
    this.host = environment.backendHost;
  }

  getPaths(): Observable<Paths> {
    return this.get('/paths');
  }

  openBinaryFile(paths: Paths): Observable<BinaryFile> {
    return this.post('/binary-file', paths);
  }

  saveBinaryFile(saveBinaryFile: SaveBinaryFile): Observable<any> {
    return this.put('/binary-file', saveBinaryFile);
  }

  suicide(): Observable<any> {
    return this.post('/suicide');
  }

  put<T>(path: string, body?: any, options?: any): Observable<any> {
    return this.http.put<T>(this.host + path, body, options);
  }

  get<T>(path: string): Observable<any> {
    return this.http.get<T>(this.host + path);
  }

  post<T>(path: string, body?: any, options?: any): Observable<any> {
    return this.http.post<T>(this.host + path, body, options);
  }
}
