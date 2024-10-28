import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountDetails} from "../models/account-details";
import {CustomerService} from "../services/customer.service";
import {AccountService} from "../services/account.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {
  public searchAccountForm!: FormGroup;
  public currentPage=0;
  public pageSize = 5;
  constructor(private formBuilder: FormBuilder,private accountService:AccountService,private activatedRoute:ActivatedRoute) { }
  public accountDetails!:AccountDetails;
  public operationForm!: FormGroup;
  public errorMessage!:string;
  public isLoading:boolean = false;

  ngOnInit() {
    let {id}=this.activatedRoute.snapshot.params;
    this.searchAccountForm = this.formBuilder.group({
      accountId:this.formBuilder.control(id || ""),
    })
    this.operationForm = this.formBuilder.group({
      operationType : this.formBuilder.control(null),
      amount : this.formBuilder.control(0),
      description : this.formBuilder.control(null),
      accountDestination : this.formBuilder.control(null),
    })
    if(id){
      this.searchAccount()
    }
  }

  searchAccount() {
    let {accountId}=this.searchAccountForm.value;
    this.isLoading = true;
     this.accountService.searchAccount(accountId,this.currentPage,this.pageSize).subscribe({
       next : accountDetails=>{
         this.accountDetails=accountDetails;
         this.isLoading = false;
       },
       error: error=> {
         this.errorMessage = error.error.message;

         this.isLoading = false;
       }
     })
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.searchAccount();
  }

  handleAccountOperation() {
    let {accountId}=this.searchAccountForm.value;
    let {operationType,amount,description,accountDestination} = this.operationForm.value;
    this.isLoading = true;
    switch(operationType) {
      case 'DEBIT':
        this.accountService.debit(accountId,amount,description).subscribe({
          next : accountDetails=>{
            alert("operation debit successfully !");
            this.searchAccount();
            this.operationForm.reset();
            this.isLoading=false
          },
          error : error=>{
            this.errorMessage=error.message
            this.isLoading=false
          }
        })
        break;
      case 'CREDIT':
        this.accountService.credit(accountId,amount,description).subscribe({
          next : accountDetails=>{
            alert("operation credit successfully !");
            this.searchAccount();
            this.operationForm.reset();
            this.isLoading=false
          },
          error : error=>{
            this.errorMessage=error.message
            this.isLoading=false
          }
        })
        break;
      case 'TRANSFER':
        this.accountService.transfer(accountId,accountDestination,amount,description).subscribe({
          next : accountDetails=>{
            alert("operation transfer successfully !");
            this.searchAccount();
            this.operationForm.reset();
            this.isLoading=false
          },
          error : error=>{
            this.errorMessage=error.message
            this.isLoading=false
          }
        })
        break;
    }

  }
}
