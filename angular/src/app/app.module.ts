import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {TableComponent} from './view/table/table.component';
import {HttpClientModule} from '@angular/common/http';
import {ApiService} from './core/services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PanelComponent} from "./view/panel/panel.component";
import {ErrorHandler} from "./core/services/error.handler";
import {TableService} from "./core/services/table.service";
import {PanelService} from "./core/services/panel.service";
import {PaginationService} from "./core/services/pagination.service";

@NgModule({
    declarations: [
        AppComponent,
        TableComponent,
        PanelComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [
        ApiService,
        PanelService,
        TableService,
        ErrorHandler,
        PaginationService
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
