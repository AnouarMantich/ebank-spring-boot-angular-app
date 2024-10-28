import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from "../services/auth.service";
import {inject} from "@angular/core";

export const authenticationGuard: CanActivateFn = (route, state) => {
 let  authService : AuthService = inject(AuthService);
 let router=inject(Router);

 if (authService.isAuthenticated)
   return authService.isAuthenticated
  else
 {
   router.navigateByUrl("/login")
   return false;
 }
};
