import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router'
import { AuthenticationService } from './authentication.service'
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AlertService } from '../alert/alert.service';
@Injectable()
export class AuthenticationGuardService implements CanActivate {

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private alertService: AlertService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log('auth guard service called');
    let url: string = state.url;
    console.log('url: ' + url);
    return this.checkLoginValidity(url);

  }


  checkLoginValidity(url: string) {
    let loggedIn = this.authService.isLoggedIn();
    console.log('user ID : ' + this.authService.userId + 'login : ' + loggedIn);
    if (loggedIn) return true;
    this.authService.redirectUrl = url;
    this.router.navigate(['/auth/login']);
    this.showWarnAlertMessage('Please login to continue');
    return false;
  }

  showWarnAlertMessage(message:string) {
    this.alertService.warn(message,true);
  }
}
