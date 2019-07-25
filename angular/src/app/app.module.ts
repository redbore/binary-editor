import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TableComponent} from './components/table.component';
import {HttpClientModule} from '@angular/common/http';
import {ApiService} from './services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PanelComponent} from "./components/panel/panel.component";

@NgModule({
    declarations: [
        AppComponent,
        TableComponent,
        PanelComponent
    ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
    ],
    providers: [ApiService],
    bootstrap: [AppComponent],
})
export class AppModule {
}
