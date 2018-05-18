import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import { AuthenticationService} from './modules/authentication/authentication.service'
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Movie Cruiser App';
  
  constructor(private authService:AuthenticationService,private router: Router){}

  logout():void{
    this.authService.logout();
    this.router.navigate(['/movies/popular']);
  }
}
