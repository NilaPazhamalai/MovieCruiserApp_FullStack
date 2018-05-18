import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'

import { MatToolbarModule}  from '@angular/material/toolbar'
import { MatButtonModule}  from '@angular/material/button'
import { MatButtonToggleModule}  from '@angular/material/button-toggle'
import { MatFormFieldModule}  from '@angular/material/form-field'
import { MatIconModule}  from '@angular/material/icon'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'

import { AppComponent } from './app.component';
import { MovieModule } from './modules/movie/movie.module';
import {AuthenticationModule} from './modules/authentication/authentication.module'
import { AlertModule } from './modules/alert/alert.module';
import { HttpClientModule } from '@angular/common/http';


const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/movies/popular' },
  { path: 'auth', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'register', pathMatch: 'full', redirectTo: '/auth/register' }
];

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatButtonToggleModule,
    HttpClientModule,
    MovieModule,
    AuthenticationModule,
    AlertModule,
    RouterModule.forRoot(appRoutes),
    
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
