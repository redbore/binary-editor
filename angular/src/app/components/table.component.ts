import {Component, HostListener} from '@angular/core';
import {ApiService} from '../services/api.service';
import {Paths} from '../dto/Paths';
import {BinaryFile} from "../dto/BinaryFile";
import {SaveBinaryFile} from "../dto/SaveBinaryFile";
import {Type} from "../dto/Type";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  paths: Paths;
  binaryFile: BinaryFile;
  selectedType: Type;
  apiService: ApiService;

  constructor(apiService: ApiService) {
    this.apiService = apiService;
    this.init();
    this.getPaths();
  }

  selectType(uuid: string) {
    this.selectedType = this.binaryFile.findType(uuid);
  }

  saveBinaryFile() {
    this.apiService.saveBinaryFile(new SaveBinaryFile(this.paths, this.binaryFile)).subscribe();
  }

  openBinaryFile() {
    this.apiService.openBinaryFile(this.paths).subscribe(bf => {
      this.binaryFile = bf;
      console.log(bf);
    });
  }

  getPaths() {
    this.apiService.getPaths().subscribe(p => this.paths = p);
  }

  init() {
    this.paths = new Paths('', '');
    this.binaryFile = new BinaryFile('', []);
    this.selectedType = new Type('', '', [])
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler() {
    this.apiService.suicide().subscribe();
  }
}
