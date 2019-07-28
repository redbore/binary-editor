import {Component} from '@angular/core';
import {ApiService} from "./core/services/api.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent {

    constructor(
        apiService: ApiService
    ) {
    }
}
