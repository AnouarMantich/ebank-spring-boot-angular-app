import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {jwtDecode} from "jwt-decode";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated: any;
  username :any;
  roles:any;
  accessToken:any;
  constructor(private http: HttpClient,private router:Router) { }


  public login(username: string, password: string){
    let options={
      headers : new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
    }
    let params = new HttpParams()
      .set('username', username).set('password', password);
    return this.http.post(`${`http://localhost:8080`}/auth/login`,params, options)
  }

  loadProfile(data:any){
    this.isAuthenticated = true;
    this.accessToken=data["access_token"]
    let {sub , scope}=jwtDecode(this.accessToken) as {sub:string,scope:string};
    this.username=sub
    this.roles=scope
    window.localStorage.setItem("access_token",this.accessToken)
  }

  logout() {
    this.isAuthenticated = false;
    this.accessToken=null;
    this.username = null;
    this.roles=null;
  }
  loadJwtTokenFromLocalStorage() {
    let token = localStorage.getItem("access_token");
    if (token) {
      console.log(typeof  token);
      this.loadProfile({"access_token": token});
      this.router.navigateByUrl('/admin');
    }
  }
}
