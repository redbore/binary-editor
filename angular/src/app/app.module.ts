import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TableComponent} from './components/table.component';
import {HttpClientModule} from '@angular/common/http';
import {ApiService} from './services/api.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PathsComponent} from "./components/paths/paths.component";

@NgModule({
    declarations: [
        AppComponent,
        TableComponent,
        PathsComponent
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
