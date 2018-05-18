import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, retry } from 'rxjs/operators'
import { User } from './user';
import { tap } from 'rxjs/operators/tap';
import { catchError } from 'rxjs/operators/catchError';
import { HttpResponse } from '@angular/common/http/src/response';
export const TOKEN_NAME: string = "jwt_token";
import * as jwt_decode_util from 'jwt-decode';
import { AlertService } from '../alert/alert.service';
@Injectable()
export class AuthenticationService {

  authEndPoint = "http://localhost:8088/user"
  authToken: string = "";
  userId: string = "";
  redirectUrl: string = "";

  constructor(private httpClient: HttpClient,
  private alertService:AlertService) {
  }

  registerNewUser(user: User): Observable<User> {
    let url = this.authEndPoint + "/register";
    return this.httpClient.post<User>(url, user).pipe(
      tap(_ => this.handleSuccess('User registered successfully, Please login to continue')),
      catchError(this.handleError<any>('register User',false))
    );
  }

  loginUser(user: User): Observable<Boolean> {
    let url = this.authEndPoint + "/login";
    return this.httpClient.post(url, user).pipe(
      tap(_ => console.log('User logged in successfully')),
      map(this.saveTokenAndReturnResult.bind(this)),
      catchError(this.handleError<any>('login User',false))
    );
  }

  saveTokenAndReturnResult(res: Response): Boolean {
    console.log([res]);
    this.setToken(res['token']);
    this.userId = res['userId'];
    return this.getToken() ? true : false;
  }

  getToken(): string {
    return localStorage.getItem(TOKEN_NAME);
  }

  setToken(token: string): void {
    localStorage.setItem(TOKEN_NAME, token);
  }

  deleteToken(): void {
    localStorage.removeItem(TOKEN_NAME);
  }

  getTokenExpirationDate(token: string): Date {
    const date = new Date(0);
    //token decode
    let decodedToken = jwt_decode_util(token);
    date.setUTCSeconds(decodedToken.exp);
    return decodedToken.exp === undefined ? null : date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) {
      token = this.getToken();
    }
    if (!token) {
      return true;
    }
    const date = this.getTokenExpirationDate(token);
    if (date === null) {
      return true;
    } else {
      return !(date.valueOf() > new Date().valueOf());
    }
  }

  logout(): void {
    this.deleteToken();
    this.userId = "";
    this.redirectUrl = "";
  }

  isLoggedIn(): boolean {
    const tokenExpired = this.isTokenExpired();
    return !tokenExpired;
  }

  //Handle Error
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      this.alertService.error(`${operation} failed => ${error.error.message}`);
      return Observable.of(result as T);
    }
  }

  //Handle success Message
  private handleSuccess(message:string){
    this.alertService.success(message,true);
  }


}
