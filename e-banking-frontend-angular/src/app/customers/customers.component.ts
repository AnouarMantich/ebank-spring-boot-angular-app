import {Component, OnInit} from '@angular/core';
import {Customer} from "../models/Customer";
import {CustomerService} from "../services/customer.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";


@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {

  public customers:Customer[] | undefined;
  public errorMessage : string ="";
  public loading:boolean = false;
  public customerSearchForm!: FormGroup;

  constructor(private customerService: CustomerService,private fb: FormBuilder,private router: Router,public authService:AuthService) {
  }

  ngOnInit(): void {
    this.customerSearchForm=this.fb.group({
      keyword : this.fb.control(""),
    })
    this.handleSearchCustomers();

  }



  handleSearchCustomers() {
    this.loading = true;
   this.customerService.searchCustomers(this.customerSearchForm.value.keyword).subscribe( {
     next : customers=>{
       this.loading = false;
       this.customers = customers;
     },
     error: error=> {
       this.errorMessage = error.message;
       this.loading = false;
     }
   })
  }
  handleDeleteCustomer(id:number) {
    let conf = confirm("Are you sure you want to delete?");
    if (!conf) return;
    this.customerService.deleteCustomer(id).subscribe({
      next: data=>{
        this.handleSearchCustomers()
      }
    })
  }

  checkClientAccounts(id: number) {
    this.router.navigateByUrl(`/admin/customerAccounts/${id}`);
  }
}
