import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProtectedComponent } from './protected.component';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  {path: '', 
  component: ProtectedComponent,
  children:[
    {path: '', component: HomeComponent}    
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProtectedRoutingModule { }
