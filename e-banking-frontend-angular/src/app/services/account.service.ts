import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountDetails} from "../models/account-details";
import {environment} from "../../environments/environment";
import {Customer} from "../models/Customer";
import {CustomerAccounts} from "../models/customer-accounts";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) { }

  public searchAccount(accountId:string,page:number,size:number) : Observable<AccountDetails>{
    return this.http.get<AccountDetails>(`${`http://localhost:8080`}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`);
  }

  public debit(accountId:string,amount:number,description:string)  {
    let data={accountId,amount,description};
    return this.http.post(`${`http://localhost:8080`}/accounts/debit`, data)
  }
  public credit(accountId:string,amount:number,description:string)  {
    let data={accountId,amount,description};
    return this.http.post(`${`http://localhost:8080`}/accounts/credit`, data)
  }
  public transfer(accountSource:string,accountDestination:string,amount:number,description:string)  {
    let data={accountSource,accountDestination,amount,description};
    return this.http.post(`${`http://localhost:8080`}/accounts/transfer`, data)
  }

  public customerAccounts(customerId:number):Observable<CustomerAccounts> {
   return this.http.get<CustomerAccounts>(`http://localhost:8080/customerAccounts/${customerId}`);
  }
}
