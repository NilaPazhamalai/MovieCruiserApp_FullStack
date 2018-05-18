import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { AuthenticationService } from '../authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginUser:User;
  hide = true;
  constructor(private authService:AuthenticationService,private router:Router) { 
    this.loginUser = <User>{};
  }

  ngOnInit() {
  }

  login(){
    console.log('login user' + this.loginUser.userId + this.loginUser.password);
    this.authService.loginUser(this.loginUser).subscribe(
      (success)=>{
        if(success){
          
          if(this.authService.redirectUrl.length>0){
            console.log('REdirect :'+ this.authService.redirectUrl);
            this.router.navigate([this.authService.redirectUrl]);
          }else{
            console.log('REdirect :'+ '/movies/popular');
            this.router.navigate(['/movies/popular']);
          }
        }else{
          //add anack bar message
          console.log('login user error');
        }
      }
    );
  }

  resetInput(){
    this.loginUser =  <User>{};
  }


}
