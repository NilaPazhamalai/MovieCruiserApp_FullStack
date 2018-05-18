import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'

import { MatToolbarModule}  from '@angular/material/toolbar'
import { MatButtonModule}  from '@angular/material/button'
import { MatButtonToggleModule}  from '@angular/material/button-toggle'
import { MatFormFieldModule}  from '@angular/material/form-field'
import { MatIconModule}  from '@angular/material/icon'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'

import { MovieModule } from './modules/movie/movie.module';
import {AuthenticationModule} from './modules/authentication/authentication.module'
import { AlertModule } from './modules/alert/alert.module';
import { HttpClientModule } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core/src/metadata/ng_module';



const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/movies/popular' },
  { path: 'auth', pathMatch: 'full', redirectTo: '/auth/login' },
  { path: 'register', pathMatch: 'full', redirectTo: '/auth/register' }
];
describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      schemas:[NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'Movie Cruiser App'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Movie Cruiser App');
  }));
  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h3').textContent).toContain('Movie Cruiser App');
  }));
});
