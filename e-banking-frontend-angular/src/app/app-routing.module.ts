import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import {LoginComponent} from "./login/login.component";
import {AdminComponent} from "./admin/admin.component";
import {authenticationGuard} from "./guards/authentication.guard";
import {authorizationGuard} from "./guards/authorization.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";

const routes: Routes = [
  {path : "login",component : LoginComponent},
  {path : "",redirectTo : "/login",pathMatch:"full"},
  {path : "admin",component : AdminComponent , canActivate:[authenticationGuard],
    children:[
      {path : "customers",component : CustomersComponent},
      {path : "accounts",component : AccountsComponent},
      {path : "accounts/:id",component : AccountsComponent},
      {path : "addCustomer",component : NewCustomerComponent,canActivate:[authorizationGuard ],data:["ADMIN"]},
      {path : "customerAccounts/:id",component : CustomerAccountsComponent},
      {path : "notAuthorized",component : NotAuthorizedComponent},
    ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
