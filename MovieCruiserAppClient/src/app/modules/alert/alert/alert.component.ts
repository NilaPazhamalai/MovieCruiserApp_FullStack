import { Component, OnInit } from '@angular/core';
import { AlertService } from '../alert.service'
import { Alert } from '../alert';
import { AlertType } from '../alertType';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {

  alerts: Alert[] = [];
  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.getAlert().subscribe(
      (alert: Alert) => {
        this.alerts = [];
        if (!alert) {
          return;
        }
        this.alerts.push(alert);
      }
    );
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(x => x !== alert);
  }

  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }
    switch (alert.type) {
      case AlertType.Success:
        return 'alert alert-success';
      case AlertType.Info:
        return 'alert alert-info';
      case AlertType.Error:
        return 'alert alert-danger';
      case AlertType.Warn:
        return 'alert alert-warning';
    }
  }

}
