import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from "../services/auth.service";
import {inject} from "@angular/core";

export const authorizationGuard: CanActivateFn = (route, state) => {
 let authService: AuthService=inject(AuthService);
 let router: Router=inject(Router);
 if(!authService.roles.includes('ADMIN')){
   router.navigateByUrl("/admin/notAuthorized")
   return false;
 }


  return true;
};
