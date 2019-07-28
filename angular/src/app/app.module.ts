import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {TableComponent} from './view/table/table.component';
import {HttpClientModule} from '@angular/common/http';
import {ApiService} from './core/services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PanelComponent} from "./view/panel/panel.component";
import {FileService} from "./core/services/file.service";

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
        FileService
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
