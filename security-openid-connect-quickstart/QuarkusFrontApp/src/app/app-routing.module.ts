import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './page/main/main.component';
import { AppAuthGuard } from './service/AppAuthGuard';

const routes: Routes = [{
  path: '',
  component: MainComponent,
  canActivate: [AppAuthGuard],
  data: { roles: ['user'] }
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
