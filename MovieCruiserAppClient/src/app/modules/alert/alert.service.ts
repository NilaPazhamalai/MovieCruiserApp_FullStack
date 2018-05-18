import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Alert } from './alert';
import { Router } from '@angular/router'
import { NavigationStart } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { AlertType } from './alertType';

@Injectable()
export class AlertService {
  private subject = new Subject<Alert>();
  private keepAfterRouterChange = false;

  constructor(private router: Router) {
    router.events.subscribe(
      (event) => {
        if (event instanceof NavigationStart) {
          if (this.keepAfterRouterChange) {
            this.keepAfterRouterChange = false;
          } else {
            this.clear();
          }
        }
      }
    );
  }

  getAlert(): Observable<any> {
    return this.subject.asObservable();
  }

  alert(type: AlertType, message: string, keepAfterRouterChange = false) {
    this.keepAfterRouterChange = keepAfterRouterChange;
    this.subject.next(<Alert>{ type: type, message: message })
  }
  clear() {
    this.subject.next();
  }

  //
  success(message: string, keepAfterRouterChange = false) {
    this.alert(AlertType.Success, message, keepAfterRouterChange);
  }
  error(message: string, keepAfterRouterChange = false) {
    this.alert(AlertType.Error, message, keepAfterRouterChange);
  }
  info(message: string, keepAfterRouterChange = false) {
    this.alert(AlertType.Info, message, keepAfterRouterChange);
  }
  warn(message: string, keepAfterRouterChange = false) {
    this.alert(AlertType.Warn, message, keepAfterRouterChange);
  }

}
