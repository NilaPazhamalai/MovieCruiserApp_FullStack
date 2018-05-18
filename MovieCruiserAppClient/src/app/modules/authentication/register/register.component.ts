import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { User } from '../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  newUser : User;

  constructor(private authService : AuthenticationService, private router: Router) {
    this.newUser =  <User>{};
   }

  ngOnInit() {
  }

  registerUser(){
    console.log('register user '+ this.newUser.userId + this.newUser.password);
    this.authService.registerNewUser(this.newUser).subscribe(
      (user)=>{
        if(user){
          this.router.navigate(['/auth/login']);
        }else{
          //add anack bar message
        }
      }
    );
  }


  resetInput(){
    this.newUser =  <User>{};
  }

}
