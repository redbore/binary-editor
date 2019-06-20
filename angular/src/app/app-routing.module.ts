import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from './components/table.component';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'table',
        pathMatch: 'full'
    },
    {
        path: 'table',
        component: TableComponent
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
