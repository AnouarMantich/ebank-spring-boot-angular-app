import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Customer} from "../models/Customer";
import {CustomerService} from "../services/customer.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-customer',
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css'
})
export class NewCustomerComponent implements OnInit {
  public saveCustomerForm!: FormGroup;
  constructor(private formBuilder: FormBuilder,private customerService: CustomerService,private router: Router) {}

  ngOnInit(): void {
        this.saveCustomerForm=this.formBuilder.group({
          name:this.formBuilder.control("",[Validators.required,Validators.minLength(2)]),
          email:this.formBuilder.control("",[Validators.required,Validators.email]),
        })
    }


  handleSaveCustomer() {
    let customer:Customer = this.saveCustomerForm.value;
    this.customerService.saveCustomer(customer).subscribe({
      next: customer=>{
        alert("customer added successfully!");
        this.router.navigateByUrl('/admin/customers');
      }
    })
  }


}
