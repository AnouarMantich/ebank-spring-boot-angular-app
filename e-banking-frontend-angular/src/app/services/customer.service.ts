import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../models/Customer";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  searchCustomers( keyword:string) :Observable<Customer[]>{
    return this.http.get<Customer[]>(`${'http://localhost:8080'}/customers/search?keyword=${keyword}`);
  }

  saveCustomer(customer:Customer) : Observable<Customer>{
    return this.http.post<Customer>(`${'http://localhost:8080'}/customers`, customer)
  }

  deleteCustomer(id:number) : Observable<void>{
    return this.http.delete<void>(`${'http://localhost:8080'}/customers/${id}`);
  }
}
