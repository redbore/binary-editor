import {Component} from '@angular/core';
import {ApiService} from '../services/api.service';
import {Paths} from '../dto/Paths';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  view: any;
  paths: Paths;
  apiService: ApiService;

  constructor(apiService: ApiService) {
    this.apiService = apiService;
    this.init();
    this.getPaths();
  }

  public getPaths() {
    this.apiService.getPaths().subscribe(paths => this.paths = paths);
  }

  public openBinaryFile() {
    this.apiService.openBinaryFile(this.paths).subscribe(() => this.getView());
  }

  public getView() {
    this.apiService.getView().subscribe(view => this.view = view);
  }

  public init() {
    this.paths = new Paths('', '')
  }

}
