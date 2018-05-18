import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms'
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {AuthenticationService} from '../authentication/authentication.service'
import { MatButtonModule} from '@angular/material/button'
import {MatSnackBarModule} from '@angular/material/snack-bar'
import { MatIconModule}  from '@angular/material/icon'
import { MatFormFieldModule, MatIcon } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { AuthenticationGuardService } from './authentication-guard.service';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AuthenticationRoutingModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  
  declarations: [LoginComponent, RegisterComponent],
  providers:[
    AuthenticationService, AuthenticationGuardService
  ]
})
export class AuthenticationModule { }
