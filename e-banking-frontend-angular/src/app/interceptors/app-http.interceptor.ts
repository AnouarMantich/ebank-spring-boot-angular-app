import {HttpInterceptorFn} from '@angular/common/http';
import {AuthService} from "../services/auth.service";
import {inject} from "@angular/core";
import {catchError, throwError} from "rxjs";

export const appHttpInterceptor: HttpInterceptorFn = (req, next) => {

  if(req.url.includes("/auth/login")) return next(req).pipe(
    catchError(err => {
      if (err.status==401){
        authService.logout()
        window.localStorage.removeItem("access_token")
      }
      return throwError(err.message)
    })
  );
  let authService=inject(AuthService)
  let request=req.clone({
    headers : req.headers.set("Authorization","Bearer "+authService.accessToken),
  })
  return next(request);
};
